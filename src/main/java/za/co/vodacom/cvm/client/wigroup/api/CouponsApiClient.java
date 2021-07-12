package za.co.vodacom.cvm.client.wigroup.api;

import org.openapitools.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${application.coupons.name}", url = "${application.coupons.url}", configuration = ClientConfiguration.class)
public interface CouponsApiClient extends CouponsApi {}
