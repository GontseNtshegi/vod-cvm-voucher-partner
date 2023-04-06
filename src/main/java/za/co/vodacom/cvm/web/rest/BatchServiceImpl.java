package za.co.vodacom.cvm.web.rest;

import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.web.api.ApiUtil;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.BatchDetailsResponse;

import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchApiDelegate {
    @Autowired
    VPBatchService vpBatchService;
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
