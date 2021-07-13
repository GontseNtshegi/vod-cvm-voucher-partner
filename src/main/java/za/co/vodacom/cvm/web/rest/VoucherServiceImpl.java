package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
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
import za.co.vodacom.cvm.client.wigroup.model.CouponsDelResponse;
import za.co.vodacom.cvm.client.wigroup.model.CouponsRequest;
import za.co.vodacom.cvm.client.wigroup.model.CouponsResponse;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.exception.WiGroupException;
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

    @Autowired
    Tracer tracer;

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
                                    CouponsRequest couponsRequest = new CouponsRequest();
                                    couponsRequest.setCampaignId(vpVoucherDef.getExtId());
                                    couponsRequest.setMobileNumber(voucherAllocationRequest.getMsisdn());
                                    couponsRequest.sendSMS(false);
                                    couponsRequest.setUserRef(voucherAllocationRequest.getMsisdn());
                                    couponsRequest.setSmsMessage("");
                                    //call wi group
                                    ResponseEntity<CouponsResponse> couponsResponseResponseEntity = couponsApiClient.issueVoucher(
                                        true,
                                        couponsRequest
                                    );
                                    //success
                                    CouponsResponse couponsResponse = couponsResponseResponseEntity.getBody();
                                    if (
                                        couponsResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                                        couponsResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                                    ) {
                                        //set response
                                        voucherAllocationResponse.setCollectpoint(couponsResponse.getCoupon().getCampaignType());
                                        voucherAllocationResponse.setExpiryDate(couponsResponse.getCoupon().getCreateDate());
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(couponsResponse.getCoupon().getWiCode() + "");
                                        voucherAllocationResponse.setVoucherDescription(couponsResponse.getCoupon().getDescription());
                                        voucherAllocationResponse.setVoucherId(couponsResponse.getCoupon().getId());
                                        voucherAllocationResponse.setVoucherType(couponsResponse.getCoupon().getCampaignType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());

                                        log.debug(voucherAllocationResponse.toString());
                                        log.info(voucherAllocationResponse.toString());
                                    } else { //failed
                                        throw new WiGroupException(
                                            couponsResponseResponseEntity.getBody().getResponseDesc(),
                                            Status.INTERNAL_SERVER_ERROR
                                        );
                                    }
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

    //retry once
    public ResponseEntity<VoucherAllocationResponse> updateVoucherToReservedFallback(VoucherAllocationRequest voucherAllocationRequest) {
        return updateVoucherToReserved(voucherAllocationRequest);
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "updateVoucherToReturnedFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherReturnResponse> updateVoucherToReturned(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        log.debug(voucherReturnRequest.toString());
        log.info(voucherReturnRequest.toString());
        VoucherReturnResponse voucherReturnResponse = new VoucherReturnResponse();
        Optional<VPVoucherDef> vpVoucherDefOptional = vpVoucherDefService.findOne(voucherReturnRequest.getProductId());
        if (vpVoucherDefOptional.isPresent()) { //productId found
            VPVoucherDef vpVoucherDef = vpVoucherDefOptional.get();
            log.debug(vpVoucherDef.toString());
            log.info(vpVoucherDef.toString());
            switch (vpVoucherDef.getType()) {
                case Constants.VOUCHER:
                    Optional<VPVouchers> optionalVPVouchers = vpVouchersService.getValidVoucherForReturn(
                        voucherReturnRequest.getProductId(),
                        voucherId,
                        voucherReturnRequest.getTrxId()
                    );
                    if (optionalVPVouchers.isPresent()) {
                        VPVouchers vpVouchers = optionalVPVouchers.get();
                        log.debug(vpVouchers.toString());
                        log.info(vpVouchers.toString());
                        //Update voucher
                        vpVouchersService.updateReturnedVoucher(voucherId);

                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setVoucherId(vpVouchers.getId());
                    } else {
                        log.debug("Voucher not found or expired: {}", voucherId);
                        log.info("Voucher not found or expired: {}", voucherId);
                        throw new AllocationException("Voucher not found or expired", Status.NOT_FOUND);
                    }
                    break;
                case Constants.GENERIC_VOUCHER:
                    //get valid voucher
                    //nothing to do just return
                    voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                    voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                    voucherReturnResponse.setVoucherId(voucherId);
                    break;
                case Constants.ONLINE_VOUCHER:
                    CouponsRequest couponsRequest = new CouponsRequest();
                    couponsRequest.setCampaignId(vpVoucherDef.getExtId());
                    couponsRequest.setMobileNumber(voucherReturnRequest.getMsisdn());
                    couponsRequest.sendSMS(false);
                    couponsRequest.setUserRef(voucherReturnRequest.getMsisdn());
                    couponsRequest.setSmsMessage("");

                    log.debug(couponsRequest.toString());
                    log.info(couponsRequest.toString());
                    //call wi group
                    ResponseEntity<CouponsDelResponse> couponsResponseResponseEntity = couponsApiClient.deleteVoucher(voucherId);
                    //success
                    CouponsDelResponse couponsDelResponse = couponsResponseResponseEntity.getBody();
                    log.debug(couponsDelResponse.toString());
                    log.info(couponsDelResponse.toString());
                    if (
                        couponsDelResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                        couponsDelResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                    ) {
                        //set response
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherId(voucherId);
                    } else { //failed
                        throw new WiGroupException(couponsResponseResponseEntity.getBody().getResponseDesc(), Status.INTERNAL_SERVER_ERROR);
                    }
                    break;
            }
        } else { //productId not found
            throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
        }

        log.debug(voucherReturnResponse.toString());
        log.info(voucherReturnResponse.toString());
        return new ResponseEntity<>(voucherReturnResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherReturnResponse> updateVoucherToReturnedFallback(
        Long voucherId,
        VoucherReturnRequest voucherReturnRequest
    ) {
        return updateVoucherToReturned(voucherId, voucherReturnRequest);
    }
}
