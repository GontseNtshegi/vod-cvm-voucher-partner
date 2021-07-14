package za.co.vodacom.cvm.web.rest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import za.co.vodacom.cvm.IntegrationTest;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class VoucherServiceImplIT {

    private static final String VOUCHER_API_URL = "/api/voucher";
    private static final String ALLOCATION_API_URL = VOUCHER_API_URL + "/allocation";
    private static final String RETURN_API_URL = VOUCHER_API_URL + "/return/{voucherId}";
    private static final String PRODUCT_API_URL = "/api/product";
    private static final String VALIDATION_API_URL = PRODUCT_API_URL + "/validation/{productId}";
}
