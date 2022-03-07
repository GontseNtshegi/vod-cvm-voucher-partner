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
 * GiftCards from WiGroup
 */
@ApiModel(description = "GiftCards from WiGroup")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-07T11:18:06.608682700+02:00[Africa/Harare]")
public class GiftCardsRedeemResponseToken   {
  @JsonProperty("userRef")
  private String userRef;

  @JsonProperty("wiCode")
  private String wiCode;

  @JsonProperty("createDate")
  private String createDate;

  @JsonProperty("validTillDate")
  private String validTillDate;

  @JsonProperty("lastModifiedDate")
  private String lastModifiedDate;

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

  public GiftCardsRedeemResponseToken userRef(String userRef) {
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

  public GiftCardsRedeemResponseToken wiCode(String wiCode) {
    this.wiCode = wiCode;
    return this;
  }

  /**
   * The wiCode linked to the user token.
   * @return wiCode
  */
  @ApiModelProperty(value = "The wiCode linked to the user token.")


  public String getWiCode() {
    return wiCode;
  }

  public void setWiCode(String wiCode) {
    this.wiCode = wiCode;
  }

  public GiftCardsRedeemResponseToken createDate(String createDate) {
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

  public GiftCardsRedeemResponseToken validTillDate(String validTillDate) {
    this.validTillDate = validTillDate;
    return this;
  }

  /**
   * The date the wicode is valid to.
   * @return validTillDate
  */
  @ApiModelProperty(value = "The date the wicode is valid to.")


  public String getValidTillDate() {
    return validTillDate;
  }

  public void setValidTillDate(String validTillDate) {
    this.validTillDate = validTillDate;
  }

  public GiftCardsRedeemResponseToken lastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

  /**
   * The last modified date.
   * @return lastModifiedDate
  */
  @ApiModelProperty(value = "The last modified date.")


  public String getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public GiftCardsRedeemResponseToken stateId(StateIdEnum stateId) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardsRedeemResponseToken giftCardsRedeemResponseToken = (GiftCardsRedeemResponseToken) o;
    return Objects.equals(this.userRef, giftCardsRedeemResponseToken.userRef) &&
        Objects.equals(this.wiCode, giftCardsRedeemResponseToken.wiCode) &&
        Objects.equals(this.createDate, giftCardsRedeemResponseToken.createDate) &&
        Objects.equals(this.validTillDate, giftCardsRedeemResponseToken.validTillDate) &&
        Objects.equals(this.lastModifiedDate, giftCardsRedeemResponseToken.lastModifiedDate) &&
        Objects.equals(this.stateId, giftCardsRedeemResponseToken.stateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userRef, wiCode, createDate, validTillDate, lastModifiedDate, stateId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsRedeemResponseToken {\n");
    
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
    sb.append("    wiCode: ").append(toIndentedString(wiCode)).append("\n");
    sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
    sb.append("    validTillDate: ").append(toIndentedString(validTillDate)).append("\n");
    sb.append("    lastModifiedDate: ").append(toIndentedString(lastModifiedDate)).append("\n");
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

