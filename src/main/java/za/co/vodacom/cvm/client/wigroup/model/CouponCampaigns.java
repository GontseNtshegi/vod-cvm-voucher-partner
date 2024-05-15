package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import za.co.vodacom.webfrontend.client.wigroup.model.CampaignInfo;
import za.co.vodacom.webfrontend.client.wigroup.model.Categories;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CouponCampaigns
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T15:02:20.639412400+02:00[Africa/Johannesburg]")
public class CouponCampaigns {

  @JsonProperty("allowExpiryDateOverride")
  private Boolean allowExpiryDateOverride;

  @JsonProperty("expiryDays")
  private Integer expiryDays;

  @JsonProperty("totalViewed")
  private Integer totalViewed;

  @JsonProperty("issueFromDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime issueFromDate;

  @JsonProperty("issueToDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime issueToDate;

  @JsonProperty("redeemFromDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime redeemFromDate;

  @JsonProperty("redeemToDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime redeemToDate;

  @JsonProperty("minBasketValue")
  private Integer minBasketValue;

  @JsonProperty("maxBasketValue")
  private Integer maxBasketValue;

  @JsonProperty("maxRedemptionsPerUserPerDay")
  private Integer maxRedemptionsPerUserPerDay;

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

  @JsonProperty("maxAllowedToIssue")
  private Integer maxAllowedToIssue;

  @JsonProperty("maxAllowedToIssueDaily")
  private Integer maxAllowedToIssueDaily;

  @JsonProperty("maxRedemptionRuleAmount")
  private Integer maxRedemptionRuleAmount;

  @JsonProperty("discountType")
  private String discountType;

  @JsonProperty("percentageDiscount")
  private Integer percentageDiscount;

  @JsonProperty("totalLive")
  private Integer totalLive;

  @JsonProperty("totalRedeemed")
  private Integer totalRedeemed;

  @JsonProperty("totalExpired")
  private Integer totalExpired;

  @JsonProperty("totalIssued")
  private Integer totalIssued;

  @JsonProperty("totalIssuedToday")
  private Integer totalIssuedToday;

  @JsonProperty("campaignInfo")
  @Valid
  private List<CampaignInfo> campaignInfo = null;

  @JsonProperty("stateId")
  private String stateId;

  public CouponCampaigns allowExpiryDateOverride(Boolean allowExpiryDateOverride) {
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

  public CouponCampaigns expiryDays(Integer expiryDays) {
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

  public CouponCampaigns totalViewed(Integer totalViewed) {
    this.totalViewed = totalViewed;
    return this;
  }

  /**
   * Get totalViewed
   * @return totalViewed
  */

  @Schema(name = "totalViewed", required = false)
  public Integer getTotalViewed() {
    return totalViewed;
  }

  public void setTotalViewed(Integer totalViewed) {
    this.totalViewed = totalViewed;
  }

  public CouponCampaigns issueFromDate(OffsetDateTime issueFromDate) {
    this.issueFromDate = issueFromDate;
    return this;
  }

  /**
   * Get issueFromDate
   * @return issueFromDate
  */
  @Valid
  @Schema(name = "issueFromDate", required = false)
  public OffsetDateTime getIssueFromDate() {
    return issueFromDate;
  }

  public void setIssueFromDate(OffsetDateTime issueFromDate) {
    this.issueFromDate = issueFromDate;
  }

  public CouponCampaigns issueToDate(OffsetDateTime issueToDate) {
    this.issueToDate = issueToDate;
    return this;
  }

  /**
   * Get issueToDate
   * @return issueToDate
  */
  @Valid
  @Schema(name = "issueToDate", required = false)
  public OffsetDateTime getIssueToDate() {
    return issueToDate;
  }

  public void setIssueToDate(OffsetDateTime issueToDate) {
    this.issueToDate = issueToDate;
  }

  public CouponCampaigns redeemFromDate(OffsetDateTime redeemFromDate) {
    this.redeemFromDate = redeemFromDate;
    return this;
  }

  /**
   * Get redeemFromDate
   * @return redeemFromDate
  */
  @Valid
  @Schema(name = "redeemFromDate", required = false)
  public OffsetDateTime getRedeemFromDate() {
    return redeemFromDate;
  }

  public void setRedeemFromDate(OffsetDateTime redeemFromDate) {
    this.redeemFromDate = redeemFromDate;
  }

  public CouponCampaigns redeemToDate(OffsetDateTime redeemToDate) {
    this.redeemToDate = redeemToDate;
    return this;
  }

  /**
   * Get redeemToDate
   * @return redeemToDate
  */
  @Valid
  @Schema(name = "redeemToDate", required = false)
  public OffsetDateTime getRedeemToDate() {
    return redeemToDate;
  }

  public void setRedeemToDate(OffsetDateTime redeemToDate) {
    this.redeemToDate = redeemToDate;
  }

  public CouponCampaigns minBasketValue(Integer minBasketValue) {
    this.minBasketValue = minBasketValue;
    return this;
  }

  /**
   * Get minBasketValue
   * @return minBasketValue
  */

  @Schema(name = "minBasketValue", required = false)
  public Integer getMinBasketValue() {
    return minBasketValue;
  }

  public void setMinBasketValue(Integer minBasketValue) {
    this.minBasketValue = minBasketValue;
  }

  public CouponCampaigns maxBasketValue(Integer maxBasketValue) {
    this.maxBasketValue = maxBasketValue;
    return this;
  }

  /**
   * Get maxBasketValue
   * @return maxBasketValue
  */

  @Schema(name = "maxBasketValue", required = false)
  public Integer getMaxBasketValue() {
    return maxBasketValue;
  }

  public void setMaxBasketValue(Integer maxBasketValue) {
    this.maxBasketValue = maxBasketValue;
  }

  public CouponCampaigns maxRedemptionsPerUserPerDay(Integer maxRedemptionsPerUserPerDay) {
    this.maxRedemptionsPerUserPerDay = maxRedemptionsPerUserPerDay;
    return this;
  }

  /**
   * Get maxRedemptionsPerUserPerDay
   * @return maxRedemptionsPerUserPerDay
  */

  @Schema(name = "maxRedemptionsPerUserPerDay", required = false)
  public Integer getMaxRedemptionsPerUserPerDay() {
    return maxRedemptionsPerUserPerDay;
  }

  public void setMaxRedemptionsPerUserPerDay(Integer maxRedemptionsPerUserPerDay) {
    this.maxRedemptionsPerUserPerDay = maxRedemptionsPerUserPerDay;
  }

  public CouponCampaigns id(Long id) {
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

  public CouponCampaigns name(String name) {
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

  public CouponCampaigns description(String description) {
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

  public CouponCampaigns termsAndConditions(String termsAndConditions) {
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

  public CouponCampaigns imageURL(String imageURL) {
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

  public CouponCampaigns createDate(OffsetDateTime createDate) {
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

  public CouponCampaigns requireUserRef(Boolean requireUserRef) {
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

  public CouponCampaigns allowedUsersRestricted(Boolean allowedUsersRestricted) {
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

  public CouponCampaigns maxNumberPerUser(Integer maxNumberPerUser) {
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

  public CouponCampaigns maxLivePerUser(Integer maxLivePerUser) {
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

  public CouponCampaigns campaignType(String campaignType) {
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

  public CouponCampaigns minRank(Integer minRank) {
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

  public CouponCampaigns categories(List<Categories> categories) {
    this.categories = categories;
    return this;
  }

  public CouponCampaigns addCategoriesItem(Categories categoriesItem) {
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

  public CouponCampaigns maxAllowedToIssue(Integer maxAllowedToIssue) {
    this.maxAllowedToIssue = maxAllowedToIssue;
    return this;
  }

  /**
   * Get maxAllowedToIssue
   * @return maxAllowedToIssue
  */

  @Schema(name = "maxAllowedToIssue", required = false)
  public Integer getMaxAllowedToIssue() {
    return maxAllowedToIssue;
  }

  public void setMaxAllowedToIssue(Integer maxAllowedToIssue) {
    this.maxAllowedToIssue = maxAllowedToIssue;
  }

  public CouponCampaigns maxAllowedToIssueDaily(Integer maxAllowedToIssueDaily) {
    this.maxAllowedToIssueDaily = maxAllowedToIssueDaily;
    return this;
  }

  /**
   * Get maxAllowedToIssueDaily
   * @return maxAllowedToIssueDaily
  */

  @Schema(name = "maxAllowedToIssueDaily", required = false)
  public Integer getMaxAllowedToIssueDaily() {
    return maxAllowedToIssueDaily;
  }

  public void setMaxAllowedToIssueDaily(Integer maxAllowedToIssueDaily) {
    this.maxAllowedToIssueDaily = maxAllowedToIssueDaily;
  }

  public CouponCampaigns maxRedemptionRuleAmount(Integer maxRedemptionRuleAmount) {
    this.maxRedemptionRuleAmount = maxRedemptionRuleAmount;
    return this;
  }

  /**
   * Get maxRedemptionRuleAmount
   * @return maxRedemptionRuleAmount
  */

  @Schema(name = "maxRedemptionRuleAmount", required = false)
  public Integer getMaxRedemptionRuleAmount() {
    return maxRedemptionRuleAmount;
  }

  public void setMaxRedemptionRuleAmount(Integer maxRedemptionRuleAmount) {
    this.maxRedemptionRuleAmount = maxRedemptionRuleAmount;
  }

  public CouponCampaigns discountType(String discountType) {
    this.discountType = discountType;
    return this;
  }

  /**
   * Get discountType
   * @return discountType
  */

  @Schema(name = "discountType", required = false)
  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  public CouponCampaigns percentageDiscount(Integer percentageDiscount) {
    this.percentageDiscount = percentageDiscount;
    return this;
  }

  /**
   * Get percentageDiscount
   * @return percentageDiscount
  */

  @Schema(name = "percentageDiscount", required = false)
  public Integer getPercentageDiscount() {
    return percentageDiscount;
  }

  public void setPercentageDiscount(Integer percentageDiscount) {
    this.percentageDiscount = percentageDiscount;
  }

  public CouponCampaigns totalLive(Integer totalLive) {
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

  public CouponCampaigns totalRedeemed(Integer totalRedeemed) {
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

  public CouponCampaigns totalExpired(Integer totalExpired) {
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

  public CouponCampaigns totalIssued(Integer totalIssued) {
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

  public CouponCampaigns totalIssuedToday(Integer totalIssuedToday) {
    this.totalIssuedToday = totalIssuedToday;
    return this;
  }

  /**
   * Get totalIssuedToday
   * @return totalIssuedToday
  */

  @Schema(name = "totalIssuedToday", required = false)
  public Integer getTotalIssuedToday() {
    return totalIssuedToday;
  }

  public void setTotalIssuedToday(Integer totalIssuedToday) {
    this.totalIssuedToday = totalIssuedToday;
  }

  public CouponCampaigns campaignInfo(List<CampaignInfo> campaignInfo) {
    this.campaignInfo = campaignInfo;
    return this;
  }

  public CouponCampaigns addCampaignInfoItem(CampaignInfo campaignInfoItem) {
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

  public CouponCampaigns stateId(String stateId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CouponCampaigns couponCampaigns = (CouponCampaigns) o;
    return Objects.equals(this.allowExpiryDateOverride, couponCampaigns.allowExpiryDateOverride) &&
        Objects.equals(this.expiryDays, couponCampaigns.expiryDays) &&
        Objects.equals(this.totalViewed, couponCampaigns.totalViewed) &&
        Objects.equals(this.issueFromDate, couponCampaigns.issueFromDate) &&
        Objects.equals(this.issueToDate, couponCampaigns.issueToDate) &&
        Objects.equals(this.redeemFromDate, couponCampaigns.redeemFromDate) &&
        Objects.equals(this.redeemToDate, couponCampaigns.redeemToDate) &&
        Objects.equals(this.minBasketValue, couponCampaigns.minBasketValue) &&
        Objects.equals(this.maxBasketValue, couponCampaigns.maxBasketValue) &&
        Objects.equals(this.maxRedemptionsPerUserPerDay, couponCampaigns.maxRedemptionsPerUserPerDay) &&
        Objects.equals(this.id, couponCampaigns.id) &&
        Objects.equals(this.name, couponCampaigns.name) &&
        Objects.equals(this.description, couponCampaigns.description) &&
        Objects.equals(this.termsAndConditions, couponCampaigns.termsAndConditions) &&
        Objects.equals(this.imageURL, couponCampaigns.imageURL) &&
        Objects.equals(this.createDate, couponCampaigns.createDate) &&
        Objects.equals(this.requireUserRef, couponCampaigns.requireUserRef) &&
        Objects.equals(this.allowedUsersRestricted, couponCampaigns.allowedUsersRestricted) &&
        Objects.equals(this.maxNumberPerUser, couponCampaigns.maxNumberPerUser) &&
        Objects.equals(this.maxLivePerUser, couponCampaigns.maxLivePerUser) &&
        Objects.equals(this.campaignType, couponCampaigns.campaignType) &&
        Objects.equals(this.minRank, couponCampaigns.minRank) &&
        Objects.equals(this.categories, couponCampaigns.categories) &&
        Objects.equals(this.maxAllowedToIssue, couponCampaigns.maxAllowedToIssue) &&
        Objects.equals(this.maxAllowedToIssueDaily, couponCampaigns.maxAllowedToIssueDaily) &&
        Objects.equals(this.maxRedemptionRuleAmount, couponCampaigns.maxRedemptionRuleAmount) &&
        Objects.equals(this.discountType, couponCampaigns.discountType) &&
        Objects.equals(this.percentageDiscount, couponCampaigns.percentageDiscount) &&
        Objects.equals(this.totalLive, couponCampaigns.totalLive) &&
        Objects.equals(this.totalRedeemed, couponCampaigns.totalRedeemed) &&
        Objects.equals(this.totalExpired, couponCampaigns.totalExpired) &&
        Objects.equals(this.totalIssued, couponCampaigns.totalIssued) &&
        Objects.equals(this.totalIssuedToday, couponCampaigns.totalIssuedToday) &&
        Objects.equals(this.campaignInfo, couponCampaigns.campaignInfo) &&
        Objects.equals(this.stateId, couponCampaigns.stateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowExpiryDateOverride, expiryDays, totalViewed, issueFromDate, issueToDate, redeemFromDate, redeemToDate, minBasketValue, maxBasketValue, maxRedemptionsPerUserPerDay, id, name, description, termsAndConditions, imageURL, createDate, requireUserRef, allowedUsersRestricted, maxNumberPerUser, maxLivePerUser, campaignType, minRank, categories, maxAllowedToIssue, maxAllowedToIssueDaily, maxRedemptionRuleAmount, discountType, percentageDiscount, totalLive, totalRedeemed, totalExpired, totalIssued, totalIssuedToday, campaignInfo, stateId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CouponCampaigns {\n");
    sb.append("    allowExpiryDateOverride: ").append(toIndentedString(allowExpiryDateOverride)).append("\n");
    sb.append("    expiryDays: ").append(toIndentedString(expiryDays)).append("\n");
    sb.append("    totalViewed: ").append(toIndentedString(totalViewed)).append("\n");
    sb.append("    issueFromDate: ").append(toIndentedString(issueFromDate)).append("\n");
    sb.append("    issueToDate: ").append(toIndentedString(issueToDate)).append("\n");
    sb.append("    redeemFromDate: ").append(toIndentedString(redeemFromDate)).append("\n");
    sb.append("    redeemToDate: ").append(toIndentedString(redeemToDate)).append("\n");
    sb.append("    minBasketValue: ").append(toIndentedString(minBasketValue)).append("\n");
    sb.append("    maxBasketValue: ").append(toIndentedString(maxBasketValue)).append("\n");
    sb.append("    maxRedemptionsPerUserPerDay: ").append(toIndentedString(maxRedemptionsPerUserPerDay)).append("\n");
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
    sb.append("    maxAllowedToIssue: ").append(toIndentedString(maxAllowedToIssue)).append("\n");
    sb.append("    maxAllowedToIssueDaily: ").append(toIndentedString(maxAllowedToIssueDaily)).append("\n");
    sb.append("    maxRedemptionRuleAmount: ").append(toIndentedString(maxRedemptionRuleAmount)).append("\n");
    sb.append("    discountType: ").append(toIndentedString(discountType)).append("\n");
    sb.append("    percentageDiscount: ").append(toIndentedString(percentageDiscount)).append("\n");
    sb.append("    totalLive: ").append(toIndentedString(totalLive)).append("\n");
    sb.append("    totalRedeemed: ").append(toIndentedString(totalRedeemed)).append("\n");
    sb.append("    totalExpired: ").append(toIndentedString(totalExpired)).append("\n");
    sb.append("    totalIssued: ").append(toIndentedString(totalIssued)).append("\n");
    sb.append("    totalIssuedToday: ").append(toIndentedString(totalIssuedToday)).append("\n");
    sb.append("    campaignInfo: ").append(toIndentedString(campaignInfo)).append("\n");
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

