package za.co.vodacom.cvm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import za.co.vodacom.cvm.config.pojos.Coupons;

/**
 * Properties specific to Voucherpartner.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public Coupons coupons;

    public Coupons getCoupons() {
        return coupons;
    }

    public void setCoupons(Coupons coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" + "coupons=" + coupons + '}';
    }
}
