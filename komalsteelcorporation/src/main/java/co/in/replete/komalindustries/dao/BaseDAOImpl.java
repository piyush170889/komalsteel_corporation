package co.in.replete.komalindustries.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAOImpl implements BaseDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String getUUID() throws DataAccessException {
		
		String uuid = jdbcTemplate.query("select uuid()", new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString(1);
			}
		});
		
		return uuid;
	}
	
}
