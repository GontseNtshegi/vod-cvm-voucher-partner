package za.co.vodacom.cvm.client.wigroup;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WiGroupRequestInterceptor implements RequestInterceptor {

    @Autowired
    @Value("${application.coupons.name:}")
    private String name;

    @Autowired
    @Value("${application.coupons.apiId:}")
    private String apiId;

    @Autowired
    @Value("${application.coupons.apiPassword:}")
    private String apiPassword;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String wigroup = requestTemplate.feignTarget().name();
        if (
            wigroup.equalsIgnoreCase(this.name)
        ) { //Add wigroup Authentication
            requestTemplate.header("apiId", apiId);
            requestTemplate.header("apiPassword", apiPassword);
        }
    }
}
