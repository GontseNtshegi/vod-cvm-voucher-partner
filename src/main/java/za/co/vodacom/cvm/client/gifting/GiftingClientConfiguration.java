package za.co.vodacom.cvm.client.gifting;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class GiftingClientConfiguration {

    @Value("${application.gifting.username}")
    private String basicAuthUsername;

    @Value("${application.gifting.password}")
    private String basicAuthPassword;

    @Bean
    public BasicAuthRequestInterceptor giftingBasicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(this.basicAuthUsername, this.basicAuthPassword);
    }
}
