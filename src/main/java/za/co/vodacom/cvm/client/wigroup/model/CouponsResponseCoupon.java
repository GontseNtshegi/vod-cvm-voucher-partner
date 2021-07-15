package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Coupon from WiGroup
 */
@ApiModel(description = "Coupon from WiGroup")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-07-12T21:30:03.237+02:00[Africa/Harare]"
)
public class CouponsResponseCoupon {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userRef")
    private String userRef;

    @JsonProperty("campaignId")
    private String campaignId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("campaignType")
    private String campaignType;

    @JsonProperty("termsAndConditions")
    private String termsAndConditions;

    @JsonProperty("createDate")
    //@org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private String createDate;

    @JsonProperty("redeemFromDate")
   // @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private String redeemFromDate;

    @JsonProperty("redeemToDate")
   // @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private String redeemToDate;

    @JsonProperty("wiCode")
    private Long wiCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("voucherAmount")
    private BigDecimal voucherAmount;

    @JsonProperty("wiQr")
    private Long wiQr;

    public CouponsResponseCoupon id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * The id of the coupon.
     * @return id
     */
    @ApiModelProperty(value = "The id of the coupon.")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CouponsResponseCoupon userRef(String userRef) {
        this.userRef = userRef;
        return this;
    }

    /**
     * A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.
     * @return userRef
     */
    @ApiModelProperty(
        value = "A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns."
    )
    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public CouponsResponseCoupon campaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    /**
     * campaignId
     * @return campaignId
     */
    @ApiModelProperty(value = "campaignId")
    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public CouponsResponseCoupon campaignName(String campaignName) {
        this.campaignName = campaignName;
        return this;
    }

    /**
     * The name of the campaign.
     * @return campaignName
     */
    @ApiModelProperty(value = "The name of the campaign.")
    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public CouponsResponseCoupon campaignType(String campaignType) {
        this.campaignType = campaignType;
        return this;
    }

    /**
     * The type of campaign.
     * @return campaignType
     */
    @ApiModelProperty(value = "The type of campaign.")
    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public CouponsResponseCoupon termsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
        return this;
    }

    /**
     * The terms and conditions of the coupon.
     * @return termsAndConditions
     */
    @ApiModelProperty(value = "The terms and conditions of the coupon.")
    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public CouponsResponseCoupon createDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    /**
     * The date the couponCampaign was created.
     * @return createDate
     */
    @ApiModelProperty(value = "The date the couponCampaign was created.")
    @Valid
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public CouponsResponseCoupon redeemFromDate(String redeemFromDate) {
        this.redeemFromDate = redeemFromDate;
        return this;
    }

    /**
     * From which date the coupon can be redeemed
     * @return redeemFromDate
     */
    @ApiModelProperty(value = "From which date the coupon can be redeemed")
    @Valid
    public String getRedeemFromDate() {
        return redeemFromDate;
    }

    public void setRedeemFromDate(String redeemFromDate) {
        this.redeemFromDate = redeemFromDate;
    }

    public CouponsResponseCoupon redeemToDate(String redeemToDate) {
        this.redeemToDate = redeemToDate;
        return this;
    }

    /**
     * Until when the coupon is redeemable.
     * @return redeemToDate
     */
    @ApiModelProperty(value = "Until when the coupon is redeemable.")
    @Valid
    public String getRedeemToDate() {
        return redeemToDate;
    }

    public void setRedeemToDate(String redeemToDate) {
        this.redeemToDate = redeemToDate;
    }

    public CouponsResponseCoupon wiCode(Long wiCode) {
        this.wiCode = wiCode;
        return this;
    }

    /**
     * The wiCode linked to the user token.
     * @return wiCode
     */
    @ApiModelProperty(value = "The wiCode linked to the user token.")
    public Long getWiCode() {
        return wiCode;
    }

    public void setWiCode(Long wiCode) {
        this.wiCode = wiCode;
    }

    public CouponsResponseCoupon description(String description) {
        this.description = description;
        return this;
    }

    /**
     * A description of the campaign.
     * @return description
     */
    @ApiModelProperty(value = "A description of the campaign.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CouponsResponseCoupon voucherAmount(BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
        return this;
    }

    /**
     * The possible coupon redemption amount.
     * @return voucherAmount
     */
    @ApiModelProperty(value = "The possible coupon redemption amount.")
    @Valid
    public BigDecimal getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public CouponsResponseCoupon wiQr(Long wiQr) {
        this.wiQr = wiQr;
        return this;
    }

    /**
     * The wiCode linked to the user token.
     * @return wiQr
     */
    @ApiModelProperty(value = "The wiCode linked to the user token.")
    public Long getWiQr() {
        return wiQr;
    }

    public void setWiQr(Long wiQr) {
        this.wiQr = wiQr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CouponsResponseCoupon couponsResponseCoupon = (CouponsResponseCoupon) o;
        return (
            Objects.equals(this.id, couponsResponseCoupon.id) &&
            Objects.equals(this.userRef, couponsResponseCoupon.userRef) &&
            Objects.equals(this.campaignId, couponsResponseCoupon.campaignId) &&
            Objects.equals(this.campaignName, couponsResponseCoupon.campaignName) &&
            Objects.equals(this.campaignType, couponsResponseCoupon.campaignType) &&
            Objects.equals(this.termsAndConditions, couponsResponseCoupon.termsAndConditions) &&
            Objects.equals(this.createDate, couponsResponseCoupon.createDate) &&
            Objects.equals(this.redeemFromDate, couponsResponseCoupon.redeemFromDate) &&
            Objects.equals(this.redeemToDate, couponsResponseCoupon.redeemToDate) &&
            Objects.equals(this.wiCode, couponsResponseCoupon.wiCode) &&
            Objects.equals(this.description, couponsResponseCoupon.description) &&
            Objects.equals(this.voucherAmount, couponsResponseCoupon.voucherAmount) &&
            Objects.equals(this.wiQr, couponsResponseCoupon.wiQr)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userRef,
            campaignId,
            campaignName,
            campaignType,
            termsAndConditions,
            createDate,
            redeemFromDate,
            redeemToDate,
            wiCode,
            description,
            voucherAmount,
            wiQr
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CouponsResponseCoupon {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
        sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
        sb.append("    campaignName: ").append(toIndentedString(campaignName)).append("\n");
        sb.append("    campaignType: ").append(toIndentedString(campaignType)).append("\n");
        sb.append("    termsAndConditions: ").append(toIndentedString(termsAndConditions)).append("\n");
        sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
        sb.append("    redeemFromDate: ").append(toIndentedString(redeemFromDate)).append("\n");
        sb.append("    redeemToDate: ").append(toIndentedString(redeemToDate)).append("\n");
        sb.append("    wiCode: ").append(toIndentedString(wiCode)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    voucherAmount: ").append(toIndentedString(voucherAmount)).append("\n");
        sb.append("    wiQr: ").append(toIndentedString(wiQr)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
