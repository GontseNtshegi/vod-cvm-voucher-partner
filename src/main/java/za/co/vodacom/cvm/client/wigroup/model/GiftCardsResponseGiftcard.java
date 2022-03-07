package za.co.vodacom.cvm.client.wigroup.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GiftCards from WiGroup
 */
@ApiModel(description = "GiftCards from WiGroup")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-01T15:34:21.931559100+02:00[Africa/Harare]")
public class GiftCardsResponseGiftcard   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("campaignId")
  private String campaignId;

  @JsonProperty("interfaceIssuerId")
  private String interfaceIssuerId;

  @JsonProperty("issuerId")
  private Integer issuerId;

  @JsonProperty("userRef")
  private String userRef;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("issuedAmount")
  private BigDecimal issuedAmount;

  @JsonProperty("redeemedAmount")
  private BigDecimal redeemedAmount;

  @JsonProperty("expiryAmount")
  private BigDecimal expiryAmount;

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("createDate")
  private String createDate;

  @JsonProperty("expiryDate")
  private String expiryDate;

  @JsonProperty("campaignName")
  private String campaignName;

  @JsonProperty("campaignType")
  private String campaignType;

  @JsonProperty("description")
  private String description;

  @JsonProperty("termsAndConditions")
  private String termsAndConditions;

  /**
   * The current state of the GiftCard. A (Active) D (Deactivated) E (Expired) R (Redeemed)
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

  @JsonProperty("wicode")
  private String wicode;

  @JsonProperty("redeemFromTime")
  private String redeemFromTime;

  @JsonProperty("redeemToTime")
  private String redeemToTime;

  public GiftCardsResponseGiftcard id(Long id) {
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

  public GiftCardsResponseGiftcard campaignId(String campaignId) {
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

  public GiftCardsResponseGiftcard interfaceIssuerId(String interfaceIssuerId) {
    this.interfaceIssuerId = interfaceIssuerId;
    return this;
  }

  /**
   * The interface issuer id.
   * @return interfaceIssuerId
  */
  @ApiModelProperty(value = "The interface issuer id.")


  public String getInterfaceIssuerId() {
    return interfaceIssuerId;
  }

  public void setInterfaceIssuerId(String interfaceIssuerId) {
    this.interfaceIssuerId = interfaceIssuerId;
  }

  public GiftCardsResponseGiftcard issuerId(Integer issuerId) {
    this.issuerId = issuerId;
    return this;
  }

  /**
   * The issuer id.
   * @return issuerId
  */
  @ApiModelProperty(value = "The issuer id.")


  public Integer getIssuerId() {
    return issuerId;
  }

  public void setIssuerId(Integer issuerId) {
    this.issuerId = issuerId;
  }

  public GiftCardsResponseGiftcard userRef(String userRef) {
    this.userRef = userRef;
    return this;
  }

  /**
   * A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.
   * @return userRef
  */
  @ApiModelProperty(value = "A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.")


  public String getUserRef() {
    return userRef;
  }

  public void setUserRef(String userRef) {
    this.userRef = userRef;
  }

  public GiftCardsResponseGiftcard mobileNumber(String mobileNumber) {
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

  public GiftCardsResponseGiftcard issuedAmount(BigDecimal issuedAmount) {
    this.issuedAmount = issuedAmount;
    return this;
  }

  /**
   * The issued Amount.
   * @return issuedAmount
  */
  @ApiModelProperty(value = "The issued Amount.")

  @Valid

  public BigDecimal getIssuedAmount() {
    return issuedAmount;
  }

  public void setIssuedAmount(BigDecimal issuedAmount) {
    this.issuedAmount = issuedAmount;
  }

  public GiftCardsResponseGiftcard redeemedAmount(BigDecimal redeemedAmount) {
    this.redeemedAmount = redeemedAmount;
    return this;
  }

  /**
   * The amount redeemed on the giftcard.
   * @return redeemedAmount
  */
  @ApiModelProperty(value = "The amount redeemed on the giftcard.")

  @Valid

  public BigDecimal getRedeemedAmount() {
    return redeemedAmount;
  }

  public void setRedeemedAmount(BigDecimal redeemedAmount) {
    this.redeemedAmount = redeemedAmount;
  }

  public GiftCardsResponseGiftcard expiryAmount(BigDecimal expiryAmount) {
    this.expiryAmount = expiryAmount;
    return this;
  }

  /**
   * The amount expired on the giftcard.
   * @return expiryAmount
  */
  @ApiModelProperty(value = "The amount expired on the giftcard.")

  @Valid

  public BigDecimal getExpiryAmount() {
    return expiryAmount;
  }

  public void setExpiryAmount(BigDecimal expiryAmount) {
    this.expiryAmount = expiryAmount;
  }

  public GiftCardsResponseGiftcard balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * The amount to issue on giftcard.
   * @return balance
  */
  @ApiModelProperty(value = "The amount to issue on giftcard.")

  @Valid

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public GiftCardsResponseGiftcard createDate(String createDate) {
    this.createDate = createDate;
    return this;
  }

  /**
   * The date the giftcard was created.
   * @return createDate
  */
  @ApiModelProperty(value = "The date the giftcard was created.")


  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public GiftCardsResponseGiftcard expiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

  /**
   * The date the giftcard expired.
   * @return expiryDate
  */
  @ApiModelProperty(value = "The date the giftcard expired.")


  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public GiftCardsResponseGiftcard campaignName(String campaignName) {
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

  public GiftCardsResponseGiftcard campaignType(String campaignType) {
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

  public GiftCardsResponseGiftcard description(String description) {
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

  public GiftCardsResponseGiftcard termsAndConditions(String termsAndConditions) {
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

  public GiftCardsResponseGiftcard stateId(StateIdEnum stateId) {
    this.stateId = stateId;
    return this;
  }

  /**
   * The current state of the GiftCard. A (Active) D (Deactivated) E (Expired) R (Redeemed)
   * @return stateId
  */
  @ApiModelProperty(value = "The current state of the GiftCard. A (Active) D (Deactivated) E (Expired) R (Redeemed)")


  public StateIdEnum getStateId() {
    return stateId;
  }

  public void setStateId(StateIdEnum stateId) {
    this.stateId = stateId;
  }

  public GiftCardsResponseGiftcard wicode(String wicode) {
    this.wicode = wicode;
    return this;
  }

  /**
   * The wiCode linked to the user token.
   * @return wicode
  */
  @ApiModelProperty(value = "The wiCode linked to the user token.")


  public String getWicode() {
    return wicode;
  }

  public void setWicode(String wicode) {
    this.wicode = wicode;
  }

  public GiftCardsResponseGiftcard redeemFromTime(String redeemFromTime) {
    this.redeemFromTime = redeemFromTime;
    return this;
  }

  /**
   * From which date the gift card can be redeemed
   * @return redeemFromTime
  */
  @ApiModelProperty(value = "From which date the gift card can be redeemed")


  public String getRedeemFromTime() {
    return redeemFromTime;
  }

  public void setRedeemFromTime(String redeemFromTime) {
    this.redeemFromTime = redeemFromTime;
  }

  public GiftCardsResponseGiftcard redeemToTime(String redeemToTime) {
    this.redeemToTime = redeemToTime;
    return this;
  }

  /**
   * Until when the gift card is redeemable.
   * @return redeemToTime
  */
  @ApiModelProperty(value = "Until when the gift card is redeemable.")


  public String getRedeemToTime() {
    return redeemToTime;
  }

  public void setRedeemToTime(String redeemToTime) {
    this.redeemToTime = redeemToTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardsResponseGiftcard giftCardsResponseGiftcard = (GiftCardsResponseGiftcard) o;
    return Objects.equals(this.id, giftCardsResponseGiftcard.id) &&
        Objects.equals(this.campaignId, giftCardsResponseGiftcard.campaignId) &&
        Objects.equals(this.interfaceIssuerId, giftCardsResponseGiftcard.interfaceIssuerId) &&
        Objects.equals(this.issuerId, giftCardsResponseGiftcard.issuerId) &&
        Objects.equals(this.userRef, giftCardsResponseGiftcard.userRef) &&
        Objects.equals(this.mobileNumber, giftCardsResponseGiftcard.mobileNumber) &&
        Objects.equals(this.issuedAmount, giftCardsResponseGiftcard.issuedAmount) &&
        Objects.equals(this.redeemedAmount, giftCardsResponseGiftcard.redeemedAmount) &&
        Objects.equals(this.expiryAmount, giftCardsResponseGiftcard.expiryAmount) &&
        Objects.equals(this.balance, giftCardsResponseGiftcard.balance) &&
        Objects.equals(this.createDate, giftCardsResponseGiftcard.createDate) &&
        Objects.equals(this.expiryDate, giftCardsResponseGiftcard.expiryDate) &&
        Objects.equals(this.campaignName, giftCardsResponseGiftcard.campaignName) &&
        Objects.equals(this.campaignType, giftCardsResponseGiftcard.campaignType) &&
        Objects.equals(this.description, giftCardsResponseGiftcard.description) &&
        Objects.equals(this.termsAndConditions, giftCardsResponseGiftcard.termsAndConditions) &&
        Objects.equals(this.stateId, giftCardsResponseGiftcard.stateId) &&
        Objects.equals(this.wicode, giftCardsResponseGiftcard.wicode) &&
        Objects.equals(this.redeemFromTime, giftCardsResponseGiftcard.redeemFromTime) &&
        Objects.equals(this.redeemToTime, giftCardsResponseGiftcard.redeemToTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, campaignId, interfaceIssuerId, issuerId, userRef, mobileNumber, issuedAmount, redeemedAmount, expiryAmount, balance, createDate, expiryDate, campaignName, campaignType, description, termsAndConditions, stateId, wicode, redeemFromTime, redeemToTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsResponseGiftcard {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    interfaceIssuerId: ").append(toIndentedString(interfaceIssuerId)).append("\n");
    sb.append("    issuerId: ").append(toIndentedString(issuerId)).append("\n");
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
    sb.append("    issuedAmount: ").append(toIndentedString(issuedAmount)).append("\n");
    sb.append("    redeemedAmount: ").append(toIndentedString(redeemedAmount)).append("\n");
    sb.append("    expiryAmount: ").append(toIndentedString(expiryAmount)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    campaignName: ").append(toIndentedString(campaignName)).append("\n");
    sb.append("    campaignType: ").append(toIndentedString(campaignType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    termsAndConditions: ").append(toIndentedString(termsAndConditions)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
    sb.append("    wicode: ").append(toIndentedString(wicode)).append("\n");
    sb.append("    redeemFromTime: ").append(toIndentedString(redeemFromTime)).append("\n");
    sb.append("    redeemToTime: ").append(toIndentedString(redeemToTime)).append("\n");
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

