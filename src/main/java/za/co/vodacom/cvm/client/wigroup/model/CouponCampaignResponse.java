package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import za.co.vodacom.webfrontend.client.wigroup.model.CouponCampaigns;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.Objects;

/**
 * CouponCampaignResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T14:53:18.276609+02:00[Africa/Johannesburg]")
public class CouponCampaignResponse {

  @JsonProperty("couponCampaign")
  private CouponCampaigns couponCampaign;

  @JsonProperty("responseCode")
  private String responseCode;

  @JsonProperty("responseDesc")
  private String responseDesc;

  public CouponCampaignResponse couponCampaign(CouponCampaigns couponCampaign) {
    this.couponCampaign = couponCampaign;
    return this;
  }

  /**
   * Get couponCampaign
   * @return couponCampaign
  */
  @Valid
  @Schema(name = "couponCampaign", required = false)
  public CouponCampaigns getCouponCampaign() {
    return couponCampaign;
  }

  public void setCouponCampaign(CouponCampaigns couponCampaign) {
    this.couponCampaign = couponCampaign;
  }

  public CouponCampaignResponse responseCode(String responseCode) {
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

  public CouponCampaignResponse responseDesc(String responseDesc) {
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
    CouponCampaignResponse couponCampaignResponse = (CouponCampaignResponse) o;
    return Objects.equals(this.couponCampaign, couponCampaignResponse.couponCampaign) &&
        Objects.equals(this.responseCode, couponCampaignResponse.responseCode) &&
        Objects.equals(this.responseDesc, couponCampaignResponse.responseDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(couponCampaign, responseCode, responseDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CouponCampaignResponse {\n");
    sb.append("    couponCampaign: ").append(toIndentedString(couponCampaign)).append("\n");
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

