package co.in.replete.komalindustries.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.entity.CourierMasterDtls;
import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.beans.entity.TransportationMasterDtls;

@Repository
public class WMasterDAOImpl extends BaseDAOImpl implements WMasterDAO {

	@Override
	public List<HSNDetails> selectAllHsnDetails() {
		
		String sql = "select * from hsn_dtls";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HSNDetails>(HSNDetails.class));
	}
	
	@Override
	public List<HSNDetails> selectAllActiveHSNDetails() {
		String sql = "select * from hsn_dtls where IS_ACTIVE='Y'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HSNDetails>(HSNDetails.class));
	}
	
	@Override
	public HSNDetails selectHsnDetailsByHsnDtlsId(int hsnDtlsId) {
//		System.out.println("HSN Dtls ID - " + hsnDtlsId);
		String sql = "select * from hsn_dtls where HSN_DTLS_ID=?";
		return jdbcTemplate.query(sql, new Object[] {hsnDtlsId}, new BeanPropertyRowMapper<HSNDetails>(HSNDetails.class)).get(0);
	}
	
	@Override
	public int insertHsnDetails(HSNDetails hsnDetails) {
		String sql = "insert into hsn_dtls(HSN_NO,IGST,CGST,SGST) values(?,?,?,?)";
		return jdbcTemplate.update(sql, hsnDetails.getHsnNo(), hsnDetails.getiGst(), hsnDetails.getcGst(), 
				hsnDetails.getsGst());
	}
	
	@Override
	public int updateHSNStatusDetails(int hsnId, String status) {
		String sql = "update hsn_dtls set IS_ACTIVE=? where HSN_DTLS_ID=?";
		return jdbcTemplate.update(sql, status, hsnId);
	}
	
	@Override
	public int updateHSNDetails(HSNDetails hsnDetails) {
		String sql = "update hsn_dtls set HSN_NO=?,IGST=?,CGST=?,SGST=? where HSN_DTLS_ID=?";
		return jdbcTemplate.update(sql, hsnDetails.getHsnNo(), hsnDetails.getiGst(), hsnDetails.getcGst(), 
				hsnDetails.getsGst(), hsnDetails.getHsnDtlsId());
	}
	
	@Override
	public List<CourierMasterDtls> selectActiveCourierDetailsList() {
		
		return jdbcTemplate.query("select * from courier_master where IS_ACTIVE=1", 
				new BeanPropertyRowMapper<CourierMasterDtls>(CourierMasterDtls.class));
	}
	
	@Override
	public void insertCourierDetails(String courierName, String trackingUrl) {
		
		jdbcTemplate.update("insert into courier_master(COURIER_NM,TRACKING_URL) values(?,?)", courierName, trackingUrl);
	}
	
	@Override
	public void updateCourierDetails(int courierDtlsId, String courierName, String trackingUrl) {
		
		jdbcTemplate.update("update courier_master set COURIER_NM=?,TRACKING_URL=? where COURIER_MASTER_ID=?", courierName, trackingUrl, courierDtlsId);
	}

	@Override
	public List<TransportationMasterDtls> selectActiveTransportationDetailsList() {
		return jdbcTemplate.query("select * from transportation_master where IS_ACTIVE=1", 
				new BeanPropertyRowMapper<TransportationMasterDtls>(TransportationMasterDtls.class));
	}

	@Override
	public void insertTransportationDetails(String name, String description) {
		jdbcTemplate.update("insert into transportation_master(NAME,DESCRIPTION) values(?,?)", name, description);
		
	}

	@Override
	public void updateTransportationDetails(int transportationDtlsId, String name, String description) {
		jdbcTemplate.update("update transportation_master set NAME=?,DESCRIPTION=? where TRANSPORTATION_MASTER_ID=?", name, description, transportationDtlsId);
		
	}
}
