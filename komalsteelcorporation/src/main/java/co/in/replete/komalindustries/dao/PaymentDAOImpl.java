package co.in.replete.komalindustries.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.PayUMoneyResponseDetails;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	
    @Override
	public void updatePaymentDetails(PayUMoneyResponseDetails response) throws Exception {
		
    	jdbcTemplate.update(sqlProperties.getProperty("update.paymentdtls"), new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				Timestamp curentTimestamp = new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(1, curentTimestamp);
				ps.setString(2, response.getStatus().trim());
				ps.setString(3, response.getBank_ref_num().trim());
				ps.setString(4, response.getUdf1().trim());
			}
		});
	}
}
