package za.co.vodacom.cvm.client;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ClientConfiguration {

    @Value("${wigroup.security.jwt.username:}")
    private String jwtUsername;

    @Value("${wigroup.security.jwt.password:}")
    private String jwtPassword;

    @Bean
    @ConditionalOnProperty(name = "wigroup.security.jwt.username")
    public BasicAuthRequestInterceptor jwtRequestInterceptor() {
        return new BasicAuthRequestInterceptor(this.jwtUsername, this.jwtPassword);
    }
}
