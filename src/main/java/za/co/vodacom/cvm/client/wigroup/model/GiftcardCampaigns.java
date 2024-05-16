package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * GiftcardCampaigns
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T13:34:34.750589900+02:00[Africa/Johannesburg]")
public class GiftcardCampaigns {

  @JsonProperty("minValueAllowedToIssue")
  private Integer minValueAllowedToIssue;

  @JsonProperty("maxValueAllowedToIssue")
  private Integer maxValueAllowedToIssue;

  @JsonProperty("totalAmountIssued")
  private Integer totalAmountIssued;

  @JsonProperty("totalAmountRedeemed")
  private Integer totalAmountRedeemed;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("termsAndConditions")
  private String termsAndConditions;

  @JsonProperty("imageURL")
  private String imageURL;

  @JsonProperty("createDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createDate;

  @JsonProperty("requireUserRef")
  private Boolean requireUserRef;

  @JsonProperty("allowedUsersRestricted")
  private Boolean allowedUsersRestricted;

  @JsonProperty("maxNumberPerUser")
  private Integer maxNumberPerUser;

  @JsonProperty("maxLivePerUser")
  private Integer maxLivePerUser;

  @JsonProperty("campaignType")
  private String campaignType;

  @JsonProperty("minRank")
  private Integer minRank;

  @JsonProperty("categories")
  @Valid
  private List<Categories> categories = null;

  @JsonProperty("totalLive")
  private Integer totalLive;

  @JsonProperty("totalRedeemed")
  private Integer totalRedeemed;

  @JsonProperty("totalExpired")
  private Integer totalExpired;

  @JsonProperty("totalIssued")
  private Integer totalIssued;

  @JsonProperty("campaignInfo")
  @Valid
  private List<CampaignInfo> campaignInfo = null;

  @JsonProperty("stateId")
  private String stateId;

  @JsonProperty("allowExpiryDateOverride")
  private Boolean allowExpiryDateOverride;

  @JsonProperty("expiryDays")
  private Integer expiryDays;

  public GiftcardCampaigns minValueAllowedToIssue(Integer minValueAllowedToIssue) {
    this.minValueAllowedToIssue = minValueAllowedToIssue;
    return this;
  }

  /**
   * Get minValueAllowedToIssue
   * @return minValueAllowedToIssue
  */

  @Schema(name = "minValueAllowedToIssue", required = false)
  public Integer getMinValueAllowedToIssue() {
    return minValueAllowedToIssue;
  }

  public void setMinValueAllowedToIssue(Integer minValueAllowedToIssue) {
    this.minValueAllowedToIssue = minValueAllowedToIssue;
  }

  public GiftcardCampaigns maxValueAllowedToIssue(Integer maxValueAllowedToIssue) {
    this.maxValueAllowedToIssue = maxValueAllowedToIssue;
    return this;
  }

  /**
   * Get maxValueAllowedToIssue
   * @return maxValueAllowedToIssue
  */

  @Schema(name = "maxValueAllowedToIssue", required = false)
  public Integer getMaxValueAllowedToIssue() {
    return maxValueAllowedToIssue;
  }

  public void setMaxValueAllowedToIssue(Integer maxValueAllowedToIssue) {
    this.maxValueAllowedToIssue = maxValueAllowedToIssue;
  }

  public GiftcardCampaigns totalAmountIssued(Integer totalAmountIssued) {
    this.totalAmountIssued = totalAmountIssued;
    return this;
  }

  /**
   * Get totalAmountIssued
   * @return totalAmountIssued
  */

  @Schema(name = "totalAmountIssued", required = false)
  public Integer getTotalAmountIssued() {
    return totalAmountIssued;
  }

  public void setTotalAmountIssued(Integer totalAmountIssued) {
    this.totalAmountIssued = totalAmountIssued;
  }

  public GiftcardCampaigns totalAmountRedeemed(Integer totalAmountRedeemed) {
    this.totalAmountRedeemed = totalAmountRedeemed;
    return this;
  }

  /**
   * Get totalAmountRedeemed
   * @return totalAmountRedeemed
  */

  @Schema(name = "totalAmountRedeemed", required = false)
  public Integer getTotalAmountRedeemed() {
    return totalAmountRedeemed;
  }

  public void setTotalAmountRedeemed(Integer totalAmountRedeemed) {
    this.totalAmountRedeemed = totalAmountRedeemed;
  }

  public GiftcardCampaigns id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */

  @Schema(name = "id", required = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GiftcardCampaigns name(String name) {
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

  public GiftcardCampaigns description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */

  @Schema(name = "description", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public GiftcardCampaigns termsAndConditions(String termsAndConditions) {
    this.termsAndConditions = termsAndConditions;
    return this;
  }

  /**
   * Get termsAndConditions
   * @return termsAndConditions
  */

  @Schema(name = "termsAndConditions", required = false)
  public String getTermsAndConditions() {
    return termsAndConditions;
  }

  public void setTermsAndConditions(String termsAndConditions) {
    this.termsAndConditions = termsAndConditions;
  }

  public GiftcardCampaigns imageURL(String imageURL) {
    this.imageURL = imageURL;
    return this;
  }

  /**
   * Get imageURL
   * @return imageURL
  */

  @Schema(name = "imageURL", required = false)
  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public GiftcardCampaigns createDate(OffsetDateTime createDate) {
    this.createDate = createDate;
    return this;
  }

  /**
   * Get createDate
   * @return createDate
  */
  @Valid
  @Schema(name = "createDate", required = false)
  public OffsetDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(OffsetDateTime createDate) {
    this.createDate = createDate;
  }

  public GiftcardCampaigns requireUserRef(Boolean requireUserRef) {
    this.requireUserRef = requireUserRef;
    return this;
  }

  /**
   * Get requireUserRef
   * @return requireUserRef
  */

  @Schema(name = "requireUserRef", required = false)
  public Boolean getRequireUserRef() {
    return requireUserRef;
  }

  public void setRequireUserRef(Boolean requireUserRef) {
    this.requireUserRef = requireUserRef;
  }

  public GiftcardCampaigns allowedUsersRestricted(Boolean allowedUsersRestricted) {
    this.allowedUsersRestricted = allowedUsersRestricted;
    return this;
  }

  /**
   * Get allowedUsersRestricted
   * @return allowedUsersRestricted
  */

  @Schema(name = "allowedUsersRestricted", required = false)
  public Boolean getAllowedUsersRestricted() {
    return allowedUsersRestricted;
  }

  public void setAllowedUsersRestricted(Boolean allowedUsersRestricted) {
    this.allowedUsersRestricted = allowedUsersRestricted;
  }

  public GiftcardCampaigns maxNumberPerUser(Integer maxNumberPerUser) {
    this.maxNumberPerUser = maxNumberPerUser;
    return this;
  }

  /**
   * Get maxNumberPerUser
   * @return maxNumberPerUser
  */

  @Schema(name = "maxNumberPerUser", required = false)
  public Integer getMaxNumberPerUser() {
    return maxNumberPerUser;
  }

  public void setMaxNumberPerUser(Integer maxNumberPerUser) {
    this.maxNumberPerUser = maxNumberPerUser;
  }

  public GiftcardCampaigns maxLivePerUser(Integer maxLivePerUser) {
    this.maxLivePerUser = maxLivePerUser;
    return this;
  }

  /**
   * Get maxLivePerUser
   * @return maxLivePerUser
  */

  @Schema(name = "maxLivePerUser", required = false)
  public Integer getMaxLivePerUser() {
    return maxLivePerUser;
  }

  public void setMaxLivePerUser(Integer maxLivePerUser) {
    this.maxLivePerUser = maxLivePerUser;
  }

  public GiftcardCampaigns campaignType(String campaignType) {
    this.campaignType = campaignType;
    return this;
  }

  /**
   * Get campaignType
   * @return campaignType
  */

  @Schema(name = "campaignType", required = false)
  public String getCampaignType() {
    return campaignType;
  }

  public void setCampaignType(String campaignType) {
    this.campaignType = campaignType;
  }

  public GiftcardCampaigns minRank(Integer minRank) {
    this.minRank = minRank;
    return this;
  }

  /**
   * Get minRank
   * @return minRank
  */

  @Schema(name = "minRank", required = false)
  public Integer getMinRank() {
    return minRank;
  }

  public void setMinRank(Integer minRank) {
    this.minRank = minRank;
  }

  public GiftcardCampaigns categories(List<Categories> categories) {
    this.categories = categories;
    return this;
  }

  public GiftcardCampaigns addCategoriesItem(Categories categoriesItem) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(categoriesItem);
    return this;
  }

  /**
   * Get categories
   * @return categories
  */
  @Valid
  @Schema(name = "categories", required = false)
  public List<Categories> getCategories() {
    return categories;
  }

  public void setCategories(List<Categories> categories) {
    this.categories = categories;
  }

  public GiftcardCampaigns totalLive(Integer totalLive) {
    this.totalLive = totalLive;
    return this;
  }

  /**
   * Get totalLive
   * @return totalLive
  */

  @Schema(name = "totalLive", required = false)
  public Integer getTotalLive() {
    return totalLive;
  }

  public void setTotalLive(Integer totalLive) {
    this.totalLive = totalLive;
  }

  public GiftcardCampaigns totalRedeemed(Integer totalRedeemed) {
    this.totalRedeemed = totalRedeemed;
    return this;
  }

  /**
   * Get totalRedeemed
   * @return totalRedeemed
  */

  @Schema(name = "totalRedeemed", required = false)
  public Integer getTotalRedeemed() {
    return totalRedeemed;
  }

  public void setTotalRedeemed(Integer totalRedeemed) {
    this.totalRedeemed = totalRedeemed;
  }

  public GiftcardCampaigns totalExpired(Integer totalExpired) {
    this.totalExpired = totalExpired;
    return this;
  }

  /**
   * Get totalExpired
   * @return totalExpired
  */

  @Schema(name = "totalExpired", required = false)
  public Integer getTotalExpired() {
    return totalExpired;
  }

  public void setTotalExpired(Integer totalExpired) {
    this.totalExpired = totalExpired;
  }

  public GiftcardCampaigns totalIssued(Integer totalIssued) {
    this.totalIssued = totalIssued;
    return this;
  }

  /**
   * Get totalIssued
   * @return totalIssued
  */

  @Schema(name = "totalIssued", required = false)
  public Integer getTotalIssued() {
    return totalIssued;
  }

  public void setTotalIssued(Integer totalIssued) {
    this.totalIssued = totalIssued;
  }

  public GiftcardCampaigns campaignInfo(List<CampaignInfo> campaignInfo) {
    this.campaignInfo = campaignInfo;
    return this;
  }

  public GiftcardCampaigns addCampaignInfoItem(CampaignInfo campaignInfoItem) {
    if (this.campaignInfo == null) {
      this.campaignInfo = new ArrayList<>();
    }
    this.campaignInfo.add(campaignInfoItem);
    return this;
  }

  /**
   * Get campaignInfo
   * @return campaignInfo
  */
  @Valid
  @Schema(name = "campaignInfo", required = false)
  public List<CampaignInfo> getCampaignInfo() {
    return campaignInfo;
  }

  public void setCampaignInfo(List<CampaignInfo> campaignInfo) {
    this.campaignInfo = campaignInfo;
  }

  public GiftcardCampaigns stateId(String stateId) {
    this.stateId = stateId;
    return this;
  }

  /**
   * Get stateId
   * @return stateId
  */

  @Schema(name = "stateId", required = false)
  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public GiftcardCampaigns allowExpiryDateOverride(Boolean allowExpiryDateOverride) {
    this.allowExpiryDateOverride = allowExpiryDateOverride;
    return this;
  }

  /**
   * Get allowExpiryDateOverride
   * @return allowExpiryDateOverride
  */

  @Schema(name = "allowExpiryDateOverride", required = false)
  public Boolean getAllowExpiryDateOverride() {
    return allowExpiryDateOverride;
  }

  public void setAllowExpiryDateOverride(Boolean allowExpiryDateOverride) {
    this.allowExpiryDateOverride = allowExpiryDateOverride;
  }

  public GiftcardCampaigns expiryDays(Integer expiryDays) {
    this.expiryDays = expiryDays;
    return this;
  }

  /**
   * Get expiryDays
   * @return expiryDays
  */

  @Schema(name = "expiryDays", required = false)
  public Integer getExpiryDays() {
    return expiryDays;
  }

  public void setExpiryDays(Integer expiryDays) {
    this.expiryDays = expiryDays;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftcardCampaigns giftcardCampaigns = (GiftcardCampaigns) o;
    return Objects.equals(this.minValueAllowedToIssue, giftcardCampaigns.minValueAllowedToIssue) &&
        Objects.equals(this.maxValueAllowedToIssue, giftcardCampaigns.maxValueAllowedToIssue) &&
        Objects.equals(this.totalAmountIssued, giftcardCampaigns.totalAmountIssued) &&
        Objects.equals(this.totalAmountRedeemed, giftcardCampaigns.totalAmountRedeemed) &&
        Objects.equals(this.id, giftcardCampaigns.id) &&
        Objects.equals(this.name, giftcardCampaigns.name) &&
        Objects.equals(this.description, giftcardCampaigns.description) &&
        Objects.equals(this.termsAndConditions, giftcardCampaigns.termsAndConditions) &&
        Objects.equals(this.imageURL, giftcardCampaigns.imageURL) &&
        Objects.equals(this.createDate, giftcardCampaigns.createDate) &&
        Objects.equals(this.requireUserRef, giftcardCampaigns.requireUserRef) &&
        Objects.equals(this.allowedUsersRestricted, giftcardCampaigns.allowedUsersRestricted) &&
        Objects.equals(this.maxNumberPerUser, giftcardCampaigns.maxNumberPerUser) &&
        Objects.equals(this.maxLivePerUser, giftcardCampaigns.maxLivePerUser) &&
        Objects.equals(this.campaignType, giftcardCampaigns.campaignType) &&
        Objects.equals(this.minRank, giftcardCampaigns.minRank) &&
        Objects.equals(this.categories, giftcardCampaigns.categories) &&
        Objects.equals(this.totalLive, giftcardCampaigns.totalLive) &&
        Objects.equals(this.totalRedeemed, giftcardCampaigns.totalRedeemed) &&
        Objects.equals(this.totalExpired, giftcardCampaigns.totalExpired) &&
        Objects.equals(this.totalIssued, giftcardCampaigns.totalIssued) &&
        Objects.equals(this.campaignInfo, giftcardCampaigns.campaignInfo) &&
        Objects.equals(this.stateId, giftcardCampaigns.stateId) &&
        Objects.equals(this.allowExpiryDateOverride, giftcardCampaigns.allowExpiryDateOverride) &&
        Objects.equals(this.expiryDays, giftcardCampaigns.expiryDays);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minValueAllowedToIssue, maxValueAllowedToIssue, totalAmountIssued, totalAmountRedeemed, id, name, description, termsAndConditions, imageURL, createDate, requireUserRef, allowedUsersRestricted, maxNumberPerUser, maxLivePerUser, campaignType, minRank, categories, totalLive, totalRedeemed, totalExpired, totalIssued, campaignInfo, stateId, allowExpiryDateOverride, expiryDays);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftcardCampaigns {\n");
    sb.append("    minValueAllowedToIssue: ").append(toIndentedString(minValueAllowedToIssue)).append("\n");
    sb.append("    maxValueAllowedToIssue: ").append(toIndentedString(maxValueAllowedToIssue)).append("\n");
    sb.append("    totalAmountIssued: ").append(toIndentedString(totalAmountIssued)).append("\n");
    sb.append("    totalAmountRedeemed: ").append(toIndentedString(totalAmountRedeemed)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    termsAndConditions: ").append(toIndentedString(termsAndConditions)).append("\n");
    sb.append("    imageURL: ").append(toIndentedString(imageURL)).append("\n");
    sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
    sb.append("    requireUserRef: ").append(toIndentedString(requireUserRef)).append("\n");
    sb.append("    allowedUsersRestricted: ").append(toIndentedString(allowedUsersRestricted)).append("\n");
    sb.append("    maxNumberPerUser: ").append(toIndentedString(maxNumberPerUser)).append("\n");
    sb.append("    maxLivePerUser: ").append(toIndentedString(maxLivePerUser)).append("\n");
    sb.append("    campaignType: ").append(toIndentedString(campaignType)).append("\n");
    sb.append("    minRank: ").append(toIndentedString(minRank)).append("\n");
    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
    sb.append("    totalLive: ").append(toIndentedString(totalLive)).append("\n");
    sb.append("    totalRedeemed: ").append(toIndentedString(totalRedeemed)).append("\n");
    sb.append("    totalExpired: ").append(toIndentedString(totalExpired)).append("\n");
    sb.append("    totalIssued: ").append(toIndentedString(totalIssued)).append("\n");
    sb.append("    campaignInfo: ").append(toIndentedString(campaignInfo)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
    sb.append("    allowExpiryDateOverride: ").append(toIndentedString(allowExpiryDateOverride)).append("\n");
    sb.append("    expiryDays: ").append(toIndentedString(expiryDays)).append("\n");
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

