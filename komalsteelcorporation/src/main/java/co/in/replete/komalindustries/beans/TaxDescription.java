package co.in.replete.komalindustries.beans;

public class TaxDescription {
	private String hsnSac;
	private float taxableValue;
	private float iGstRate;
	private float iGsttaxAmount;
	private float cGstRate;
	private float cGsttaxAmount;
	private float sGstRate;
	private float sGsttaxAmount;
	
	
	
	
	//Taxdescription constructor Outside Maharashtra GST 
	public TaxDescription(String hsnSac, float taxableValue, float iGstRate, float iGsttaxAmount) {
		this.hsnSac = hsnSac;
		this.taxableValue = taxableValue;
		this.iGstRate = iGstRate;
		this.iGsttaxAmount = iGsttaxAmount;
	}
	
	
	
	
	//Taxdescription constructor  Maharashtra GST 
	public TaxDescription(String hsnSac, float taxableValue, float cGstRate, float cGsttaxAmount, float sGstRate,
			float sGsttaxAmount) {
		this.hsnSac = hsnSac;
		this.taxableValue = taxableValue;
		this.cGstRate = cGstRate;
		this.cGsttaxAmount = cGsttaxAmount;
		this.sGstRate = sGstRate;
		this.sGsttaxAmount = sGsttaxAmount;
	}





	public String getHsnSac() {
		return hsnSac;
	}
	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
	}
	public float getTaxableValue() {
		return taxableValue;
	}
	public void setTaxableValue(float taxableValue) {
		this.taxableValue = taxableValue;
	}
	public float getiGstRate() {
		return iGstRate;
	}
	public void setiGstRate(float iGstRate) {
		this.iGstRate = iGstRate;
	}
	public float getcGstRate() {
		return cGstRate;
	}
	public void setcGstRate(float cGstRate) {
		this.cGstRate = cGstRate;
	}
	public float getsGstRate() {
		return sGstRate;
	}
	public void setsGstRate(float sGstRate) {
		this.sGstRate = sGstRate;
	}
	public float getiGsttaxAmount() {
		return iGsttaxAmount;
	}
	public void setiGsttaxAmount(float iGsttaxAmount) {
		this.iGsttaxAmount = iGsttaxAmount;
	}
	public float getcGsttaxAmount() {
		return cGsttaxAmount;
	}
	public void setcGsttaxAmount(float cGsttaxAmount) {
		this.cGsttaxAmount = cGsttaxAmount;
	}
	public float getsGsttaxAmount() {
		return sGsttaxAmount;
	}
	public void setsGsttaxAmount(float sGsttaxAmount) {
		this.sGsttaxAmount = sGsttaxAmount;
	}
	
}
