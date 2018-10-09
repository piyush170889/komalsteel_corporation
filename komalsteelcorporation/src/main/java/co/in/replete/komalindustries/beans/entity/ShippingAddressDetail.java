package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import java.math.BigDecimal;
/**
 * The persistent class for the address_details database table.
 * 
 */
@Entity
@Table(name="other_address_details")
public class ShippingAddressDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975986091159771806L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="OTHER_ADDRESS_ID")
	private int otherAddressId;

	@Column(name="ADDRESS_TYPE")
	private String addressType;

	@Column(name="CITY")
	private String city;

	@Column(name="COUNTRY")
	private String country;

	@Column(name="LATITUDE")
	private BigDecimal latitude;

	@Column(name="LONGITUDE")
	private BigDecimal longitude;

	@Column(name="POSTAL_CODE")
	private String postalCode;

	@Column(name="ST_ADDRESS_1")
	private String stAddress1;

	@Column(name="ST_ADDRESS_2")
	private String stAddress2;

	@Column(name="ST_ADDRESS_3")
	private String stAddress3;

	@Column(name="STATE")
	private String state;

	@Column(name="TRACK_ID")
	private String trackId;

	private String mark;
	
	private String destination;
	
	private String tranNm;
	
	private String tinNo;
	
	public ShippingAddressDetail() {
	}

	public ShippingAddressDetail(String addressTypeCd, String city, String countryCd, BigDecimal latitude, BigDecimal longitude,
			String postalCode, String stAddress1, String stAddress2, String stAddress3, String stateCd,
			String trackId,String mark,String destination,String tranNm,String tinNo) {
		this.addressType = addressTypeCd;
		this.city = city;
		this.country = countryCd;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postalCode = postalCode;
		this.stAddress1 = stAddress1;
		this.stAddress2 = stAddress2;
		this.stAddress3 = stAddress3;
		this.state = stateCd;
		this.trackId = trackId;
		this.mark=mark;
		this.destination=destination;
		this.tranNm=tranNm;
		this.tinNo=tinNo;
	}
	

	@Override
	public String toString() {
		return "ShippingAddressDetail [otherAddressId=" + otherAddressId + ", addressType=" + addressType + ", city="
				+ city + ", country=" + country + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", postalCode=" + postalCode + ", stAddress1=" + stAddress1 + ", stAddress2=" + stAddress2
				+ ", stAddress3=" + stAddress3 + ", state=" + state + ", trackId=" + trackId + ", mark=" + mark
				+ ", destination=" + destination + ", tranNm=" + tranNm + ", tinNo=" + tinNo + "]";
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTranNm() {
		return tranNm;
	}

	public void setTranNm(String tranNm) {
		this.tranNm = tranNm;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public int getOtherAddressId() {
		return this.otherAddressId;
	}

	public void setOtherAddressId(int otherAddressId) {
		this.otherAddressId = otherAddressId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStAddress1() {
		return this.stAddress1;
	}

	public void setStAddress1(String stAddress1) {
		this.stAddress1 = stAddress1;
	}

	public String getStAddress2() {
		return this.stAddress2;
	}

	public void setStAddress2(String stAddress2) {
		this.stAddress2 = stAddress2;
	}

	public String getStAddress3() {
		return this.stAddress3;
	}

	public void setStAddress3(String stAddress3) {
		this.stAddress3 = stAddress3;
	}

	public String getTrackId() {
		return this.trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}