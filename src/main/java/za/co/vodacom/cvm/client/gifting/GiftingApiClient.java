package za.co.vodacom.cvm.client.gifting;

import org.springframework.cloud.openfeign.FeignClient;
import za.co.vodacom.cvm.client.GiftingApi;

@FeignClient(name="${application.gifting.feignClientName}", url="${application.gifting.url}", configuration = GiftingClientConfiguration.class)
public interface GiftingApiClient extends GiftingApi {
}
