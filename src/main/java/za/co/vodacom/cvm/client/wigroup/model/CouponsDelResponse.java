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
 * CouponsDelResponse
 */
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-07-12T21:30:03.237+02:00[Africa/Harare]"
)
public class CouponsDelResponse {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseDesc")
    private String responseDesc;

    public CouponsDelResponse responseCode(String responseCode) {
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

    public CouponsDelResponse responseDesc(String responseDesc) {
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
        CouponsDelResponse couponsDelResponse = (CouponsDelResponse) o;
        return (
            Objects.equals(this.responseCode, couponsDelResponse.responseCode) &&
            Objects.equals(this.responseDesc, couponsDelResponse.responseDesc)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseDesc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CouponsDelResponse {\n");

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
