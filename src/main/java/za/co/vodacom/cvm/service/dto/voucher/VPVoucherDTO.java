package za.co.vodacom.cvm.service.dto.voucher;

public class VPVoucherDTO {
    private Integer numLoaded;
    private Integer numFailed;
    private Integer batchId;

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


    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }


    @Override
    public String toString() {
        return "VPVoucherDTO{" +
            "numLoaded=" + numLoaded +
            ", numFailed=" + numFailed +
            ", batchId=" + batchId +
            '}';
    }

}
