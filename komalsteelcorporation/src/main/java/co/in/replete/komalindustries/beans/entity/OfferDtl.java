package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the offer_dtls database table.
 * 
 */
@Entity
@Table(name="offer_dtls")
@NamedQuery(name="OfferDtl.findAll", query="SELECT o FROM OfferDtl o")
public class OfferDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3925718310808674279L;

	@Id
	@Column(name="OFFER_DTLS_ID")
	private int offerDtlsId;

	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="OFFER_CODE")
	private String offerCode;

	@Column(name="OFFER_DESC")
	private String offerDesc;

	@Column(name="OFFER_DISCOUNT")
	private BigDecimal offerDiscount;

	@Column(name="OFFER_END_TS")
	private Timestamp offerEndTs;

	@Column(name="OFFER_NM")
	private String offerNm;

	@Column(name="OFFER_START_TS")
	private Timestamp offerStartTs;

	@Column(name="OFFER_STATUS")
	private String offerStatus;

	public OfferDtl() {
	}

	public int getOfferDtlsId() {
		return this.offerDtlsId;
	}

	public void setOfferDtlsId(int offerDtlsId) {
		this.offerDtlsId = offerDtlsId;
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getOfferCode() {
		return this.offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getOfferDesc() {
		return this.offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public BigDecimal getOfferDiscount() {
		return this.offerDiscount;
	}

	public void setOfferDiscount(BigDecimal offerDiscount) {
		this.offerDiscount = offerDiscount;
	}

	public Timestamp getOfferEndTs() {
		return this.offerEndTs;
	}

	public void setOfferEndTs(Timestamp offerEndTs) {
		this.offerEndTs = offerEndTs;
	}

	public String getOfferNm() {
		return this.offerNm;
	}

	public void setOfferNm(String offerNm) {
		this.offerNm = offerNm;
	}

	public Timestamp getOfferStartTs() {
		return this.offerStartTs;
	}

	public void setOfferStartTs(Timestamp offerStartTs) {
		this.offerStartTs = offerStartTs;
	}

	public String getOfferStatus() {
		return this.offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

}