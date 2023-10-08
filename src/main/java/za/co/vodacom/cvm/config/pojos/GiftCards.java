package za.co.vodacom.cvm.config.pojos;

public class GiftCards {

    public String url;
    public Credentials defaults;
    public Credentials campaign10;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Credentials getDefaults() { return defaults; }

    public void setDefaults(Credentials defaults) { this.defaults = defaults; }

    public Credentials getCampaign10() { return campaign10; }

    public void setCampaign10(Credentials campaign10) { this.campaign10 = campaign10; }

    public static class Credentials {
        public String name;
        public String apiId;
        public String apiPassword;

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
    }

    @Override
    public String toString() {
        return "GiftCards{" +
            "url='" + url + '\'' +
            ", defaults=" + defaults +
            ", campaign10=" + campaign10 +
            '}';
    }
}
