package za.co.vodacom.cvm.web.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.api.ApiUtil;
import za.co.vodacom.cvm.web.api.VoucherApiDelegate;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationResponse;
import za.co.vodacom.cvm.web.api.model.VoucherReturnRequest;
import za.co.vodacom.cvm.web.api.model.VoucherReturnResponse;

public class VoucherServiceImpl implements VoucherApiDelegate {

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

    VoucherServiceImpl(
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
    @HystrixCommand(fallbackMethod = "updateVoucherToReservedFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherAllocationResponse> updateVoucherToReserved(VoucherAllocationRequest voucherAllocationRequest) {
        log.debug(voucherAllocationRequest.toString());
        log.info(voucherAllocationRequest.toString());

        VoucherAllocationResponse voucherAllocationResponse = null;
        //Validate the incoming campaign
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(voucherAllocationRequest.getCampaign());
        if (vpCampaign.isPresent()) { //campaign found
            //Validate incoming productId
            Optional<VPCampaignVouchers> vpCampaignVouchers = vpCampaignVouchersService.findByProductId(
                voucherAllocationRequest.getProductId()
            );
            if (vpCampaignVouchers.isPresent()) { //productId found
                log.debug(vpCampaignVouchers.get().toString());
                log.info(vpCampaignVouchers.get().toString());
                vpVoucherDefService
                    .findOne(voucherAllocationRequest.getProductId())
                    .ifPresent(
                        vpVoucherDef -> {
                            log.debug(vpVoucherDef.toString());
                            log.info(vpVoucherDef.toString());
                            switch (vpVoucherDef.getType()) {
                                case Constants.VOUCHER:
                                    vpVoucherDefService
                                        .findByProductId(voucherAllocationRequest.getProductId()) //acquire a lock
                                        .ifPresent(
                                            vpVoucherDef1 -> {
                                                //get valid voucher
                                                Optional<VPVouchers> vpVouchers = vpVouchersService.getValidVoucher(
                                                    voucherAllocationRequest.getProductId()
                                                );
                                                if (vpVouchers.isPresent()) { //voucher found
                                                    VPVouchers vpVoucher = vpVouchers.get();
                                                    log.debug(vpVoucher.toString());
                                                    log.info(vpVoucher.toString());
                                                    //issue voucher
                                                    vpVouchersService.issueVoucher(voucherAllocationRequest.getTrxId(), vpVoucher.getId());
                                                    //set response
                                                    voucherAllocationResponse.setCollectpoint(vpVoucher.getCollectionPoint());
                                                    voucherAllocationResponse.setExpiryDate(vpVoucher.getExpiryDate().toOffsetDateTime());
                                                    voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                                    voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                                    voucherAllocationResponse.setVoucherCode(vpVoucher.getVoucherCode());
                                                    voucherAllocationResponse.setVoucherDescription(vpVoucher.getDescription());
                                                    voucherAllocationResponse.setVoucherId(vpVoucher.getId());
                                                    voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                                    voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());

                                                    log.debug(voucherAllocationResponse.toString());
                                                    log.info(voucherAllocationResponse.toString());
                                                } else { //no valid voucher
                                                    throw new AllocationException("Voucher not available", Status.NOT_FOUND);
                                                }
                                            }
                                        );

                                    break;
                                case Constants.GENERIC_VOUCHER:
                                    //get valid voucher
                                    Optional<VPVouchers> vpVouchersGen = vpVouchersService.getValidVoucher(
                                        voucherAllocationRequest.getProductId()
                                    );
                                    if (vpVouchersGen.isPresent()) { //voucher found
                                        VPVouchers vpVoucher = vpVouchersGen.get();
                                        log.debug(vpVoucher.toString());
                                        log.info(vpVoucher.toString());

                                        //set response
                                        voucherAllocationResponse.setCollectpoint(vpVoucher.getCollectionPoint());
                                        voucherAllocationResponse.setExpiryDate(vpVoucher.getExpiryDate().toOffsetDateTime());
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(vpVoucher.getVoucherCode());
                                        voucherAllocationResponse.setVoucherDescription(vpVoucher.getDescription());
                                        voucherAllocationResponse.setVoucherId(vpVoucher.getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());

                                        log.debug(voucherAllocationResponse.toString());
                                        log.info(voucherAllocationResponse.toString());
                                    } else { //no valid voucher
                                        throw new AllocationException("Voucher not available", Status.NOT_FOUND);
                                    }
                                    break;
                                case Constants.ONLINE_VOUCHER:
                                    break;
                            }
                        }
                    );
            } else { //productId not found
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }
        } else { //campaign not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoucherReturnResponse> updateVoucherToReturned(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        getRequest()
            .ifPresent(
                request -> {
                    for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                        if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                            String exampleString =
                                "{ \"voucherId\" : 0.8008281904610115, \"trxId\" : \"trxId\", \"voucherDescription\" : \"voucherDescription\" }";
                            ApiUtil.setExampleResponse(request, "application/json", exampleString);
                            break;
                        }
                    }
                }
            );
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
