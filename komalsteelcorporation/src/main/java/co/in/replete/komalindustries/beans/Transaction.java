package co.in.replete.komalindustries.beans;

public class Transaction {
	
private String descriptioOFGoods;
private String hsnSac;
private Float gstRate;
private int quantity;
private Float rate;
private String per;
private Float amount;



public Transaction(String descriptioOFGoods, String hsnSac, Float gstRate, int quantity, Float rate, String per,
		Float amount) {
	this.descriptioOFGoods = descriptioOFGoods;
	this.hsnSac = hsnSac;
	this.gstRate = gstRate;
	this.quantity = quantity;
	this.rate = rate;
	this.per = per;
	this.amount = amount;
}
public String getDescriptioOFGoods() {
	return descriptioOFGoods;
}
public void setDescriptioOFGoods(String descriptioOFGoods) {
	this.descriptioOFGoods = descriptioOFGoods;
}

public String getHsnSac() {
	return hsnSac;
}
public void setHsnSac(String hsnSac) {
	this.hsnSac = hsnSac;
}
public Float getGstRate() {
	return gstRate;
}
public void setGstRate(Float gstRate) {
	this.gstRate = gstRate;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public Float getRate() {
	return rate;
}
public void setRate(Float rate) {
	this.rate = rate;
}
public String getPer() {
	return per;
}
public void setPer(String per) {
	this.per = per;
}
public Float getAmount() {
	return amount;
}
public void setAmount(Float amount) {
	this.amount = amount;
}

}
