package za.co.vodacom.cvm.config;

import java.util.List;
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
    public String proxyHost;
    public Integer proxyPort;
    public String proxyUser;
    public String proxyPassword;
    private List<String> allocationAllowed;

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

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public List<String> getAllocationAllowed() {
        return allocationAllowed;
    }

    public void setAllocationAllowed(List<String> allocationAllowed) {
        this.allocationAllowed = allocationAllowed;
    }

    @Override
    public String toString() {
        return (
            "ApplicationProperties{" +
            "coupons=" +
            coupons +
            ", giftCards=" +
            giftCards +
            ", encryption=" +
            encryption +
            ", msisdn=" +
            msisdn +
            ", proxyHost='" +
            proxyHost +
            '\'' +
            ", proxyPort=" +
            proxyPort +
            ", proxyUser='" +
            proxyUser +
            '\'' +
            ", proxyPassword='" +
            proxyPassword +
            '\'' +
            ", allocationAllowed=" +
            allocationAllowed +
            '}'
        );
    }
}
