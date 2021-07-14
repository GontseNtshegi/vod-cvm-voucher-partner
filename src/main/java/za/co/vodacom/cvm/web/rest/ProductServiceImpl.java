package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.api.ProductApiDelegate;
import za.co.vodacom.cvm.web.api.model.ProductValidationResponse;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationResponse;

public class ProductServiceImpl implements ProductApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private final VPCampaignService vpCampaignService;

    @Autowired
    private final VPCampaignVouchersService vpCampaignVouchersService;

    @Autowired
    private final VPVoucherDefService vpVoucherDefService;

    @Autowired
    private final VPVouchersService vpVouchersService;

    @Autowired
    CouponsApiClient couponsApiClient;

    @Autowired
    Tracer tracer;

    ProductServiceImpl(
        VPCampaignService vpCampaignService,
        VPCampaignVouchersService vpCampaignVouchersService,
        VPVoucherDefService vpVoucherDefService,
        VPVouchersService vpVouchersService
    ) {
        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
        this.vpVoucherDefService = vpVoucherDefService;
        this.vpVouchersService = vpVouchersService;
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "updateVoucherToValidFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<ProductValidationResponse> updateVoucherToValid(String productId, String origin, String campaign) {
        ProductValidationResponse productValidationResponse = new ProductValidationResponse();
        log.debug("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);
        log.info("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);

        VoucherAllocationResponse voucherAllocationResponse = null;
        //Validate the incoming campaign
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(campaign);
        if (vpCampaign.isPresent()) {
            //Validate incoming productId
            Optional<VPCampaignVouchers> vpCampaignVouchers = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(
                productId,
                vpCampaign.get().getId(),
                Constants.YES
            );
        } else { //campaign found //campaign not found
            throw new AllocationException("Invalid Campaign", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(productValidationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<ProductValidationResponse> updateVoucherToValidFallback(String productId, String origin, String campaign) {
        return updateVoucherToValid(productId, origin, campaign);
    }
}
