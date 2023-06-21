package za.co.vodacom.cvm.client.wigroup;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.vodacom.cvm.config.ApplicationProperties;

@Component
public class WiGroupRequestInterceptor implements RequestInterceptor {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String wigroup = requestTemplate.feignTarget().name();
        if (wigroup.equalsIgnoreCase(applicationProperties.getCoupons().getName())) { //Add wigroup Authentication
            requestTemplate.header("apiId", applicationProperties.getCoupons().getApiId());
            requestTemplate.header("apiPassword", applicationProperties.getCoupons().getApiPassword());
        }else if (wigroup.equalsIgnoreCase(applicationProperties.getGiftCards().getDefaults().getName())) { //Add wigroup Authentication for defaults
            requestTemplate.header("apiId", applicationProperties.getGiftCards().getDefaults().getApiId());
            requestTemplate.header("apiPassword", applicationProperties.getGiftCards().getDefaults().getApiPassword());
        }else if (wigroup.equalsIgnoreCase(applicationProperties.getGiftCards().getCampaign10().getName())) { //Add wigroup Authentication for campaign 10
            requestTemplate.header("apiId", applicationProperties.getGiftCards().getCampaign10().getApiId());
            requestTemplate.header("apiPassword", applicationProperties.getGiftCards().getCampaign10().getApiPassword());
        }
    }
}
