package za.co.vodacom.cvm.config;

import feign.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@EnableFeignClients(basePackages = "za.co.vodacom.cvm")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Value("${application.proxyHost:}")
    private String proxyHost;
    @Value("${application.proxyPort:}")
    private int proxyPort;

    /**
     * Set the Feign specific log level to log client REST requests.
     */
    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }

    @Bean
    public Client feignClient() {
        return new Client.Proxied(null, null,
            new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(proxyHost, proxyPort)));
    }
}
