package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;
import za.co.vodacom.cvm.web.api.ProductApiDelegate;
import za.co.vodacom.cvm.web.api.model.ProductValidationResponse;

import java.math.BigDecimal;
import java.util.Optional;

@Service
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
    // @HystrixCommand(fallbackMethod = "validateVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<ProductValidationResponse> validateVoucher(String productId, String origin, String campaign) {
        ProductValidationResponse productValidationResponse = new ProductValidationResponse();
        log.info("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);

        //Validate the incoming campaign
        Optional<VPCampaignDTO> vpCampaignDTO = vpCampaignService.findByName(campaign);
        if (vpCampaignDTO.isPresent()) {
            log.info(vpCampaignDTO.get().toString());
            //Validate incoming productId
            Optional<VPCampaignVouchersDTO> vpCampaignVouchersDTO = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(
                productId,
                vpCampaignDTO.get().getId(),
                Constants.YES
            );
            //ProductId Found
            if (vpCampaignVouchersDTO.isPresent()) {
                log.info(vpCampaignVouchersDTO.get().toString());
                vpVoucherDefService
                    .findOne(vpCampaignVouchersDTO.get().getProductId())
                    .ifPresent(
                        vpVoucherDef -> {
                            switch (vpVoucherDef.getType()) {
                                case Constants.VOUCHER:
                                    vpVouchersService
                                        .getValidVoucherForProduct(productId)
                                        .ifPresent(
                                            vpVouchers -> {
                                                productValidationResponse.setMinExpiry(vpVouchers==null?null:vpVouchers.getMinEndDateTime().toOffsetDateTime());
                                                productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                                productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                                productValidationResponse.setVoucherQuantity(BigDecimal.valueOf(vpVouchers.getCount()));
                                                productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                                productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                            }
                                        );
                                    break;
                                case Constants.GENERIC_VOUCHER:
                                    vpVouchersService
                                        .getValidVoucherForProductGenericVoucher(productId)
                                        .ifPresent(
                                            vpVouchers -> {
                                                productValidationResponse.setMinExpiry(vpVouchers.getMinEndDateTime().toOffsetDateTime());
                                                productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                                productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                                productValidationResponse.setVoucherQuantity(BigDecimal.valueOf(vpVouchers.getCount()));
                                                productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                                productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                            }
                                        );
                                    if (productValidationResponse == null) {
                                        productValidationResponse.setMinExpiry(null);
                                        productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                        productValidationResponse.setVoucherQuantity(BigDecimal.ZERO);
                                        productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                        productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                    }
                                    break;
                                case Constants.ONLINE_VOUCHER:
                                    productValidationResponse.setMinExpiry(null);
                                    productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                    productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                    productValidationResponse.setVoucherQuantity(BigDecimal.ONE);
                                    productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                    productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                    break;
                            }
                        }
                    );
            } else {
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }
        } else { //campaign found //campaign not found
            throw new AllocationException("Invalid Campaign", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(productValidationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<ProductValidationResponse> validateVoucherFallback(String productId, String origin, String campaign) {
        return validateVoucher(productId, origin, campaign);
    }
}
