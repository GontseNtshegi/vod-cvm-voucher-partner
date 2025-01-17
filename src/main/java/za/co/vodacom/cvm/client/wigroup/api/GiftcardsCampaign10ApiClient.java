package za.co.vodacom.cvm.client.wigroup.api;

import org.springframework.cloud.openfeign.FeignClient;
import za.co.vodacom.cvm.client.ClientConfiguration;

@FeignClient(name="${application.giftCards.campaign10.name}", url="${application.giftCards.url}", configuration = ClientConfiguration.class)
public interface GiftcardsCampaign10ApiClient extends GiftcardsApi {
}
