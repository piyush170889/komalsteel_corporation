package co.in.replete.komalindustries.beans;

public class PayUMoneyResponseDetails {
	private String txnid;
	private String mihpayid;
	private String key;
	private String status;
	private String mode;
	private String amount;
	private String email;
	private String hash;
	private String PG_TYPE;
	private String bank_ref_num;
	private String unmappedstatus;
	private String payuMoneyId ;
	private String udf1;
	private String firstname;
	private String phone;
	private String productinfo;
	
	public PayUMoneyResponseDetails() {}

	public PayUMoneyResponseDetails(String txnid, String mihpayid, String key, String status, String mode,
			String amount, String email, String hash, String pG_TYPE, String bank_ref_num, String unmappedstatus,
			String payuMoneyId, String udf1, String firstname, String phone, String productinfo) {
		this.phone = phone;
		this.txnid = txnid;
		this.mihpayid = mihpayid;
		this.key = key;
		this.status = status;
		this.mode = mode;
		this.amount = amount;
		this.email = email;
		this.hash = hash;
		PG_TYPE = pG_TYPE;
		this.bank_ref_num = bank_ref_num;
		this.unmappedstatus = unmappedstatus;
		this.payuMoneyId = payuMoneyId;
		this.udf1 = udf1;
		this.firstname=firstname;
		this.productinfo=productinfo;
	}

	
	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getMihpayid() {
		return mihpayid;
	}

	public void setMihpayid(String mihpayid) {
		this.mihpayid = mihpayid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPG_TYPE() {
		return PG_TYPE;
	}

	public void setPG_TYPE(String pG_TYPE) {
		PG_TYPE = pG_TYPE;
	}

	public String getBank_ref_num() {
		return bank_ref_num;
	}

	public void setBank_ref_num(String bank_ref_num) {
		this.bank_ref_num = bank_ref_num;
	}

	public String getUnmappedstatus() {
		return unmappedstatus;
	}

	public void setUnmappedstatus(String unmappedstatus) {
		this.unmappedstatus = unmappedstatus;
	}

	public String getPayuMoneyId() {
		return payuMoneyId;
	}

	public void setPayuMoneyId(String payuMoneyId) {
		this.payuMoneyId = payuMoneyId;
	}
	
}
