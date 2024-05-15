package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import za.co.vodacom.webfrontend.client.wigroup.model.GiftcardCampaigns;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.Objects;

/**
 * GiftcardCampaignsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T14:53:18.276609+02:00[Africa/Johannesburg]")
public class GiftcardCampaignsResponse {

  @JsonProperty("giftcardCampaign")
  private GiftcardCampaigns giftcardCampaign;

  @JsonProperty("responseCode")
  private String responseCode;

  @JsonProperty("responseDesc")
  private String responseDesc;

  public GiftcardCampaignsResponse giftcardCampaign(GiftcardCampaigns giftcardCampaign) {
    this.giftcardCampaign = giftcardCampaign;
    return this;
  }

  /**
   * Get giftcardCampaign
   * @return giftcardCampaign
  */
  @Valid
  @Schema(name = "giftcardCampaign", required = false)
  public GiftcardCampaigns getGiftcardCampaign() {
    return giftcardCampaign;
  }

  public void setGiftcardCampaign(GiftcardCampaigns giftcardCampaign) {
    this.giftcardCampaign = giftcardCampaign;
  }

  public GiftcardCampaignsResponse responseCode(String responseCode) {
    this.responseCode = responseCode;
    return this;
  }

  /**
   * Get responseCode
   * @return responseCode
  */

  @Schema(name = "responseCode", required = false)
  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public GiftcardCampaignsResponse responseDesc(String responseDesc) {
    this.responseDesc = responseDesc;
    return this;
  }

  /**
   * Get responseDesc
   * @return responseDesc
  */

  @Schema(name = "responseDesc", required = false)
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
    GiftcardCampaignsResponse giftcardCampaignsResponse = (GiftcardCampaignsResponse) o;
    return Objects.equals(this.giftcardCampaign, giftcardCampaignsResponse.giftcardCampaign) &&
        Objects.equals(this.responseCode, giftcardCampaignsResponse.responseCode) &&
        Objects.equals(this.responseDesc, giftcardCampaignsResponse.responseDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(giftcardCampaign, responseCode, responseDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftcardCampaignsResponse {\n");
    sb.append("    giftcardCampaign: ").append(toIndentedString(giftcardCampaign)).append("\n");
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

