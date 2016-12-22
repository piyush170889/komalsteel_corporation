package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * The persistent class for the invoice_dtls database table.
 * 
 */
@Entity
@Table(name="invoice_dtls")
public class InvoiceDtl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7126531815316164656L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="INVOICE_DTLS_ID")
	private int invoiceDtlsId;

	@Column(name="AMOUNT_BAL")
	private Float amountBal;

	@Column(name="AMOUNT_PAID")
	private Float amountPaid;

	@Column(name="DISCOUNT")
	private Float discount;

	@Column(name="DISCOUNT_VALUE")
	private Float discountValue;

	@Column(name="GRAND_TOTAL")
	private Float grandTotal;

	@Column(name="MISC_CHARGES")
	private Float miscCharges;

	@Column(name="SERVICE_TAX")
	private Float serviceTax;

	@Column(name="SERVICE_TAX_VALUE")
	private Float serviceTaxValue;

	@Column(name="SHIPPING_CHARGES")
	private Float shippingCharges;

	@Column(name="SUB_TOTAL")
	private Float subTotal;

	@Column(name="TRACK_ID")
	private String trackId;

	@Column(name="VAT")
	private Float vat;

	@Column(name="VAT_VALUE")
	private Float vatValue;

	public InvoiceDtl() {
	}

	public InvoiceDtl(Float amountBal, Float amountPaid, Float discount, Float discountValue, Float grandTotal,
			Float miscCharges, Float serviceTax, Float serviceTaxValue, Float shippingCharges, Float subTotal, String trackId,
			Float vat, Float vatValue) {
		this.amountBal = amountBal;
		this.amountPaid = amountPaid;
		this.discount = discount;
		this.discountValue = discountValue;
		this.grandTotal = grandTotal;
		this.miscCharges = miscCharges;
		this.serviceTax = serviceTax;
		this.serviceTaxValue = serviceTaxValue;
		this.shippingCharges = shippingCharges;
		this.subTotal = subTotal;
		this.trackId = trackId;
		this.vat = vat;
		this.vatValue = vatValue;
	}

	public int getInvoiceDtlsId() {
		return invoiceDtlsId;
	}

	public void setInvoiceDtlsId(int invoiceDtlsId) {
		this.invoiceDtlsId = invoiceDtlsId;
	}

	public Float getAmountBal() {
		return amountBal;
	}

	public void setAmountBal(Float amountBal) {
		this.amountBal = amountBal;
	}

	public Float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Float discountValue) {
		this.discountValue = discountValue;
	}

	public Float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Float getMiscCharges() {
		return miscCharges;
	}

	public void setMiscCharges(Float miscCharges) {
		this.miscCharges = miscCharges;
	}

	public Float getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Float serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Float getServiceTaxValue() {
		return serviceTaxValue;
	}

	public void setServiceTaxValue(Float serviceTaxValue) {
		this.serviceTaxValue = serviceTaxValue;
	}

	public Float getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(Float shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Float getVat() {
		return vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	public Float getVatValue() {
		return vatValue;
	}

	public void setVatValue(Float vatValue) {
		this.vatValue = vatValue;
	}

}