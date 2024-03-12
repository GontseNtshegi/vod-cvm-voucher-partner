package za.co.vodacom.cvm.service;

import com.hazelcast.ringbuffer.impl.ReadResultSetImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.config.ApplicationProperties;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
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
    void batchDetails() {
    }

    @Test
    void batchStatus() {
    }

    @Test
    void batchValidation() {
    }
}
