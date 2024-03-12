package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.Objects;

/**
 * GiftCardsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-10T08:13:28.436+02:00[Africa/Harare]")
public class GiftCardsResponse   {
  @JsonProperty("giftcard")
  private GiftCardsResponseGiftcard giftcard;

  @JsonProperty("responseCode")
  private String responseCode;

  @JsonProperty("responseDesc")
  private String responseDesc;

  public GiftCardsResponse giftcard(GiftCardsResponseGiftcard giftcard) {
    this.giftcard = giftcard;
    return this;
  }

  /**
   * Get giftcard
   * @return giftcard
  */
  @ApiModelProperty(value = "")

  @Valid

  public GiftCardsResponseGiftcard getGiftcard() {
    return giftcard;
  }

  public void setGiftcard(GiftCardsResponseGiftcard giftcard) {
    this.giftcard = giftcard;
  }

  public GiftCardsResponse responseCode(String responseCode) {
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

  public GiftCardsResponse responseDesc(String responseDesc) {
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
    GiftCardsResponse giftCardsResponse = (GiftCardsResponse) o;
    return Objects.equals(this.giftcard, giftCardsResponse.giftcard) &&
        Objects.equals(this.responseCode, giftCardsResponse.responseCode) &&
        Objects.equals(this.responseDesc, giftCardsResponse.responseDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(giftcard, responseCode, responseDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardsResponse {\n");

    sb.append("    giftcard: ").append(toIndentedString(giftcard)).append("\n");
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

