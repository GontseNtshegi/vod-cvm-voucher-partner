package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Coupon from WiGroup
 */
@ApiModel(description = "Coupon from WiGroup")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-07-12T21:30:03.237+02:00[Africa/Harare]"
)
public class CouponsGetResponseCoupon {

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
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createDate;

    @JsonProperty("redeemFromDate")
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime redeemFromDate;

    @JsonProperty("redeemToDate")
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime redeemToDate;

    @JsonProperty("wiCode")
    private Long wiCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("voucherAmount")
    private BigDecimal voucherAmount;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("redeemedAmount")
    private BigDecimal redeemedAmount;

    /**
     * The current state of the coupon. A (Active) D (Deactivated) E (Expired) R (Redeemed)
     */
    public enum StateIdEnum {
        A("A"),

        D("D"),

        E("E"),

        R("R");

        private String value;

        StateIdEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StateIdEnum fromValue(String value) {
            for (StateIdEnum b : StateIdEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @JsonProperty("stateId")
    private StateIdEnum stateId;

    @JsonProperty("redeemedDate")
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime redeemedDate;

    public CouponsGetResponseCoupon id(Long id) {
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

    public CouponsGetResponseCoupon userRef(String userRef) {
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

    public CouponsGetResponseCoupon campaignId(String campaignId) {
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

    public CouponsGetResponseCoupon campaignName(String campaignName) {
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

    public CouponsGetResponseCoupon campaignType(String campaignType) {
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

    public CouponsGetResponseCoupon termsAndConditions(String termsAndConditions) {
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

    public CouponsGetResponseCoupon createDate(OffsetDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    /**
     * The date the couponCampaign was created.
     * @return createDate
     */
    @ApiModelProperty(value = "The date the couponCampaign was created.")
    @Valid
    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public CouponsGetResponseCoupon redeemFromDate(OffsetDateTime redeemFromDate) {
        this.redeemFromDate = redeemFromDate;
        return this;
    }

    /**
     * From which date the coupon can be redeemed
     * @return redeemFromDate
     */
    @ApiModelProperty(value = "From which date the coupon can be redeemed")
    @Valid
    public OffsetDateTime getRedeemFromDate() {
        return redeemFromDate;
    }

    public void setRedeemFromDate(OffsetDateTime redeemFromDate) {
        this.redeemFromDate = redeemFromDate;
    }

    public CouponsGetResponseCoupon redeemToDate(OffsetDateTime redeemToDate) {
        this.redeemToDate = redeemToDate;
        return this;
    }

    /**
     * Until when the coupon is redeemable.
     * @return redeemToDate
     */
    @ApiModelProperty(value = "Until when the coupon is redeemable.")
    @Valid
    public OffsetDateTime getRedeemToDate() {
        return redeemToDate;
    }

    public void setRedeemToDate(OffsetDateTime redeemToDate) {
        this.redeemToDate = redeemToDate;
    }

    public CouponsGetResponseCoupon wiCode(Long wiCode) {
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

    public CouponsGetResponseCoupon description(String description) {
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

    public CouponsGetResponseCoupon voucherAmount(BigDecimal voucherAmount) {
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

    public CouponsGetResponseCoupon mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    /**
     * The users mobile number, if available.
     * @return mobileNumber
     */
    @ApiModelProperty(value = "The users mobile number, if available.")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public CouponsGetResponseCoupon imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * The image url of the coupon.
     * @return imageUrl
     */
    @ApiModelProperty(value = "The image url of the coupon.")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CouponsGetResponseCoupon redeemedAmount(BigDecimal redeemedAmount) {
        this.redeemedAmount = redeemedAmount;
        return this;
    }

    /**
     * The amount redeemed on the coupon.
     * @return redeemedAmount
     */
    @ApiModelProperty(value = "The amount redeemed on the coupon.")
    @Valid
    public BigDecimal getRedeemedAmount() {
        return redeemedAmount;
    }

    public void setRedeemedAmount(BigDecimal redeemedAmount) {
        this.redeemedAmount = redeemedAmount;
    }

    public CouponsGetResponseCoupon stateId(StateIdEnum stateId) {
        this.stateId = stateId;
        return this;
    }

    /**
     * The current state of the coupon. A (Active) D (Deactivated) E (Expired) R (Redeemed)
     * @return stateId
     */
    @ApiModelProperty(value = "The current state of the coupon. A (Active) D (Deactivated) E (Expired) R (Redeemed)")
    public StateIdEnum getStateId() {
        return stateId;
    }

    public void setStateId(StateIdEnum stateId) {
        this.stateId = stateId;
    }

    public CouponsGetResponseCoupon redeemedDate(OffsetDateTime redeemedDate) {
        this.redeemedDate = redeemedDate;
        return this;
    }

    /**
     * The date on which the coupon was redeemed (if it was expire)
     * @return redeemedDate
     */
    @ApiModelProperty(value = "The date on which the coupon was redeemed (if it was expire)")
    @Valid
    public OffsetDateTime getRedeemedDate() {
        return redeemedDate;
    }

    public void setRedeemedDate(OffsetDateTime redeemedDate) {
        this.redeemedDate = redeemedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CouponsGetResponseCoupon couponsGetResponseCoupon = (CouponsGetResponseCoupon) o;
        return (
            Objects.equals(this.id, couponsGetResponseCoupon.id) &&
            Objects.equals(this.userRef, couponsGetResponseCoupon.userRef) &&
            Objects.equals(this.campaignId, couponsGetResponseCoupon.campaignId) &&
            Objects.equals(this.campaignName, couponsGetResponseCoupon.campaignName) &&
            Objects.equals(this.campaignType, couponsGetResponseCoupon.campaignType) &&
            Objects.equals(this.termsAndConditions, couponsGetResponseCoupon.termsAndConditions) &&
            Objects.equals(this.createDate, couponsGetResponseCoupon.createDate) &&
            Objects.equals(this.redeemFromDate, couponsGetResponseCoupon.redeemFromDate) &&
            Objects.equals(this.redeemToDate, couponsGetResponseCoupon.redeemToDate) &&
            Objects.equals(this.wiCode, couponsGetResponseCoupon.wiCode) &&
            Objects.equals(this.description, couponsGetResponseCoupon.description) &&
            Objects.equals(this.voucherAmount, couponsGetResponseCoupon.voucherAmount) &&
            Objects.equals(this.mobileNumber, couponsGetResponseCoupon.mobileNumber) &&
            Objects.equals(this.imageUrl, couponsGetResponseCoupon.imageUrl) &&
            Objects.equals(this.redeemedAmount, couponsGetResponseCoupon.redeemedAmount) &&
            Objects.equals(this.stateId, couponsGetResponseCoupon.stateId) &&
            Objects.equals(this.redeemedDate, couponsGetResponseCoupon.redeemedDate)
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
            mobileNumber,
            imageUrl,
            redeemedAmount,
            stateId,
            redeemedDate
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CouponsGetResponseCoupon {\n");

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
        sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
        sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
        sb.append("    redeemedAmount: ").append(toIndentedString(redeemedAmount)).append("\n");
        sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
        sb.append("    redeemedDate: ").append(toIndentedString(redeemedDate)).append("\n");
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
