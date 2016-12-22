package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import java.sql.Timestamp;

/**
 * The persistent class for the cart_dlvry_dtls database table.
 * 
 */
@Entity
@Table(name="cart_dlvry_dtls")
public class CartDlvryDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6007915031723553959L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="CART_DLVRY_DTLS_ID")
	private int cartDlvryDtlsId;

	@Temporal(TemporalType.DATE)
	@Column(name="ACTUAL_DLVRY_DT")
	private String actualDlvryDt;

	@Column(name="ALTERNATE_CNTC")
	private String alternateCntc;

	@Column(name="DLVRY_BY_TRACK_ID")
	private String dlvryByTrackId;

	@Column(name="DLVRY_TYPE")
	private String dlvryType;

	@Temporal(TemporalType.DATE)
	@Column(name="EXP_DLVRY_DT")
	private String expDlvryDt;

	@Column(name="SHIPPING_ADDRESS_ID")
	private int shippingAddressId;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	public CartDlvryDtl() {
	}

	public CartDlvryDtl(String actualDlvryDt, String alternateCntc, String dlvryByTrackId, String dlvryType, String expDlvryDt, int shippingAddressId) {
		super();
		this.actualDlvryDt = actualDlvryDt;
		this.alternateCntc = alternateCntc;
		this.dlvryByTrackId = dlvryByTrackId;
		this.dlvryType = dlvryType;
		this.expDlvryDt = expDlvryDt;
		this.shippingAddressId = shippingAddressId;
	}

	public int getCartDlvryDtlsId() {
		return this.cartDlvryDtlsId;
	}

	public void setCartDlvryDtlsId(int cartDlvryDtlsId) {
		this.cartDlvryDtlsId = cartDlvryDtlsId;
	}

	public String getActualDlvryDt() {
		return this.actualDlvryDt;
	}

	public void setActualDlvryDt(String actualDlvryDt) {
		this.actualDlvryDt = actualDlvryDt;
	}

	public String getAlternateCntc() {
		return this.alternateCntc;
	}

	public void setAlternateCntc(String alternateCntc) {
		this.alternateCntc = alternateCntc;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public String getDlvryByTrackId() {
		return this.dlvryByTrackId;
	}

	public void setDlvryByTrackId(String dlvryByTrackId) {
		this.dlvryByTrackId = dlvryByTrackId;
	}

	public String getDlvryType() {
		return this.dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}

	public String getExpDlvryDt() {
		return this.expDlvryDt;
	}

	public void setExpDlvryDt(String expDlvryDt) {
		this.expDlvryDt = expDlvryDt;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public int getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(int shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

}