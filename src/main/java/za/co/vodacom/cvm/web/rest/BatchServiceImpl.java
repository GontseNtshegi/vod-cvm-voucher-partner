package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.exception.BatchException;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.BatchRequest;
import za.co.vodacom.cvm.web.api.model.BatchResponse;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchApiDelegate {
    public static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

    @Autowired
    private final VPBatchService vpBatchService;

    BatchServiceImpl(VPBatchService vpBatchService) {
        this.vpBatchService = vpBatchService;
    }


    public ResponseEntity<BatchResponse> batch(BatchRequest batchRequest) {
        Optional<VPBatch> vpBatch = vpBatchService.findByName(batchRequest.getBatchName());
        BatchResponse batchResponse = new BatchResponse();
        if (vpBatch.isPresent()) {
            throw new BatchException("Batch name already exists", Status.CONFLICT);
        } else {
            VPBatch newBatchEntry = new VPBatch();
            newBatchEntry.setName(batchRequest.getBatchName());
            newBatchEntry.setComment(batchRequest.getBatchComment());
            newBatchEntry.setRestrictedYN("N");
            newBatchEntry.setCreateUser(batchRequest.getUserName());
            newBatchEntry.setStatus("O");
            vpBatchService.save(newBatchEntry);

            batchResponse.setBatchId(BigDecimal.valueOf(newBatchEntry.getId()));
        }
        return new ResponseEntity<>(batchResponse, HttpStatus.OK);
    }
}
