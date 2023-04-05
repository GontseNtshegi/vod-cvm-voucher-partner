package za.co.vodacom.cvm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.web.api.CampaignApiDelegate;
import za.co.vodacom.cvm.web.api.model.Campaign;
import za.co.vodacom.cvm.web.api.model.LinkDelinkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl  implements CampaignApiDelegate {

    @Autowired
    private final VPCampaignService vpCampaignService;

    public CampaignServiceImpl(VPCampaignService vpCampaignService) {
        this.vpCampaignService = vpCampaignService;
    }

    @Override
    public ResponseEntity<List<Campaign>> getCampaignList() {
       List<VPCampaign> vpCampaignList = vpCampaignService.findAll();

       if(vpCampaignList.isEmpty() ){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       //Loop through list and map
        List<Campaign> campaignsList = new ArrayList<>();

        for(VPCampaign vpcampaign: vpCampaignList  )
        {
            campaignsList.add(new Campaign()
                .campaignId(vpcampaign.getId().toString())
                .campaignName(vpcampaign.getName())
                .startDate(vpcampaign.getStartDate().toLocalDate())
                .endDate(vpcampaign.getEndDate().toLocalDate()));
        }

        return new ResponseEntity<>(campaignsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Object>> getCampaignProducts(String campaignId) {
       Optional<VPCampaign> campaignList = vpCampaignService.findByName(campaignId);

       //check if list is empty then return 404
        if( campaignList.equals(null))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //return list object
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Object>> getQuantities(String campaignId) {
        return CampaignApiDelegate.super.getQuantities(campaignId);
    }

    @Override
    public ResponseEntity<List<Object>> linkDelinkProduct(String campaignid, LinkDelinkRequest linkDelinkRequest) {




        return CampaignApiDelegate.super.linkDelinkProduct(campaignid, linkDelinkRequest);
    }
}
