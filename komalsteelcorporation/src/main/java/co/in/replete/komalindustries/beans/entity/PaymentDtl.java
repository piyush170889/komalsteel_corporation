package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the payment_dtls database table.
 * 
 */
@Entity
@Table(name="payment_dtls")
@NamedQuery(name="PaymentDtl.findAll", query="SELECT p FROM PaymentDtl p")
public class PaymentDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3442051446387654292L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid2")
	@Column(name="payment_dtls_id")
	private String paymentDtlsId;

	@Column(name="bank_ref_num")
	private String bankRefNum;

	@Column(name="payment_amount")
	private Float paymentAmount;

	@Column(name="payment_ts")
	private Timestamp paymentTs;

	@Lob
	@Column(name="txn_data")
	private String txnData;

	@Column(name="txn_ref_no")
	private String txnRefNo;

	@Column(name="payment_gateway_cd")
	private String paymentGatewayCd;

	@Column(name="payment_mode_cd")
	private String paymentModeCd;

	@Column(name="payment_status_cd")
	private String paymentStatusCd;

	public PaymentDtl() {
	}

	public PaymentDtl(String bankRefNum, Float paymentAmount, Timestamp paymentTs,
			String txnData, String txnRefNo, String paymentGatewayCd, String paymentModeCd, String paymentStatusCd) {
		this.bankRefNum = bankRefNum;
		this.paymentAmount = paymentAmount;
		this.paymentTs = paymentTs;
		this.txnData = txnData;
		this.txnRefNo = txnRefNo;
		this.paymentGatewayCd = paymentGatewayCd;
		this.paymentModeCd = paymentModeCd;
		this.paymentStatusCd = paymentStatusCd;
	}

	public PaymentDtl(Float paymentAmount,String txnData, String txnRefNo, String paymentGatewayCd, String paymentModeCd, String paymentStatusCd) {
		this.paymentAmount = paymentAmount;
		this.txnData = txnData;
		this.txnRefNo = txnRefNo;
		this.paymentGatewayCd = paymentGatewayCd;
		this.paymentModeCd = paymentModeCd;
		this.paymentStatusCd = paymentStatusCd;
	}
	
	public String getPaymentDtlsId() {
		return this.paymentDtlsId;
	}

	public void setPaymentDtlsId(String paymentDtlsId) {
		this.paymentDtlsId = paymentDtlsId;
	}

	public String getBankRefNum() {
		return this.bankRefNum;
	}

	public void setBankRefNum(String bankRefNum) {
		this.bankRefNum = bankRefNum;
	}

	public Float getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Timestamp getPaymentTs() {
		return this.paymentTs;
	}

	public void setPaymentTs(Timestamp paymentTs) {
		this.paymentTs = paymentTs;
	}

	public String getTxnData() {
		return this.txnData;
	}

	public void setTxnData(String txnData) {
		this.txnData = txnData;
	}

	public String getTxnRefNo() {
		return this.txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getPaymentGatewayCd() {
		return paymentGatewayCd;
	}

	public void setPaymentGatewayCd(String paymentGatewayCd) {
		this.paymentGatewayCd = paymentGatewayCd;
	}

	public String getPaymentModeCd() {
		return paymentModeCd;
	}

	public void setPaymentModeCd(String paymentModeCd) {
		this.paymentModeCd = paymentModeCd;
	}

	public String getPaymentStatusCd() {
		return paymentStatusCd;
	}

	public void setPaymentStatusCd(String paymentStatusCd) {
		this.paymentStatusCd = paymentStatusCd;
	}

}