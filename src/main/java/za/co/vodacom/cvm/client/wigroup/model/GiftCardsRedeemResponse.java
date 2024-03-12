package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.Objects;

/**
 * GiftCardsRedeemResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-07T11:18:06.608682700+02:00[Africa/Harare]")
public class GiftCardsRedeemResponse   {
  @JsonProperty("token")
  private GiftCardsRedeemResponseToken token;

  @JsonProperty("responseCode")
  private String responseCode;

  @JsonProperty("responseDesc")
  private String responseDesc;

  public GiftCardsRedeemResponse token(GiftCardsRedeemResponseToken token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
  */
  @ApiModelProperty(value = "")

  @Valid

  public GiftCardsRedeemResponseToken getToken() {
    return token;
  }

  public void setToken(GiftCardsRedeemResponseToken token) {
    this.token = token;
  }

  public GiftCardsRedeemResponse responseCode(String responseCode) {
    this.responseCode = responseCode;
    return this;
  }

  /**
   * The date by which the voucher has to be redeemed
   * @return responseCode
  */
  @ApiModelProperty(value = "The date by which the voucher has to be redeemed")


  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public GiftCardsRedeemResponse responseDesc(String responseDesc) {
    this.responseDesc = responseDesc;
    return this;
  }

  /**
   * The date by which the voucher has to be redeemed
   * @return responseDesc
  */
  @ApiModelProperty(value = "The date by which the voucher has to be redeemed")


  public String getResponseDesc() {
    return responseDesc;
  }

  public void setResponseDesc(String responseDesc) {
    this.responseDesc = responseDesc;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardsRedeemResponse giftCardsRedeemResponse = (GiftCardsRedeemResponse) o;
    return Objects.equals(this.token, giftCardsRedeemResponse.token) &&
        Objects.equals(this.responseCode, giftCardsRedeemResponse.responseCode) &&
        Objects.equals(this.responseDesc, giftCardsRedeemResponse.responseDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, responseCode, responseDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsRedeemResponse {\n");

    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    responseCode: ").append(toIndentedString(responseCode)).append("\n");
    sb.append("    responseDesc: ").append(toIndentedString(responseDesc)).append("\n");
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

