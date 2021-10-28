package za.co.vodacom.cvm.client.gifting;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class GiftingFeignRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    @Override
    public void apply(RequestTemplate template) {
        //SecurityUtils.getCurrentUserJWT().ifPresent(s -> template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER, s)));
    }
}
