package co.in.replete.komalindustries.beans;

import co.in.replete.komalindustries.beans.entity.PaymentDtl;

public class PaymentDetailsRequest {
	private String paymentAmount;
	private String paymentGatewayCd;
	private String firstName;
	private String emailId;
	private String contact;
	private String productInfo;
	private String paymentModeCd;
	private String paymentDtlsId;
	
	public PaymentDetailsRequest(String paymentAmount, String paymentGatewayCd, String firstName, String emailId,
			String contact, String productInfo, String paymentModeCd, String paymentDtlsId) {
		this.paymentAmount = paymentAmount;
		this.paymentGatewayCd = paymentGatewayCd;
		this.firstName = firstName;
		this.emailId = emailId;
		this.contact = contact;
		this.productInfo = productInfo;
		this.paymentModeCd = paymentModeCd;
		this.paymentDtlsId=paymentDtlsId;
	}
	
	public PaymentDetailsRequest() {}
	
	
	public String getPaymentDtlsId() {
		return paymentDtlsId;
	}

	public void setPaymentDtlsId(String paymentDtlsId) {
		this.paymentDtlsId = paymentDtlsId;
	}

	public String getPaymentModeCd() {
		return paymentModeCd;
	}

	public void setPaymentModeCd(String paymentModeCd) {
		this.paymentModeCd = paymentModeCd;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentGatewayCd() {
		return paymentGatewayCd;
	}
	public void setPaymentGatewayCd(String paymentGatewayCd) {
		this.paymentGatewayCd = paymentGatewayCd;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	
	public PaymentDtl convertToPaymentDtlDTO(String txnId, String paymentStatusCd){
		PaymentDtl paymentDtlDTO = new PaymentDtl(Float.parseFloat(this.paymentAmount), null, txnId, this.paymentGatewayCd, 
				this.paymentModeCd, paymentStatusCd);
		
		return paymentDtlDTO;
	}

	@Override
	public String toString() {
		return "PaymentDetailsRequest [paymentAmount=" + paymentAmount + ", paymentGatewayCd=" + paymentGatewayCd
				+ ", firstName=" + firstName + ", emailId=" + emailId + ", contact=" + contact + ", productInfo="
				+ productInfo + ", paymentModeCd=" + paymentModeCd + ", paymentDtlsId=" + paymentDtlsId + "]";
	}
	
	
}
