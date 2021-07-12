package za.co.vodacom.cvm.client.wigroup.api;

import org.openapitools.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "${coupons.name:coupons}",
    url = "${coupons.url:https://za-vsp-int.wigroup.co/cvs-issuer/rest}",
    configuration = ClientConfiguration.class
)
public interface CouponsApiClient extends CouponsApi {}
