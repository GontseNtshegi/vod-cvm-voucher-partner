package za.co.vodacom.cvm.service;

import com.hazelcast.ringbuffer.impl.ReadResultSetImpl;
import org.apache.commons.validator.Arg;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.config.ApplicationProperties;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;
import za.co.vodacom.cvm.web.api.model.BatchStatusRequest;
import za.co.vodacom.cvm.web.rest.TestUtil;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "ADMIN" })
class BatchServiceIT {
    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.now();
    private static final String DEFAULT_STRING = "AAAAAA";
    public static final String DEFAULT_CAMPAIGN = "MegaShake";
    private static final long DEFAULT_LONG = 21L;
    private static final int DEFAULT_INT = 9;

    @MockBean
    VPBatchService vpBatchService;

    @MockBean
    VPFileLoadService vpFileLoadService;

    @Autowired
    VPVoucherDTO vpVoucherDTO;

    @Autowired
    private Job job;

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    ApplicationProperties applicationProperties;
    ///////////////////////////////////////////////////////TEST METHODS

    private List<VPBatch> createBatchListResponse() {
        VPBatch batch = new VPBatch();
        batch.setActivateUser(DEFAULT_STRING);
        batch.setComment(DEFAULT_STRING);
        batch.setCreateDate(DEFAULT_DATE.minusMonths(18));
        batch.setCreateUser(DEFAULT_STRING);
        batch.setDeleteDate(DEFAULT_DATE.minusMonths(3));
        batch.setDeleteUser(DEFAULT_STRING);
        batch.setId(DEFAULT_LONG);
        batch.setLoadDate(DEFAULT_DATE);
        batch.setName(DEFAULT_CAMPAIGN);
        batch.setRestrictedYN(Constants.YES);
        batch.setStatus(Constants.STATUS_A);

        List<VPBatch> batchList = new ArrayList<>();
        batchList.add(batch);
        return batchList ;
    }

    private VPBatch createBatch(boolean isStatusA,boolean isStatusD) {
        VPBatch vpBatch = new VPBatch();
        vpBatch.setActivateUser(DEFAULT_STRING);
        vpBatch.setComment(DEFAULT_STRING);
        vpBatch.setCreateDate(DEFAULT_DATE);
        vpBatch.setCreateUser(DEFAULT_STRING);
        vpBatch.setDeleteDate(DEFAULT_DATE.minusMonths(3));
        vpBatch.setDeleteUser(DEFAULT_STRING);
        vpBatch.setId(DEFAULT_LONG);
        vpBatch.setLoadDate(DEFAULT_DATE);
        vpBatch.setName(DEFAULT_STRING);
        vpBatch.setRestrictedYN(Constants.YES);
        if(isStatusA){
            vpBatch.setStatus(Constants.STATUS_A);
        } else if (isStatusD){
            vpBatch.setStatus(Constants.STATUS_D);
        }else {
            vpBatch.setStatus(DEFAULT_STRING);
        }
        return vpBatch;
    }

    private List<BatchDetailsDTO> createBatchDetailsDTO() {
        BatchDetailsDTO batchDetailsDTO = new BatchDetailsDTO(DEFAULT_STRING,DEFAULT_STRING,DEFAULT_STRING,DEFAULT_STRING,DEFAULT_DATE.minusMonths(60),DEFAULT_DATE.plusMonths(36),DEFAULT_DATE.plusMonths(30),DEFAULT_STRING,DEFAULT_LONG);
        List<BatchDetailsDTO> batchDetailsDTOS = new ArrayList<>();
        batchDetailsDTOS.add(batchDetailsDTO);
        return batchDetailsDTOS;
    }

    private BatchStatusRequest createBatchStatusRequest() {
        BatchStatusRequest request = new BatchStatusRequest();
        request.setStatus(Constants.STATUS_O);
        request.setUserName(DEFAULT_STRING);
        return  request;
    }


    ///////////////////////////////////////////////////////////TESTS
    @Test
    void batchList() throws Exception {
        given(this.vpBatchService.getAll())
            .willReturn(Optional.of(createBatchListResponse()));
        restMockMvc.perform(
            get("/api/batch/list/{1}",0)
        ).andExpect(status().isOk());
    }


    @Test
    void batchDetails() throws Exception{
        given(this.vpBatchService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(false,false)));
        given(this.vpBatchService.getVoucherQuantity(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willReturn(createBatchDetailsDTO());
        restMockMvc.perform(
            get("/api/batch/details/{1}",String.valueOf(DEFAULT_LONG))
        ).andExpect(status().isOk());
    }

    @Test
    void batchStatusA() throws Exception {
        given(this.vpBatchService.getBatch(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(true,false)));
        given(this.vpBatchService.save(ArgumentMatchers.any()))
            .willReturn(createBatch(true,false));
        given(this.vpBatchService.getBatchWithStatus(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(true,false)));
        restMockMvc.perform(
            put("/api/batch/status/{1}",DEFAULT_INT).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(createBatchStatusRequest()))
        ).andExpect(status().isForbidden());

    }

    @Test
    void batchStatusD() throws Exception {
        given(this.vpBatchService.getBatch(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(false,true)));
        given(this.vpBatchService.save(ArgumentMatchers.any()))
            .willReturn(createBatch(false,true));
        given(this.vpBatchService.getBatchWithStatus(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(false,true)));
        restMockMvc.perform(
            put("/api/batch/status/{1}",DEFAULT_INT).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(createBatchStatusRequest()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void batchStatus() throws Exception {
        given(this.vpBatchService.getBatch(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(false,false)));
        given(this.vpBatchService.save(ArgumentMatchers.any()))
            .willReturn(createBatch(false,false));
        given(this.vpBatchService.getBatchWithStatus(ArgumentMatchers.any()))
            .willReturn(Optional.of(createBatch(false,false)));
        restMockMvc.perform(
            put("/api/batch/status/{1}",DEFAULT_INT).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(createBatchStatusRequest()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void batchValidation() {
    }
}
