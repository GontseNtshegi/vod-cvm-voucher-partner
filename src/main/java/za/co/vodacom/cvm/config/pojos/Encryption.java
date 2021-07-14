package za.co.vodacom.cvm.config.pojos;

public class Encryption {

    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Encryption{" + "key='" + key + '\'' + '}';
    }
}
