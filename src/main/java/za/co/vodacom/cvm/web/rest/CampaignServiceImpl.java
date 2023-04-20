package za.co.vodacom.cvm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.product.Quantity;
import za.co.vodacom.cvm.web.api.CampaignApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


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
    private final VPVouchersService vpVouchersService;
    private boolean found = false;


    public CampaignServiceImpl(VPCampaignService vpCampaignService, VPCampaignVouchersService vpCampaignVouchersService, VPVoucherDefService voucherDefService, VPVouchersService vpVouchersService) {
        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
        this.voucherDefService = voucherDefService;
        this.vpVouchersService = vpVouchersService;
    }

    @Override
    public ResponseEntity<List<CampaignListResponseObject>> getCampaignList() {

        List<VPCampaign> vpCampaignList = vpCampaignService.findAll();

        if (vpCampaignList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.debug(vpCampaignList.toString());
        List<CampaignListResponseObject> campaignsList = new ArrayList<>();

        for (VPCampaign vpcampaign : vpCampaignList) {
            campaignsList.add(new CampaignListResponseObject()
                .campaignId(vpcampaign.getId().toString())
                .campaignName(vpcampaign.getName())
                .startDate(vpcampaign.getStartDate().toLocalDate())
                .endDate(vpcampaign.getEndDate().toLocalDate()));
        }
        log.debug("Campaign List{}", campaignsList.toString());
        return new ResponseEntity<>(campaignsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<VoucherProductResponseObject>> getCampaignProducts(String campaignId) {
        List<VoucherProductResponseObject> voucherListResponse = new ArrayList<>();

        //Needs work. May have to create a sql query that does everything in one query.

        Optional<VPCampaign> campaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        log.debug(campaign.toString());

        if (campaign.isPresent()) {
            Optional<VPCampaignVouchers> campaignVouchers = vpCampaignVouchersService.findOne(Long.valueOf(campaignId));

            if (campaignVouchers.isPresent()) {
                log.debug(campaignVouchers.toString());
                VoucherProductResponseObject voucherProductResponseObject = new VoucherProductResponseObject();

                VPCampaignVouchers campaignVoucher = campaignVouchers.get();
                voucherDefService.findOne(campaignVoucher.getProductId())
                    .ifPresent(vpVoucherDef -> {
                        voucherProductResponseObject.setProductName(vpVoucherDef.getDescription());
                    });
                voucherProductResponseObject.setProductId(campaignVoucher.getProductId());
                voucherProductResponseObject.setId(String.valueOf(campaignVoucher.getId()));
                voucherProductResponseObject.setActiveYN(campaignVoucher.getActiveYN());

                voucherListResponse.add(voucherProductResponseObject);
            }
            log.debug("List of campaign products{}", voucherListResponse.toString());
            return new ResponseEntity<>(voucherListResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<List<QuantitiesResponseObject>> getQuantities(String campaignId) {
        Optional<VPCampaign> campaign = vpCampaignService.findOne(Long.valueOf(campaignId));

        //Check if campaign ID exists
        if (campaign.isPresent()) {
            //TODO: run query *select d.product_id,d_desc,v.descr as voucher_desc, count* from VP Vouchers
            //May need to create a response DTO
/*
            List<Quantity> campaignVouchers = vpVouchersService.getVoucherQuantity();
            log.debug("Quantity response object:{}",campaignVouchers);

            List<QuantitiesResponseObject> quantitiesResponseObjectList = new ArrayList<>();
           for(Quantity quantity : campaignVouchers){
               quantitiesResponseObjectList.add(new QuantitiesResponseObject()
                   .productId(quantity.getProductId())
                   .quantity(quantity.getCount())
                   .productType(QuantitiesResponseObject.ProductTypeEnum.valueOf(quantity.getType()))
                   .productDescription(quantity.getProductDescription())
                   .voucherDescription(quantity.getDescription())
                   .endDate(LocalDate.from(quantity.getEndDate()))
                   .voucherExpiryDate(LocalDate.from(quantity.getExpiryDate())));
           }
*/


            return new ResponseEntity<>(/*quantitiesResponseObjectList,*/HttpStatus.OK);
        } else {
            //If it exists return 404 or Invalid Campaign
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static <T> Set<T> findCommonElements(List<T> first, List<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<LinkDelinkResponse> linkDelinkProduct(String campaignid, LinkDelinkRequest linkDelinkRequest) {

        Optional<VPCampaign> vpCampaign = vpCampaignService.findOne(Long.valueOf(campaignid));

        if (vpCampaign.isPresent()) {
            //Make the request into 2 lists
            List<String> addList = Arrays.asList(linkDelinkRequest.getAddProducts().split(","));
            List<String> removeList = Arrays.asList(linkDelinkRequest.getRemoveProducts().split(","));

            Set<String> common = findCommonElements(addList, removeList);
            if (!common.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Clashing product IDs");
            }
            int numAdded = 0;
            int numRemoved = 0;

            // Add products
            Optional<List<VPCampaignVouchers>> addProductList = vpCampaignVouchersService.getVouchersByProductId(addList);

            if (addProductList.isPresent()) {
                for (VPCampaignVouchers vpCampaignVouchers : addProductList.get()) {
                    log.debug("Add ProductId: {}", vpCampaignVouchers.getProductId());
                    if (vpCampaignVouchers.getActiveYN().equals(Constants.YES)) {
                        addList.remove(vpCampaignVouchers.getProductId());
                    } else {
                        vpCampaignVouchers.setActiveYN(Constants.YES);
                        vpCampaignVouchersService.save(vpCampaignVouchers);
                        addList.remove(vpCampaignVouchers.getProductId());
                        numAdded++;
                    }
                }
            }
            for (String toBeInserted : addList) {
                log.debug("Inserting productId: {}", toBeInserted);
                vpCampaignVouchersService.save(new VPCampaignVouchers()
                    .campaignId(Long.valueOf(campaignid))
                    .productId(toBeInserted)
                    .createDate(ZonedDateTime.now())
                    .modifiedDate(ZonedDateTime.now())
                    .activeYN(Constants.YES));
                numAdded++;
            }

            // Remove products
            Optional<List<VPCampaignVouchers>> removeProductList = vpCampaignVouchersService.getVouchersByProductId(removeList);

            if (removeProductList.isPresent()) {
                for (VPCampaignVouchers vpCampaignVouchers : removeProductList.get()) {
                    log.debug("Remove ProductId: {}", vpCampaignVouchers.getProductId());
                    if (vpCampaignVouchers.getActiveYN().equals(Constants.NO)) {
                        removeList.remove(vpCampaignVouchers.getProductId());
                    } else {
                        vpCampaignVouchers.setActiveYN(Constants.NO);
                        vpCampaignVouchersService.save(vpCampaignVouchers);
                        numRemoved++;
                    }
                }
            }
            return new ResponseEntity<>(new LinkDelinkResponse().campaignId(campaignid).numAdded(numAdded).numDeleted(numRemoved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


