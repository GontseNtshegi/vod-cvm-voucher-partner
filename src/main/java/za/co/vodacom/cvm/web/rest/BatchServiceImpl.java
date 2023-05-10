package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import java.time.LocalDateTime;
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

    BatchServiceImpl(VPBatchService vpBatchService) {
        this.vpBatchService = vpBatchService;
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

        Optional<VPBatch> vpBatch = vpBatchService.findOne(batchId.longValue());
        List<BatchDetailsResponseObject> batchDetailsResponse = new ArrayList<>();

        if (!vpBatch.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
        } else {
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

}
