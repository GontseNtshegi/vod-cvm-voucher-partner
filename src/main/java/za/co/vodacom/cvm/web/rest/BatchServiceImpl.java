package za.co.vodacom.cvm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.web.api.ApiUtil;
import za.co.vodacom.cvm.web.api.BatchApiDelegate;
import za.co.vodacom.cvm.web.api.model.BatchListResponseObject;

import javax.xml.datatype.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchApiDelegate {

    @Autowired
    VPBatchService vpBatchService;
    @Override
    public ResponseEntity<List<BatchListResponseObject>> batchlist(Integer period) {

       List<BatchListResponseObject> batchListResponseObjects = new ArrayList<>();

       vpBatchService.getAll()
           .ifPresent(vpBatches -> {
               vpBatches.forEach(vpBatch -> {
                   // to add other fields once db changes are made

                   BatchListResponseObject batchListResponseObject = new BatchListResponseObject();
                   batchListResponseObject.setBatchSeq(vpBatch.getId().intValue());
                   batchListResponseObject.setComment(vpBatch.getComment());
                   batchListResponseObject.setCreateDate(vpBatch.getCreateDate().toOffsetDateTime());
                   batchListResponseObjects.add(batchListResponseObject);
               });
           });
        return new ResponseEntity<>(batchListResponseObjects,HttpStatus.OK);

    }

}
