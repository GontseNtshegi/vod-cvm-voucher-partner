package za.co.vodacom.cvm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import za.co.vodacom.cvm.config.pojos.Coupons;
import za.co.vodacom.cvm.config.pojos.Encryption;
import za.co.vodacom.cvm.config.pojos.GiftCards;
import za.co.vodacom.cvm.config.pojos.Msisdn;

/**
 * Properties specific to Voucherpartner.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public Coupons coupons;
    public GiftCards giftCards;
    public Encryption encryption;
    public Msisdn msisdn;

    public Msisdn getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Msisdn msisdn) {
        this.msisdn = msisdn;
    }

    public Coupons getCoupons() {
        return coupons;
    }

    public void setCoupons(Coupons coupons) {
        this.coupons = coupons;
    }

    public GiftCards getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(GiftCards giftCards) {
        this.giftCards = giftCards;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
            "coupons=" + coupons +
            ", giftCards=" + giftCards +
            ", encryption=" + encryption +
            ", msisdn=" + msisdn +
            '}';
    }
}
