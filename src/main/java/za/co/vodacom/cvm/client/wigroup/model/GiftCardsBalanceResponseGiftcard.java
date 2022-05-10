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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-10T08:13:28.436+02:00[Africa/Harare]")
public class GiftCardsBalanceResponseGiftcard   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("campaignId")
  private Integer campaignId;

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

  @JsonProperty("expiredAmount")
  private BigDecimal expiredAmount;

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

  public GiftCardsBalanceResponseGiftcard id(Long id) {
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

  public GiftCardsBalanceResponseGiftcard campaignId(Integer campaignId) {
    this.campaignId = campaignId;
    return this;
  }

  /**
   * campaignId
   * @return campaignId
  */
  @ApiModelProperty(value = "campaignId")


  public Integer getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Integer campaignId) {
    this.campaignId = campaignId;
  }

  public GiftCardsBalanceResponseGiftcard interfaceIssuerId(String interfaceIssuerId) {
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

  public GiftCardsBalanceResponseGiftcard issuerId(Integer issuerId) {
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

  public GiftCardsBalanceResponseGiftcard userRef(String userRef) {
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

  public GiftCardsBalanceResponseGiftcard mobileNumber(String mobileNumber) {
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

  public GiftCardsBalanceResponseGiftcard issuedAmount(BigDecimal issuedAmount) {
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

  public GiftCardsBalanceResponseGiftcard redeemedAmount(BigDecimal redeemedAmount) {
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

  public GiftCardsBalanceResponseGiftcard expiredAmount(BigDecimal expiredAmount) {
    this.expiredAmount = expiredAmount;
    return this;
  }

  /**
   * The amount expired on the giftcard.
   * @return expiredAmount
  */
  @ApiModelProperty(value = "The amount expired on the giftcard.")

  @Valid

  public BigDecimal getExpiredAmount() {
    return expiredAmount;
  }

  public void setExpiredAmount(BigDecimal expiredAmount) {
    this.expiredAmount = expiredAmount;
  }

  public GiftCardsBalanceResponseGiftcard balance(BigDecimal balance) {
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

  public GiftCardsBalanceResponseGiftcard createDate(String createDate) {
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

  public GiftCardsBalanceResponseGiftcard expiryDate(String expiryDate) {
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

  public GiftCardsBalanceResponseGiftcard campaignName(String campaignName) {
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

  public GiftCardsBalanceResponseGiftcard campaignType(String campaignType) {
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

  public GiftCardsBalanceResponseGiftcard description(String description) {
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

  public GiftCardsBalanceResponseGiftcard termsAndConditions(String termsAndConditions) {
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

  public GiftCardsBalanceResponseGiftcard stateId(StateIdEnum stateId) {
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

  public GiftCardsBalanceResponseGiftcard wicode(String wicode) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardsBalanceResponseGiftcard giftCardsBalanceResponseGiftcard = (GiftCardsBalanceResponseGiftcard) o;
    return Objects.equals(this.id, giftCardsBalanceResponseGiftcard.id) &&
        Objects.equals(this.campaignId, giftCardsBalanceResponseGiftcard.campaignId) &&
        Objects.equals(this.interfaceIssuerId, giftCardsBalanceResponseGiftcard.interfaceIssuerId) &&
        Objects.equals(this.issuerId, giftCardsBalanceResponseGiftcard.issuerId) &&
        Objects.equals(this.userRef, giftCardsBalanceResponseGiftcard.userRef) &&
        Objects.equals(this.mobileNumber, giftCardsBalanceResponseGiftcard.mobileNumber) &&
        Objects.equals(this.issuedAmount, giftCardsBalanceResponseGiftcard.issuedAmount) &&
        Objects.equals(this.redeemedAmount, giftCardsBalanceResponseGiftcard.redeemedAmount) &&
        Objects.equals(this.expiredAmount, giftCardsBalanceResponseGiftcard.expiredAmount) &&
        Objects.equals(this.balance, giftCardsBalanceResponseGiftcard.balance) &&
        Objects.equals(this.createDate, giftCardsBalanceResponseGiftcard.createDate) &&
        Objects.equals(this.expiryDate, giftCardsBalanceResponseGiftcard.expiryDate) &&
        Objects.equals(this.campaignName, giftCardsBalanceResponseGiftcard.campaignName) &&
        Objects.equals(this.campaignType, giftCardsBalanceResponseGiftcard.campaignType) &&
        Objects.equals(this.description, giftCardsBalanceResponseGiftcard.description) &&
        Objects.equals(this.termsAndConditions, giftCardsBalanceResponseGiftcard.termsAndConditions) &&
        Objects.equals(this.stateId, giftCardsBalanceResponseGiftcard.stateId) &&
        Objects.equals(this.wicode, giftCardsBalanceResponseGiftcard.wicode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, campaignId, interfaceIssuerId, issuerId, userRef, mobileNumber, issuedAmount, redeemedAmount, expiredAmount, balance, createDate, expiryDate, campaignName, campaignType, description, termsAndConditions, stateId, wicode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsBalanceResponseGiftcard {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    interfaceIssuerId: ").append(toIndentedString(interfaceIssuerId)).append("\n");
    sb.append("    issuerId: ").append(toIndentedString(issuerId)).append("\n");
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
    sb.append("    issuedAmount: ").append(toIndentedString(issuedAmount)).append("\n");
    sb.append("    redeemedAmount: ").append(toIndentedString(redeemedAmount)).append("\n");
    sb.append("    expiredAmount: ").append(toIndentedString(expiredAmount)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    campaignName: ").append(toIndentedString(campaignName)).append("\n");
    sb.append("    campaignType: ").append(toIndentedString(campaignType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    termsAndConditions: ").append(toIndentedString(termsAndConditions)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
    sb.append("    wicode: ").append(toIndentedString(wicode)).append("\n");
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

