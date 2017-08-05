package co.in.replete.komalindustries.beans;

public class TaxDescription {
	private String hsnSac;
	private float taxableValue;
	private float gstRate;
	private float taxAmount;
	public TaxDescription()
	{
		
	}
	public TaxDescription(String hsnSac, float taxableValue, float gstRate, float taxAmount) {
		this.hsnSac = hsnSac;
		this.taxableValue = taxableValue;
		this.gstRate = gstRate;
		this.taxAmount = taxAmount;
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
	public float getGstRate() {
		return gstRate;
	}
	public void setGstRate(int gstRate) {
		this.gstRate = gstRate;
	}
	public float getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}
	

}
