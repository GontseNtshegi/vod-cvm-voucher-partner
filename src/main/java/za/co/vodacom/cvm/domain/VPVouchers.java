package za.co.vodacom.cvm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPVouchers.
 */
@Entity
@Table(name = "vp_vouchers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPVouchers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "batch_id", nullable = false)
    private Integer batchId;

    @Column(name = "file_id")
    private Integer fileId;

    @NotNull
    @Size(max = 20)
    @Column(name = "product_id", length = 20, nullable = false)
    private String productId;

    @NotNull
    @Size(max = 60)
    @Column(name = "voucher_code", length = 60, nullable = false)
    private String voucherCode;

    @NotNull
    @Size(max = 100)
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    //@NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    //@NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    //@NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    @NotNull
    @Size(max = 100)
    @Column(name = "collection_point", length = 100, nullable = false)
    private String collectionPoint;

    @Column(name = "issued_date")
    private ZonedDateTime issuedDate;

    //@NotNull
    @Column(name = "reversed_date", nullable = false)
    private ZonedDateTime reversedDate;

    @Size(max = 60)
    @Column(name = "source_trxid", length = 60)
    private String sourceTrxid;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /* @ManyToOne
    @JsonIgnoreProperties(value = { "productIds", "productIds" }, allowSetters = true)
    private VPVoucherDef productId;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPVouchers id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getBatchId() {
        return this.batchId;
    }

    public VPVouchers batchId(Integer batchId) {
        this.batchId = batchId;
        return this;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public VPVouchers fileId(Integer fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /*  public String getProductId() {
        return this.productId;
    }*/

    public VPVouchers productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVoucherCode() {
        return this.voucherCode;
    }

    public VPVouchers voucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
        return this;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDescription() {
        return this.description;
    }

    public VPVouchers description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public VPVouchers createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public VPVouchers startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return this.endDate;
    }

    public VPVouchers endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public VPVouchers expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCollectionPoint() {
        return this.collectionPoint;
    }

    public VPVouchers collectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
        return this;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public ZonedDateTime getIssuedDate() {
        return this.issuedDate;
    }

    public VPVouchers issuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public ZonedDateTime getReversedDate() {
        return this.reversedDate;
    }

    public VPVouchers reversedDate(ZonedDateTime reversedDate) {
        this.reversedDate = reversedDate;
        return this;
    }

    public void setReversedDate(ZonedDateTime reversedDate) {
        this.reversedDate = reversedDate;
    }

    public String getSourceTrxid() {
        return this.sourceTrxid;
    }

    public VPVouchers sourceTrxid(String sourceTrxid) {
        this.sourceTrxid = sourceTrxid;
        return this;
    }

    public void setSourceTrxid(String sourceTrxid) {
        this.sourceTrxid = sourceTrxid;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public VPVouchers quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /* public VPVoucherDef getProductId() {
        return this.productId;
    }

    public VPVouchers productId(VPVoucherDef vPVoucherDef) {
        this.setProductId(vPVoucherDef);
        return this;
    }

    public void setProductId(VPVoucherDef vPVoucherDef) {
        this.productId = vPVoucherDef;
    }*/

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPVouchers)) {
            return false;
        }
        return id != null && id.equals(((VPVouchers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPVouchers{" +
            "id=" + getId() +
            ", batchId=" + getBatchId() +
            ", fileId=" + getFileId() +
            //", productId='" + getProductId() + "'" +
            ", voucherCode='" + getVoucherCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", collectionPoint='" + getCollectionPoint() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", reversedDate='" + getReversedDate() + "'" +
            ", sourceTrxid='" + getSourceTrxid() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
