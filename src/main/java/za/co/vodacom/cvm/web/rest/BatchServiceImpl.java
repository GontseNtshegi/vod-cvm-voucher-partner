package za.co.vodacom.cvm.web.rest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.VPFileLoadService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;
import za.co.vodacom.cvm.web.api.ApiUtil;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    VPVouchersService vpVouchersService;

    BatchServiceImpl(VPBatchService vpBatchService,VPFileLoadService vpFileLoadService, VPVouchersService vpVouchersService) {
        this.vpBatchService = vpBatchService;
        this.vpFileLoadService = vpFileLoadService;
        this.vpVouchersService = vpVouchersService;
    }

    @Override
    public ResponseEntity<List<BatchListResponseObject>> batchList() {
        List<BatchListResponseObject> batchListResponseObjects = new ArrayList<>();
        vpBatchService.getAll()
            .ifPresent(vpBatches -> {
                vpBatches.forEach(vpBatch -> {
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
                });
            });
        log.debug("BatchList {} ", batchListResponseObjects);
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

        Optional<VPBatch> vpBatch = vpBatchService.findOne(batchId.longValue());
        List<BatchDetailsResponseObject> batchDetailsResponse = new ArrayList<>();

        if(!vpBatch.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
        }else{
            List<BatchDetailsDTO> batchDetailsDTOList = vpBatchService.getVoucherQuantity(batchId.longValue(), ZonedDateTime.now());

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

        return new ResponseEntity<>(batchDetailsResponse,HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<BatchStatusResponse> batchStatus(Long batchId,
                                                            BatchStatusRequest batchStatusRequest) {
        BatchStatusResponse batchStatusResponse = new BatchStatusResponse();

        if (batchStatusRequest.getStatus().equalsIgnoreCase(Constants.STATUS_A)) {
            Optional <VPBatch> vpBatch = vpBatchService.getBatch(batchId);
            if(!vpBatch.isPresent()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Batch ID");
            }else{
                VPBatch vpBatch1 = vpBatch.get();
                log.debug(vpBatch1.toString());
                log.info(vpBatch1.toString());
                vpBatchService.updateBatch(batchId,batchStatusRequest.getUserName());
                batchStatusResponse.setStatus(vpBatch1.getStatus());
            }
        } else if (batchStatusRequest.getStatus().equalsIgnoreCase(Constants.STATUS_D)) {
            Optional<VPBatch> vpBatch = vpBatchService.getBatchWithStatus(batchId);
            if(!vpBatch.isPresent()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid Batch ID");
            }else{
                VPBatch vpBatch1 = vpBatch.get();
                log.debug(vpBatch1.toString());
                log.info(vpBatch1.toString());
                vpBatchService.updateReturnedBatch(batchId,batchStatusRequest.getUserName());
                batchStatusResponse.setStatus(vpBatch1.getStatus());
            }
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid status");
        }

        log.debug(batchStatusResponse.toString());
        log.info(batchStatusResponse.toString());
        return new ResponseEntity<>(batchStatusResponse,HttpStatus.OK);

    }
@Override
    public ResponseEntity<BatchUploadResponse> batchUpload(Integer batchId,
                                                            BatchUploadRequest batchUploadRequest) {

        BatchUploadResponse batchUploadResponse = new BatchUploadResponse();

        Optional<VPBatch> vpBatch = vpBatchService.getBatch(Long.valueOf(batchId));

        if(vpBatch.isPresent()) {
            Optional<VPFileLoad> vpFileLoad = vpFileLoadService.getFileByNameAndId(batchId,batchUploadRequest.getFileName());
            if(vpFileLoad.isPresent()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FileName already exists");
            }else{
                VPFileLoad vpFileLoad1 = new VPFileLoad();

                vpFileLoad1.setBatchId(batchId);
                vpFileLoad1.setFileName(batchUploadRequest.getFileName());
                vpFileLoad1.setCompletedDate(ZonedDateTime.now());
                vpFileLoad1.setCompletedDate(ZonedDateTime.now());
                vpFileLoad1.setNumLoaded(0);
                vpFileLoad1.setNumFailed(0);

                vpFileLoadService.save(vpFileLoad1);

                // process the csv file
                try {
                    List<VPVouchers> vpVouchers = csvToVPVouchers(batchUploadRequest.getData().getInputStream());
                    List<VPVouchers> result = vpVouchersService.saveAll(vpVouchers);

                    log.debug("Results returned from csv" , result.toString());

                }catch (IOException e){
                    throw new RuntimeException("Failed to store csv data : " + e.getMessage());
                }
            }

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid batch ID");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public static List<VPVouchers> csvToVPVouchers(InputStream inputStream) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<VPVouchers> vpVouchers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for(CSVRecord csvRecord : csvRecords){
                VPVouchers vpVouchers1 = new VPVouchers(
                    csvRecord.get("productId"),
                    csvRecord.get("voucherCode"),
                    csvRecord.get("Description"),
                    ZonedDateTime.parse(csvRecord.get("startDate")),
                    ZonedDateTime.parse(csvRecord.get("endDate")),
                    ZonedDateTime.parse(csvRecord.get("expiryDate")),
                    csvRecord.get("CollectionPoint"),
                    Integer.parseInt(csvRecord.get("Quantity")));
                vpVouchers.add(vpVouchers1);
            }
            return vpVouchers;

        } catch(IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
