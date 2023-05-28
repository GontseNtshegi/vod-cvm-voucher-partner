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
import org.zalando.problem.Status;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.exception.BatchException;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.VPFileLoadService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            batchListResponseObject.setId(vpBatch.getId().intValue());
            batchListResponseObject.setBatchComment(vpBatch.getComment());
            batchListResponseObject.setCreateDate(vpBatch.getCreateDate().toOffsetDateTime());
            batchListResponseObject.setBatchName(vpBatch.getName());
            batchListResponseObject.setStatus(vpBatch.getStatus());
            batchListResponseObject.setActivateUser(vpBatch.getActivateUser());
            batchListResponseObject.setLoadDate(batchListResponseObject.getLoadDate() == null ? null : batchListResponseObject.getLoadDate());
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
            newBatchEntry.setLoadDate(null);
            newBatchEntry.setCreateDate(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UCT")));
            newBatchEntry.setActivateUser(null);

            VPBatch result = vpBatchService.save(newBatchEntry);
            log.debug("VPBatch result {}", result);
            batchResponse.setBatchId(BigDecimal.valueOf(result.getId().longValue()));
        }
        return new ResponseEntity<>(batchResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<BatchDetailsResponse> batchDetails(Integer batchId) {

        Optional<VPBatch> vpBatch = vpBatchService.findOne(batchId.longValue());
        List<BatchDetailsResponseObject> batchDetailsResponseObjects = new ArrayList<>();
        BatchDetailsResponse batchDetailsResponse = new BatchDetailsResponse();

        if (!vpBatch.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
        } else {
            VPBatch vpBatchResponse = vpBatch.get();
            List<BatchDetailsDTO> batchDetailsDTOList = vpBatchService.getVoucherQuantity(batchId, ZonedDateTime.now());
            batchDetailsResponse.setId(vpBatchResponse.getId().intValue());
            batchDetailsResponse.setBatchComment(vpBatchResponse.getComment());
            batchDetailsResponse.setStatus(vpBatchResponse.getStatus());
            batchDetailsResponse.setBatchName(vpBatchResponse.getName());
            batchDetailsResponse.setActivateUser(vpBatchResponse.getActivateUser());
            batchDetailsResponse.setCreateUser(vpBatchResponse.getCreateUser());
            batchDetailsResponse.setDeleteDate(vpBatchResponse.getDeleteDate() == null ? null : vpBatchResponse.getDeleteDate().toOffsetDateTime());
            batchDetailsResponse.setCreateDate(vpBatchResponse.getCreateDate() == null ? null : vpBatchResponse.getCreateDate().toOffsetDateTime());
            batchDetailsResponse.setLoadDate(vpBatchResponse.getLoadDate() == null ? null : vpBatchResponse.getLoadDate().toOffsetDateTime());
            batchDetailsResponse.setDeleteUser(vpBatchResponse.getDeleteUser());

            batchDetailsDTOList.forEach(batchDetailsDTO -> {
                BatchDetailsResponseObject batchDetailsResponse1 = new BatchDetailsResponseObject();
                batchDetailsResponse1.setProductType(batchDetailsDTO.getType());
                batchDetailsResponse1.setFileName(batchDetailsDTO.getFileName());
                batchDetailsResponse1.setQuantity(Math.toIntExact(batchDetailsDTO.getCount()));
                batchDetailsResponse1.setVoucherDescription(batchDetailsDTO.getVoucherDescription());
                batchDetailsResponse1.setProductId(batchDetailsDTO.getId());
                batchDetailsResponse1.setProductDescription(batchDetailsDTO.getDescription());
                batchDetailsResponse1.setStartDate(batchDetailsDTO.getStartDate() == null ? null :
                    batchDetailsDTO.getStartDate().toOffsetDateTime());
                batchDetailsResponse1.setEndDate(batchDetailsDTO.getEndDate() == null ? null :
                    batchDetailsDTO.getEndDate().toOffsetDateTime());
                batchDetailsResponse1.setVoucherExpiryDate(batchDetailsDTO.getExpiryDate() == null ? null :
                    batchDetailsDTO.getExpiryDate().toOffsetDateTime());

                batchDetailsResponseObjects.add(batchDetailsResponse1);

            });

            batchDetailsResponse.setProducts(batchDetailsResponseObjects);
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
                vpBatch1.setActivateUser(batchStatusRequest.getUserName());
                vpBatch1.setLoadDate(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UCT")));

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
                vpBatch1.setDeleteUser(batchStatusRequest.getUserName());
                vpBatch1.setDeleteDate(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UCT")));

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
        log.debug("Using get  batch with ID :{}",batchId);

        if(vpBatch.isPresent()) {
            log.debug("Fetched batch :{}",vpBatch);

            Optional<VPFileLoad> vpFileLoad = vpFileLoadService.findByBatchIdAndAndFileName(batchId,fileName);
            log.debug("findByBatchId : {} and Filename called: {}",vpBatch,fileName);
            if(vpFileLoad.isPresent()){
                log.debug("File name already exists throwing exception :{}",vpFileLoad);
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
                log.debug(" Saved file VPFileload : {} " , vpFileLoad1);

                //Create temp file name
                String tempName = "upload" + System.currentTimeMillis() + ".csv";
                try {
                    Path tempFile = Files.createTempFile(tempName, "");
                    data.transferTo(tempFile);
                    log.debug(" Creating temp file name:{} for file:{}",tempName,tempFile);

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

                log.debug("Updated batch :{}",vpFileLoad1);

                batchUploadResponse.setNumFailed(BigDecimal.valueOf(vpVoucherDTO.getNumFailed()));
                batchUploadResponse.setNumLoaded(BigDecimal.valueOf(vpVoucherDTO.getNumLoaded()));

            }

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid batch ID");
        }

        return new ResponseEntity<>(batchUploadResponse,HttpStatus.OK);
    }

}
