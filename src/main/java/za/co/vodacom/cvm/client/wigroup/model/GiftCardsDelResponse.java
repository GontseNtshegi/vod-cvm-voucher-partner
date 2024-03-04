package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * GiftCardsDelResponse
 */
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-07-12T21:30:03.237+02:00[Africa/Harare]"
)
public class GiftCardsDelResponse {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseDesc")
    private String responseDesc;

    public GiftCardsDelResponse responseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    /**
     * The response code
     * @return responseCode
     */
    @ApiModelProperty(value = "The response code")
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public GiftCardsDelResponse responseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
        return this;
    }

    /**
     * The response description
     * @return responseDesc
     */
    @ApiModelProperty(value = "The response description")
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
        GiftCardsDelResponse giftCardsDelResponse = (GiftCardsDelResponse) o;
        return (
            Objects.equals(this.responseCode, giftCardsDelResponse.responseCode) &&
                Objects.equals(this.responseDesc, giftCardsDelResponse.responseDesc)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseDesc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class giftCardsDelResponse {\n");

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
