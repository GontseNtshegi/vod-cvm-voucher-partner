package za.co.vodacom.cvm.config.pojos;

public class Coupons {

    public String url;
    public String name;
    public String apiId;
    public String apiPassword;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    @Override
    public String toString() {
        return (
            "Coupons{" +
            "url='" +
            url +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", apiId='" +
            apiId +
            '\'' +
            ", apiPassword='" +
            apiPassword +
            '\'' +
            '}'
        );
    }
}
