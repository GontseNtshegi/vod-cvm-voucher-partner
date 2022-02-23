package za.co.vodacom.cvm.client.wigroup.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GiftCardsRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-23T12:47:32.039955200+02:00[Africa/Harare]")
public class GiftCardsRequest   {
  @JsonProperty("campaignId")
  private String campaignId;

  @JsonProperty("balance")
  private Long balance;

  @JsonProperty("userRef")
  private String userRef;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("numExpiryDays")
  private Integer numExpiryDays;

  @JsonProperty("smsMessage")
  private String smsMessage;

  @JsonProperty("sendSMS")
  private Boolean sendSMS;

  @JsonProperty("sendFollowUpSMS")
  private Boolean sendFollowUpSMS;

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

  public GiftCardsRequest campaignId(String campaignId) {
    this.campaignId = campaignId;
    return this;
  }

  /**
   * The id of the campaign to issue against.
   * @return campaignId
  */
  @ApiModelProperty(required = true, value = "The id of the campaign to issue against.")
  @NotNull


  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public GiftCardsRequest balance(Long balance) {
    this.balance = balance;
    return this;
  }

  /**
   * The amount to issue on giftcard.
   * @return balance
  */
  @ApiModelProperty(required = true, value = "The amount to issue on giftcard.")
  @NotNull


  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }

  public GiftCardsRequest userRef(String userRef) {
    this.userRef = userRef;
    return this;
  }

  /**
   * A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.
   * @return userRef
  */
  @ApiModelProperty(required = true, value = "A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.")
  @NotNull


  public String getUserRef() {
    return userRef;
  }

  public void setUserRef(String userRef) {
    this.userRef = userRef;
  }

  public GiftCardsRequest mobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
    return this;
  }

  /**
   * The mobile number. The mobile number must be presented in International format.
   * @return mobileNumber
  */
  @ApiModelProperty(value = "The mobile number. The mobile number must be presented in International format.")


  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public GiftCardsRequest numExpiryDays(Integer numExpiryDays) {
    this.numExpiryDays = numExpiryDays;
    return this;
  }

  /**
   * If allowed by campaign the channel can override the default expiry days. It will still be capped by the default campaign value.
   * @return numExpiryDays
  */
  @ApiModelProperty(value = "If allowed by campaign the channel can override the default expiry days. It will still be capped by the default campaign value.")


  public Integer getNumExpiryDays() {
    return numExpiryDays;
  }

  public void setNumExpiryDays(Integer numExpiryDays) {
    this.numExpiryDays = numExpiryDays;
  }

  public GiftCardsRequest smsMessage(String smsMessage) {
    this.smsMessage = smsMessage;
    return this;
  }

  /**
   * The message to override original sms message with. Used in request only.
   * @return smsMessage
  */
  @ApiModelProperty(value = "The message to override original sms message with. Used in request only.")


  public String getSmsMessage() {
    return smsMessage;
  }

  public void setSmsMessage(String smsMessage) {
    this.smsMessage = smsMessage;
  }

  public GiftCardsRequest sendSMS(Boolean sendSMS) {
    this.sendSMS = sendSMS;
    return this;
  }

  /**
   * Indicates whether an SMS must be sent on issue or not.
   * @return sendSMS
  */
  @ApiModelProperty(value = "Indicates whether an SMS must be sent on issue or not.")


  public Boolean getSendSMS() {
    return sendSMS;
  }

  public void setSendSMS(Boolean sendSMS) {
    this.sendSMS = sendSMS;
  }

  public GiftCardsRequest sendFollowUpSMS(Boolean sendFollowUpSMS) {
    this.sendFollowUpSMS = sendFollowUpSMS;
    return this;
  }

  /**
   * Indicates whether a follow-up SMS must be sent if the user partially redeems their gift card
   * @return sendFollowUpSMS
  */
  @ApiModelProperty(value = "Indicates whether a follow-up SMS must be sent if the user partially redeems their gift card")


  public Boolean getSendFollowUpSMS() {
    return sendFollowUpSMS;
  }

  public void setSendFollowUpSMS(Boolean sendFollowUpSMS) {
    this.sendFollowUpSMS = sendFollowUpSMS;
  }

  public GiftCardsRequest stateId(StateIdEnum stateId) {
    this.stateId = stateId;
    return this;
  }

  /**
   * The current state of the coupon. A (Active) D (Deactivated) E (Expired) R (Redeemed)
   * @return stateId
  */
  @ApiModelProperty(required = true, value = "The current state of the coupon. A (Active) D (Deactivated) E (Expired) R (Redeemed)")
  @NotNull


  public StateIdEnum getStateId() {
    return stateId;
  }

  public void setStateId(StateIdEnum stateId) {
    this.stateId = stateId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardsRequest giftCardsRequest = (GiftCardsRequest) o;
    return Objects.equals(this.campaignId, giftCardsRequest.campaignId) &&
        Objects.equals(this.balance, giftCardsRequest.balance) &&
        Objects.equals(this.userRef, giftCardsRequest.userRef) &&
        Objects.equals(this.mobileNumber, giftCardsRequest.mobileNumber) &&
        Objects.equals(this.numExpiryDays, giftCardsRequest.numExpiryDays) &&
        Objects.equals(this.smsMessage, giftCardsRequest.smsMessage) &&
        Objects.equals(this.sendSMS, giftCardsRequest.sendSMS) &&
        Objects.equals(this.sendFollowUpSMS, giftCardsRequest.sendFollowUpSMS) &&
        Objects.equals(this.stateId, giftCardsRequest.stateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campaignId, balance, userRef, mobileNumber, numExpiryDays, smsMessage, sendSMS, sendFollowUpSMS, stateId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsRequest {\n");
    
    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
    sb.append("    numExpiryDays: ").append(toIndentedString(numExpiryDays)).append("\n");
    sb.append("    smsMessage: ").append(toIndentedString(smsMessage)).append("\n");
    sb.append("    sendSMS: ").append(toIndentedString(sendSMS)).append("\n");
    sb.append("    sendFollowUpSMS: ").append(toIndentedString(sendFollowUpSMS)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
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

