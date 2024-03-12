package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * GiftCardsRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-10T08:13:28.436+02:00[Africa/Harare]")
public class GiftCardsRequest   {
  @JsonProperty("campaignId")
  private String campaignId;

  @JsonProperty("balance")
  private Long balance;

  @JsonProperty("userRef")
  private String userRef;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

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
        Objects.equals(this.stateId, giftCardsRequest.stateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campaignId, balance, userRef, mobileNumber, stateId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsRequest {\n");

    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
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

