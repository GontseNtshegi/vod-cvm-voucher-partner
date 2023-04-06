package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.web.api.CampaignApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.bouncycastle.asn1.x500.style.BCStyle.T;

@Service
public class CampaignServiceImpl  implements CampaignApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private final VPCampaignService vpCampaignService;
    @Autowired
    private final VPCampaignVouchersService vpCampaignVouchersService;
    @Autowired
    private final VPVoucherDefService voucherDefService;
    @Autowired
    private final ProductServiceImpl vpProductService;

    public CampaignServiceImpl(VPCampaignService vpCampaignService, VPCampaignVouchersService vpCampaignVouchersService, VPVoucherDefService voucherDefService, ProductServiceImpl vpProductService) {
        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
        this.voucherDefService = voucherDefService;
        this.vpProductService = vpProductService;
    }

    @Override
    public ResponseEntity<List<CampaignListResponseObject>> getCampaignList() {

       List<VPCampaign> vpCampaignList = vpCampaignService.findAll();

       if(vpCampaignList.isEmpty() ){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       log.debug(vpCampaignList.toString());
        List<CampaignListResponseObject> campaignsList = new ArrayList<>();

        //Loop through list and map
        for(VPCampaign vpcampaign: vpCampaignList  )
        {
            campaignsList.add(new CampaignListResponseObject()
                .campaignId(vpcampaign.getId().toString())
                .campaignName(vpcampaign.getName())
                .startDate(vpcampaign.getStartDate().toLocalDate())
                .endDate(vpcampaign.getEndDate().toLocalDate()));
        }
        log.debug(campaignsList.toString());
        return new ResponseEntity<>(campaignsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<VoucherProductResponseObject>> getCampaignProducts(String campaignId) {
        List<VoucherProductResponseObject> voucherProductResponseObject = new ArrayList<>();

        Optional<VPCampaign> campaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        log.debug(campaign.toString());

       //check if list is empty then return 404
        if( campaign.isPresent())
        {
            Optional<VPCampaignVouchers> campaignVouchers = vpCampaignVouchersService.findOne(Long.valueOf(campaignId));

            if(campaignVouchers.isPresent()){
                log.debug(campaignVouchers.toString());
                 VoucherProductResponseObject voucherProductResponseObject1 = new VoucherProductResponseObject();

                VPCampaignVouchers campaignVoucher = campaignVouchers.get();
                voucherDefService.findOne(campaignVoucher.getProductId())
                        .ifPresent(vpVoucherDef -> {
                            voucherProductResponseObject1.setProductName(vpVoucherDef.getDescription());
                        });
                voucherProductResponseObject1.setProductId(campaignVoucher.getProductId());
                voucherProductResponseObject1.setId(String.valueOf(campaignVoucher.getId()));
                voucherProductResponseObject1.setActiveYN(campaignVoucher.getActiveYN());

                voucherProductResponseObject.add(voucherProductResponseObject1);
            }
            //return list object
            log.debug(voucherProductResponseObject.toString());
            return new ResponseEntity<>(voucherProductResponseObject,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //  throw new IllegalArgumentException("Invalid campaign ID");
        }



    }


    @Override
    public ResponseEntity<List<QuantitiesResponseObject>> getQuantities(String campaignId) {
      Optional<VPCampaign> campaign =  vpCampaignService.findOne(Long.valueOf(campaignId));

      //Check if campaign ID exists
      if(campaign.isPresent())
      {
          //TODO: run query *select d.product_id,d_desc,v.descr as voucher_desc, count* from VP Vouchers

          return CampaignApiDelegate.super.getQuantities(campaignId);
      }
      else {
          //If it exists return 404 or Invalid Campaign
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    }

    private static <T> Set<T> findCommonElements(List<T> first, List<T> second) { return first.stream().filter(second::contains).collect(Collectors.toSet());}

    @Override
    public ResponseEntity<List<LinkDelinkResponseObject>> linkDelinkProduct(String campaignid, LinkDelinkRequest linkDelinkRequest) {

        Optional<VPCampaign> vpCampaign = vpCampaignService.findOne(Long.valueOf(campaignid));

        if(vpCampaign.isPresent()){
            //Make the request into 2 lists
            List<String> addList = Arrays.asList(linkDelinkRequest.getAddProducts().split(","));
            List<String> removeList = Arrays.asList(linkDelinkRequest.getRemoveProducts().split(","));

            Set<String> common = findCommonElements(addList, removeList);
            if (!common.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Clashing product IDs");
            }

            int numAdded, numRemoved = 0;
            //TODO: run query select product_id, active_yn from VPCampaignVouchers

            // Optional<VPCampaignVouchers>voucher = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(campaignid,);


            return CampaignApiDelegate.super.linkDelinkProduct(campaignid, linkDelinkRequest);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
