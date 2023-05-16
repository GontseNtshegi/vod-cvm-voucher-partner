package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.VPFileLoadService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.zalando.problem.Status;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.exception.BatchException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

    @Autowired
    VPBatchService vpBatchService;

    @Autowired
    VPFileLoadService vpFileLoadService;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    VPVoucherDTO vpVoucherDTO ;
    @Autowired
    private Job job;

    VPVouchersService vpVouchersService;

    BatchServiceImpl(VPBatchService vpBatchService,VPFileLoadService vpFileLoadService, VPVouchersService vpVouchersService) {
        this.vpBatchService = vpBatchService;
        this.vpFileLoadService = vpFileLoadService;
        this.vpVouchersService = vpVouchersService;
    }

    @Override
    public ResponseEntity<List<BatchListResponseObject>> batchList(Integer period) {
        List<BatchListResponseObject> batchListResponseObjects = new ArrayList<>();
        Optional<List<VPBatch>> listOptional ;

        if(period!=null && period > 0) {
          listOptional = vpBatchService.getAllListWithInterval(period);
        } else {
            listOptional = vpBatchService.getAll();
        }
        listOptional.ifPresent(vpBatches -> vpBatches.forEach(vpBatch -> {
            BatchListResponseObject batchListResponseObject = new BatchListResponseObject();
            batchListResponseObject.setBatchSeq(vpBatch.getId().intValue());
            batchListResponseObject.setBatchComment(vpBatch.getComment());
            batchListResponseObject.setCreateDate(vpBatch.getCreateDate().toOffsetDateTime());
            batchListResponseObject.setBatchName(vpBatch.getName());
            batchListResponseObject.setStatus(vpBatch.getStatus());
            batchListResponseObject.setActivateUser(vpBatch.getActivateUser());
            batchListResponseObject.setLoadDate(vpBatch.getLoadDate().toOffsetDateTime());
            batchListResponseObject.setCreateUser(vpBatch.getCreateUser());
            batchListResponseObjects.add(batchListResponseObject);
        }));

        log.debug("BatchList : {} ", batchListResponseObjects);
        return new ResponseEntity<>(batchListResponseObjects, HttpStatus.OK);

    }

    @Transactional
    @Override
    public ResponseEntity<BatchResponse> batch(BatchRequest batchRequest) {
        Optional<VPBatch> vpBatch = vpBatchService.findByName(batchRequest.getBatchName());
        BatchResponse batchResponse = new BatchResponse();
        if (vpBatch.isPresent()) {
            throw new BatchException("Batch name already exists", Status.CONFLICT);
        } else {
            VPBatch newBatchEntry = new VPBatch();
            newBatchEntry.setName(batchRequest.getBatchName());
            newBatchEntry.setComment(batchRequest.getBatchComment());
            newBatchEntry.setRestrictedYN(Constants.RESTRICTED_N);
            newBatchEntry.setCreateUser(batchRequest.getUserName());
            newBatchEntry.setStatus(Constants.STATUS_O);
            newBatchEntry.setLoadDate(ZonedDateTime.now());
            newBatchEntry.setCreateDate(ZonedDateTime.now());
            newBatchEntry.setDeleteUser(batchRequest.getUserName());
            newBatchEntry.setActivateUser(batchRequest.getBatchName());

            VPBatch result = vpBatchService.save(newBatchEntry);
            log.debug("VPBatch result {}", result);
            batchResponse.setBatchId(BigDecimal.valueOf(result.getId().longValue()));
        }
        return new ResponseEntity<>(batchResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<BatchDetailsResponseObject>> batchDetails(Integer batchId) {

        Optional<VPBatch> vpBatch = vpBatchService.findOne(Long.valueOf(batchId));
        List<BatchDetailsResponseObject> batchDetailsResponse = new ArrayList<>();

        if (!vpBatch.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
        } else {
            List<BatchDetailsDTO> batchDetailsDTOList = vpBatchService.getVoucherQuantity(Long.valueOf(batchId), ZonedDateTime.now());

            batchDetailsDTOList.forEach(batchDetailsDTO -> {
                BatchDetailsResponseObject batchDetailsResponse1 = new BatchDetailsResponseObject();
                batchDetailsResponse1.setEndDate(batchDetailsDTO.getEndDate().toLocalDate());
                batchDetailsResponse1.setFileName(batchDetailsDTO.getFileName());
                batchDetailsResponse1.setNumVouchers(Math.toIntExact(batchDetailsDTO.getCount()));
                batchDetailsResponse1.setVoucherDescription(batchDetailsDTO.getVoucherDescription());
                batchDetailsResponse1.setProductId(batchDetailsDTO.getId());
                batchDetailsResponse1.setProductDescription(batchDetailsDTO.getDescription());
                batchDetailsResponse1.setStartDate(batchDetailsDTO.getStartDate().toLocalDate());
                batchDetailsResponse1.setEndDate(batchDetailsDTO.getEndDate().toLocalDate());
                batchDetailsResponse1.setVoucherExpiryDate(batchDetailsDTO.getExpiryDate().toLocalDate());

                batchDetailsResponse.add(batchDetailsResponse1);

            });

        }

        return new ResponseEntity<>(batchDetailsResponse, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<BatchStatusResponse> batchStatus(Long batchId,
                                                           BatchStatusRequest batchStatusRequest) {
        BatchStatusResponse batchStatusResponse = new BatchStatusResponse();

        if (batchStatusRequest.getStatus().equalsIgnoreCase(Constants.STATUS_A)) {
            Optional<VPBatch> vpBatch = vpBatchService.getBatch(batchId);
            if (!vpBatch.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Batch ID");
            } else {
                VPBatch vpBatch1 = vpBatch.get();

                vpBatch1.setStatus(Constants.STATUS_A);
                vpBatch1.setName(batchStatusRequest.getUserName());
                vpBatch1.setLoadDate(ZonedDateTime.now());

                VPBatch result = vpBatchService.save(vpBatch1);
                batchStatusResponse.setStatus(result.getStatus());
            }
        } else if (batchStatusRequest.getStatus().equalsIgnoreCase(Constants.STATUS_D)) {
            Optional<VPBatch> vpBatch = vpBatchService.getBatchWithStatus(batchId);
            if (!vpBatch.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Batch ID");
            } else {
                VPBatch vpBatch1 = vpBatch.get();
                vpBatch1.setStatus(Constants.STATUS_D);
                vpBatch1.setName(batchStatusRequest.getUserName());
                vpBatch1.setLoadDate(ZonedDateTime.now());

                VPBatch result = vpBatchService.save(vpBatch1);

                batchStatusResponse.setStatus(result.getStatus());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid status");
        }

        return new ResponseEntity<>(batchStatusResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<BatchUploadResponse> batchUpload(Integer batchId,
                                                           String fileName,
                                                           MultipartFile data) {

        BatchUploadResponse batchUploadResponse = new BatchUploadResponse();

        Optional<VPBatch> vpBatch = vpBatchService.getBatch(Long.valueOf(batchId));

        if(vpBatch.isPresent()) {
            Optional<VPFileLoad> vpFileLoad = vpFileLoadService.findByBatchIdAndAndFileName(batchId,fileName);
            if(vpFileLoad.isPresent()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Filename already used");
            }else{
                VPFileLoad vpFileLoad1 = new VPFileLoad();

                vpFileLoad1.setBatchId(batchId);
                vpFileLoad1.setFileName(fileName);
                vpFileLoad1.setCompletedDate(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UCT")));
                vpFileLoad1.setCreateDate(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UCT")));
                vpFileLoad1.setNumLoaded(0);
                vpFileLoad1.setNumFailed(0);

                vpFileLoadService.save(vpFileLoad1);

                log.info("VPFileload : {} ", vpFileLoad1);
                log.debug("VPFileload : {} " , vpFileLoad1);

                //Create temp file name
                String tempName = "upload" + System.currentTimeMillis() + ".csv";
                try {
                    Path tempFile = Files.createTempFile(tempName, "");
                    data.transferTo(tempFile);

                JobParameters Parameters = new JobParametersBuilder()
                    .addString("fullPathFileName", tempFile.toString())
                    .addLong("StartAt", System.currentTimeMillis()).toJobParameters();

                    jobLauncher.run(job, Parameters);
                } catch (IOException | JobExecutionAlreadyRunningException | JobRestartException
                         | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

                    e.printStackTrace();
                }

                vpFileLoad1.setBatchId(batchId);
                vpFileLoad1.setNumFailed(vpVoucherDTO.getNumFailed());
                vpFileLoad1.setNumLoaded(vpVoucherDTO.getNumLoaded());
                vpFileLoadService.partialUpdate(vpFileLoad1);

                batchUploadResponse.setNumFailed(BigDecimal.valueOf(vpVoucherDTO.getNumFailed()));
                batchUploadResponse.setNumLoaded(BigDecimal.valueOf(vpVoucherDTO.getNumLoaded()));

            }

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid batch ID");
        }

        return new ResponseEntity<>(batchUploadResponse,HttpStatus.OK);
    }

}
