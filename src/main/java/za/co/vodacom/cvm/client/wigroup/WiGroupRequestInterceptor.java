package za.co.vodacom.cvm.client.wigroup;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        }
    }
}
