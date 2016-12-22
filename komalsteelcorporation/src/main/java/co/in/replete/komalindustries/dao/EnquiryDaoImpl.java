package co.in.replete.komalindustries.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.EnquiryDetailsTo;

@Repository
public class EnquiryDaoImpl implements EnquiryDao {
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Override
	public List<EnquiryDetailsTo> selectEnquiryList() {

		List<EnquiryDetailsTo> list = jdbcTemplate.query(
				"SELECT ENQUIRY_DTLS_ID,FIRST_NM,LAST_NM,EMAIL_ID,PHN_NM,ENQUIRY_TYPE,MESSAGE,ld.LOCATION_NAME as CITY,"
				+ "ld1.LOCATION_NAME as STATE,date_format(ed.CREATED_TS,'%d-%m-%Y %h:%i:%s') as CREATED_TS FROM enquiry_dtls ed inner join location_dtls ld on ld.LOCATION_ID=ed.CITY_ID "
				+ "inner join location_dtls ld1 on ld1.LOCATION_ID=ed.STATE_ID limit 200",
				new BeanPropertyRowMapper<EnquiryDetailsTo>(EnquiryDetailsTo.class));
		if (list.size() > 0)
			return list;
		return new ArrayList<EnquiryDetailsTo>();
	}

	@Override
	public List<EnquiryDetailsTo> selectEnquiryListByDate(String startDate, String endDate) {

		List<EnquiryDetailsTo> list = jdbcTemplate.query(
				"SELECT ENQUIRY_DTLS_ID,FIRST_NM,LAST_NM,EMAIL_ID,PHN_NM,ENQUIRY_TYPE,MESSAGE,ld.LOCATION_NAME as CITY,ld1.LOCATION_NAME as STATE,ed.CREATED_TS "
				+ "FROM enquiry_dtls ed inner join location_dtls ld on ld.LOCATION_ID=ed.CITY_ID "
						+ "inner join location_dtls ld1 on ld1.LOCATION_ID=ed.STATE_ID and date_format(ed.CREATED_TS,'%Y-%m-%d') between ? and ?",
				new Object[] { startDate, endDate }, new BeanPropertyRowMapper<EnquiryDetailsTo>(EnquiryDetailsTo.class));
		if (list.size() > 0)
			return list;
		return new ArrayList<EnquiryDetailsTo>();
	}
	
}
