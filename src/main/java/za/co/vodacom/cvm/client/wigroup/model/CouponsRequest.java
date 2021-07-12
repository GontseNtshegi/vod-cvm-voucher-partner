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
 * CouponsRequest
 */
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-07-12T21:30:03.237+02:00[Africa/Harare]"
)
public class CouponsRequest {

    @JsonProperty("campaignId")
    private String campaignId;

    @JsonProperty("userRef")
    private String userRef;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("smsMessage")
    private String smsMessage;

    @JsonProperty("sendSMS")
    private Boolean sendSMS;

    public CouponsRequest campaignId(String campaignId) {
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

    public CouponsRequest userRef(String userRef) {
        this.userRef = userRef;
        return this;
    }

    /**
     * A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns.
     * @return userRef
     */
    @ApiModelProperty(
        required = true,
        value = "A unique user reference as on the issuer channel system. This reference will be used to restrict user limits on campaigns."
    )
    @NotNull
    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public CouponsRequest mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    /**
     * The mobile number. The mobile number must be presented in International format.
     * @return mobileNumber
     */
    @ApiModelProperty(required = true, value = "The mobile number. The mobile number must be presented in International format.")
    @NotNull
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public CouponsRequest smsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
        return this;
    }

    /**
     * The message to override original sms message with. Used in request only.
     * @return smsMessage
     */
    @ApiModelProperty(required = true, value = "The message to override original sms message with. Used in request only.")
    @NotNull
    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public CouponsRequest sendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
        return this;
    }

    /**
     * Indicates whether an SMS must be sent on issue or not.
     * @return sendSMS
     */
    @ApiModelProperty(required = true, value = "Indicates whether an SMS must be sent on issue or not.")
    @NotNull
    public Boolean getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CouponsRequest couponsRequest = (CouponsRequest) o;
        return (
            Objects.equals(this.campaignId, couponsRequest.campaignId) &&
            Objects.equals(this.userRef, couponsRequest.userRef) &&
            Objects.equals(this.mobileNumber, couponsRequest.mobileNumber) &&
            Objects.equals(this.smsMessage, couponsRequest.smsMessage) &&
            Objects.equals(this.sendSMS, couponsRequest.sendSMS)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, userRef, mobileNumber, smsMessage, sendSMS);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CouponsRequest {\n");

        sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
        sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
        sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
        sb.append("    smsMessage: ").append(toIndentedString(smsMessage)).append("\n");
        sb.append("    sendSMS: ").append(toIndentedString(sendSMS)).append("\n");
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
