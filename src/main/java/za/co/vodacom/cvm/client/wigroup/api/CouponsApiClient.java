package za.co.vodacom.cvm.client.wigroup.api;

import org.springframework.cloud.openfeign.FeignClient;
import za.co.vodacom.cvm.client.ClientConfiguration;

@FeignClient(name = "${application.coupons.name}", url = "${application.coupons.url}", configuration = ClientConfiguration.class)
public interface CouponsApiClient extends CouponsApi {}
