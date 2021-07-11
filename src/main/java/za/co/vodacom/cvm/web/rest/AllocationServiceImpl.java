package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.web.api.AllocationApiDelegate;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationResponse;

import java.util.Optional;

public class AllocationServiceImpl implements AllocationApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(AllocationServiceImpl.class);
    @Autowired
    private final VPCampaignService vpCampaignService;
    @Autowired
    private final VPCampaignVouchersService vpCampaignVouchersService;

    AllocationServiceImpl(VPCampaignService vpCampaignService, VPCampaignVouchersService vpCampaignVouchersService) {
        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
    }

    @Override
    public ResponseEntity<VoucherAllocationResponse> updateVoucherToReserved(VoucherAllocationRequest voucherAllocationRequest) {
        log.debug(voucherAllocationRequest.toString());
        log.info(voucherAllocationRequest.toString());

        VoucherAllocationResponse voucherAllocationResponse = null;
        //Validate the incoming campaign
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(voucherAllocationRequest.getCampaign());
        if (vpCampaign.isPresent()) {//campaign found
            //Validate incoming productId
            Optional<VPCampaignVouchers> vpCampaignVouchers = vpCampaignVouchersService.findByProductId(voucherAllocationRequest.getProductId());
            if(vpCampaignVouchers.isPresent()){

            }
            else{
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }

        } else {//campaign not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }
}
