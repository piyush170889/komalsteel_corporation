package co.in.replete.komalindustries.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.entity.HSNDetails;

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
}
