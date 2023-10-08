package za.co.vodacom.cvm.service.dto.campaign;

public class CampaignProductDTO {

    Long id;
    Long campaignId;
    String product_id;
    String active_yn;
    String description;

    public CampaignProductDTO() {
    }

    public CampaignProductDTO(Long id, Long campaignId, String product_id, String active_yn, String description) {
        this.id = id;
        this.campaignId = campaignId;
        this.product_id = product_id;
        this.active_yn = active_yn;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getproduct_id() {
        return product_id;
    }

    public void setproduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getActiveYN() {
        return active_yn;
    }

    public void setActiveYN(String activeYN) {
        this.active_yn = activeYN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "CampaignProductDTO{" +
            "id='" + id + '\'' +
            ", campaignId='" + campaignId + '\'' +
            ", product_id='" + product_id + '\'' +
            ", activeYN='" + active_yn + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
