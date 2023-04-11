package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.BatchListResponseObject;
import java.util.ArrayList;
import java.util.List;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.web.api.model.BatchDetailsResponse;

import java.util.Optional;


@Service
public class BatchServiceImpl implements BatchApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);
    @Autowired
    VPBatchService vpBatchService;
    @Override
    public ResponseEntity<List<BatchListResponseObject>> batchlist() {
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

    @Override
    public ResponseEntity<BatchDetailsResponse> batchdetails(Integer batchId) {

        Optional<VPBatch> vpBatch = vpBatchService.findOne(batchId.longValue());

        if(!vpBatch.isPresent()){
            //throw error
        }else{
            BatchDetailsResponse batchDetailsResponse = new BatchDetailsResponse();

            vpBatchService.getVoucherQuantity(batchId.longValue());

            //map values to the JSON Object

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
