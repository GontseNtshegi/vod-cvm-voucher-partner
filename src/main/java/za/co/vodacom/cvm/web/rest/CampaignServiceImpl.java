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
import za.co.vodacom.cvm.service.*;
import za.co.vodacom.cvm.service.dto.campaign.CampaignProductDTO;
import za.co.vodacom.cvm.service.dto.campaign.QuantityDetailsDTO;
import za.co.vodacom.cvm.web.api.CampaignApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private final VPBatchService vpBatchService;

    private boolean found = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    public CampaignServiceImpl(VPCampaignService vpCampaignService, VPCampaignVouchersService vpCampaignVouchersService, VPVoucherDefService voucherDefService, VPVouchersService vpVouchersService, VPBatchService vpBatchService) {
        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
        this.voucherDefService = voucherDefService;
        this.vpVouchersService = vpVouchersService;
        this.vpBatchService = vpBatchService;
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
                .startDate(vpcampaign.getStartDate().minusHours(2).format(formatter))
                .endDate(vpcampaign.getEndDate() == null? null: vpcampaign.getEndDate().minusHours(2).format(formatter)));
        }
        log.debug("Campaign List{}", campaignsList.toString());
        return new ResponseEntity<>(campaignsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CampaignProductsResponse> getCampaignProducts(String campaignId) {
        //List<VoucherProductResponseObject> voucherListResponse = new ArrayList<>();
        CampaignProductsResponse campaignProductsResponse = new CampaignProductsResponse();

        //Needs work. May have to create a sql query that does everything in one query.
        Optional<VPCampaign> campaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        log.debug(campaign.toString());

        if (campaign.isPresent()) {
            Optional<VPCampaignVouchers> vpCampaign = vpCampaignVouchersService.findOne(Long.valueOf(campaignId));
            if (vpCampaign.isPresent()) {
                log.debug(vpCampaign.toString());
                List<VoucherProductResponseObject> campaignProducts = new ArrayList<>();


               // VPCampaignVouchers campaignVoucher = vpCampaign.get();
                List<CampaignProductDTO> vpCampaignProducts = vpCampaignService.getCampaignProducts(campaignId);

                if(vpCampaignProducts.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                campaignProductsResponse.setCampaignName(campaign.get().getName());
                campaignProductsResponse.setCampaignId(campaignId);
                campaignProductsResponse.setStartDate(String.valueOf(campaign.get().getStartDate().format(formatter)));
                campaignProductsResponse.setEndDate(String.valueOf(campaign.get().getEndDate().format(formatter)));

                for(CampaignProductDTO cpProducts : vpCampaignProducts){
                    VoucherProductResponseObject vpProduct = new VoucherProductResponseObject();
                    vpProduct.setId(String.valueOf(cpProducts.getId()));
                    vpProduct.setProductId(cpProducts.getproduct_id());
                    vpProduct.productName(cpProducts.getDescription());
                    vpProduct.setActiveYN(cpProducts.getActiveYN());
                    campaignProducts.add(vpProduct);
                }
                campaignProductsResponse.setCampaignProductId(campaignProducts);
            }
        }
          //  log.debug("List of campaign products{}", voucherListResponse.toString());
            return new ResponseEntity<>(campaignProductsResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<QuantitiesResponseObject>> getQuantities(String campaignId) {
        Optional<VPCampaign> vpCampaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        List<QuantitiesResponseObject> quantitiesResponseObjectList = new ArrayList<>();
        log.debug(":{}",vpCampaign);

        //Check if campaign ID exists
        if (!vpCampaign.isPresent()) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Campaign not found.");
        }else {
                List<QuantityDetailsDTO> quantityDetailsDTOSList = vpVouchersService.getVoucherQuantity(Long.valueOf(campaignId),ZonedDateTime.now());
                log.debug("List of quantityDTO's",quantityDetailsDTOSList);

                quantityDetailsDTOSList.forEach(quantityDetailsDTO -> {
                    QuantitiesResponseObject quantitiesResponseObject = new QuantitiesResponseObject();
                     quantitiesResponseObject.setProductId(quantityDetailsDTO.getProductId());
                     quantitiesResponseObject.setProductType(quantityDetailsDTO.getType());
                     quantitiesResponseObject.setProductDescription(quantityDetailsDTO.getProductDescription());
                     quantitiesResponseObject.setQuantity(Math.toIntExact(quantityDetailsDTO.getCount()));
                     quantitiesResponseObject.setVoucherDescription(quantityDetailsDTO.getDescription());
                     quantitiesResponseObject.setEndDate(quantityDetailsDTO.getEndDate() == null? null:quantityDetailsDTO.getEndDate().toLocalDate());
                     quantitiesResponseObject.setVoucherExpiryDate(quantityDetailsDTO.getvoucherExpiryDate() == null? null: quantityDetailsDTO.getvoucherExpiryDate().toLocalDate());
                    quantitiesResponseObjectList.add(quantitiesResponseObject);
                });
                log.debug("Response object{}",quantitiesResponseObjectList);
        }

        return new ResponseEntity<>(quantitiesResponseObjectList,HttpStatus.OK);
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


