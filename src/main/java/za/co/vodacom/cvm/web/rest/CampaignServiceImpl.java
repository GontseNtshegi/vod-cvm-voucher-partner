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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class CampaignServiceImpl implements CampaignApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

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
                .endDate(vpcampaign.getEndDate() == null ? null : vpcampaign.getEndDate().minusHours(2).format(formatter)));
        }

        return new ResponseEntity<>(campaignsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CampaignProductsResponse> getCampaignProducts(String campaignId) {

        CampaignProductsResponse campaignProductsResponse = new CampaignProductsResponse();

        //Needs work optimise db queries.
        Optional<VPCampaign> campaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        log.debug("Searching for campaign with ID:{}", campaignId);

        if (!campaign.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        }
        Optional<VPCampaignVouchers> vpCampaign = vpCampaignVouchersService.findOne(Long.valueOf(campaignId));
        log.debug("Searching for products in campaign with ID:{}", campaignId);
        if (vpCampaign.isPresent()) {
            log.debug(vpCampaign.toString());
            List<VoucherProductResponseObject> campaignProducts = new ArrayList<>();


            // Get products from DB
            List<CampaignProductDTO> vpCampaignProducts = vpCampaignService.getCampaignProducts(campaignId);

            if (vpCampaignProducts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            campaignProductsResponse.setCampaignName(campaign.get().getName());
            campaignProductsResponse.setCampaignId(campaignId);
            campaignProductsResponse.setStartDate(campaign.get().getStartDate().minusHours(2).format(formatter));
            campaignProductsResponse.setEndDate(campaign.get().getEndDate().minusHours(2).format(formatter));

            for (CampaignProductDTO cpProducts : vpCampaignProducts) {
                VoucherProductResponseObject vpProduct = new VoucherProductResponseObject();
                vpProduct.setId(String.valueOf(cpProducts.getId()));
                vpProduct.setProductId(cpProducts.getproduct_id());
                vpProduct.productName(cpProducts.getDescription());
                vpProduct.setActiveYN(cpProducts.getActiveYN());
                campaignProducts.add(vpProduct);
            }
            campaignProductsResponse.products(campaignProducts);
        }

        return new ResponseEntity<>(campaignProductsResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<QuantitiesResponseObject>> getQuantities(String campaignId) {
        Optional<VPCampaign> vpCampaign = vpCampaignService.findOne(Long.valueOf(campaignId));
        List<QuantitiesResponseObject> quantitiesResponseObjectList = new ArrayList<>();
        log.debug(":{}", vpCampaign);

        //Check if campaign ID exists
        if (!vpCampaign.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        } else {
            List<QuantityDetailsDTO> quantityDetailsDTOSList = vpVouchersService.getVoucherQuantity(Long.valueOf(campaignId), ZonedDateTime.now());
            log.debug("List of quantityDTO's", quantityDetailsDTOSList);

            quantityDetailsDTOSList.forEach(quantityDetailsDTO -> {
                QuantitiesResponseObject quantitiesResponseObject = new QuantitiesResponseObject();
                quantitiesResponseObject.setProductId(quantityDetailsDTO.getProductId());
                quantitiesResponseObject.setProductType(quantityDetailsDTO.getType());
                quantitiesResponseObject.setProductDescription(quantityDetailsDTO.getDescription());
                quantitiesResponseObject.setQuantity(Math.toIntExact(quantityDetailsDTO.getCount()));
                quantitiesResponseObject.setVoucherDescription(quantityDetailsDTO.getProductDescription());
                quantitiesResponseObject.setEndDate(quantityDetailsDTO.getEndDate() == null ? null : quantityDetailsDTO.getEndDate().minusHours(2).toLocalDate().toString());
                quantitiesResponseObject.setVoucherExpiryDate(quantityDetailsDTO.getExpiryDate() == null ? null : quantityDetailsDTO.getExpiryDate().minusHours(2).toLocalDate().toString());
                quantitiesResponseObjectList.add(quantitiesResponseObject);
            });
        }

        return new ResponseEntity<>(quantitiesResponseObjectList, HttpStatus.OK);
    }

    private boolean checkAllProductsExist(List<String> addList) {
        for (String productId : addList) {
            int count = voucherDefService.getVouchersByProductId(productId);
            if (count == 0) {
                return false; // productId does not exist
            }
        }
        return true; // All productIds exist
    }


    @Override
    public ResponseEntity<LinkDelinkResponse> linkDelinkProduct(String campaignid, LinkDelinkRequest linkDelinkRequest) {

        if (linkDelinkRequest.getAddProducts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "addProducts List must not be empty.");
        }

        if (linkDelinkRequest.getRemoveProducts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "removeProducts List must not be empty.");
        }


        Optional<VPCampaign> vpCampaign = vpCampaignService.findOne(Long.valueOf(campaignid));


        if (vpCampaign.isPresent()) {
            //Make the request into 2 lists
            List<String> addL = Arrays.asList(linkDelinkRequest.getAddProducts().split(","));
            List<String> addList = new ArrayList<>(addL);
            List<String> removeL = Arrays.asList(linkDelinkRequest.getRemoveProducts().split(","));
            List<String> removeList = new ArrayList<>(removeL);

            boolean allItemsExist = checkAllProductsExist(addList);

            if (!allItemsExist) {
                // throw an exception, log an error
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, " Invalid product in addProducts.");
            } else {
                if (addList.containsAll(removeList)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Clashing product IDs.");
                }

                int numAdded = 0;
                int numRemoved = 0;

                List<VPCampaignVouchers> addProductList = vpCampaignVouchersService.getVouchersByCampaign(Long.valueOf(campaignid)); //change to list

                if (addProductList.isEmpty()) { //checking if there are such products
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }

                for (String productID : addList) { // Looping through each product to be added to the db

                    Optional<VPCampaignVouchers> voucher = vpCampaignVouchersService.findVoucherByProductIdandCampaignId(Long.valueOf(campaignid), productID);
                    if (voucher.isPresent()) {
                        VPCampaignVouchers cpVoucher = voucher.get();
                        if (cpVoucher.getActiveYN().equals(Constants.NO)) {
                            cpVoucher.setActiveYN(Constants.YES);
                            cpVoucher.setModifiedDate(ZonedDateTime.now());
                            vpCampaignVouchersService.save(cpVoucher);
                            numAdded++;
                        }
                    } else {
                        vpCampaignVouchersService.save(new VPCampaignVouchers() //Product is not in db add it to db.
                            .campaignId(Long.valueOf(campaignid))
                            .productId(productID)
                            .createDate(ZonedDateTime.now())
                            .modifiedDate(ZonedDateTime.now())
                            .activeYN(Constants.YES));
                             numAdded++;
                    }
                }

                List<VPCampaignVouchers> removeProductsList = vpCampaignVouchersService.getVouchersByCampaign(Long.valueOf(campaignid)); //Updated products list
                if (removeProductsList.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                for (String productId : removeList) {
                    Optional<VPCampaignVouchers> voucher = vpCampaignVouchersService.findVoucherByProductIdandCampaignId(Long.valueOf(campaignid), productId);
                    if (voucher.isPresent()) {
                        if (voucher.get().getActiveYN().equals(Constants.YES)) {
                            voucher.get().setActiveYN(Constants.NO);
                            voucher.get().setModifiedDate(ZonedDateTime.now());
                            vpCampaignVouchersService.save(voucher.get());
                            numRemoved++;
                        }
                    }
                }
                return new ResponseEntity<>(new LinkDelinkResponse().campaignId(campaignid).numAdded(numAdded).numDeleted(numRemoved), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid CampaignID.");

    }

}




