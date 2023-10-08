package za.co.vodacom.cvm.service.dto.voucher;

public class VPVoucherDTO {
    private Integer numLoaded;
    private Integer numFailed;


    public Integer getNumLoaded() {
        return numLoaded;
    }

    public void setNumLoaded(Integer numLoaded) {
        this.numLoaded = numLoaded;
    }

    public Integer getNumFailed() {
        return numFailed;
    }

    public void setNumFailed(Integer numFailed) {
        this.numFailed = numFailed;
    }


    @Override
    public String toString() {
        return "VPVoucherDTO{" +
            "numLoaded=" + numLoaded +
            ", numFailed=" + numFailed +
            '}';
    }

}
