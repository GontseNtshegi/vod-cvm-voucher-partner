package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * CampaignInfo
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T13:34:34.750589900+02:00[Africa/Johannesburg]")
public class CampaignInfo {

  @JsonProperty("name")
  private String name;

  @JsonProperty("value")
  private String value;

  public CampaignInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */

  @Schema(name = "name", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CampaignInfo value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */

  @Schema(name = "value", required = false)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CampaignInfo campaignInfo = (CampaignInfo) o;
    return Objects.equals(this.name, campaignInfo.name) &&
        Objects.equals(this.value, campaignInfo.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CampaignInfo {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

