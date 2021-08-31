package za.co.vodacom.cvm.config.pojos;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Msisdn {
    private String external;
    private String internal;

    public String getExternal() {
        return external;
    }
    public void setExternal(String external) {
        this.external = external;
    }
    public String getInternal() {
        return internal;
    }
    public void setInternal(String internal) {
        this.internal = internal;
    }
}
