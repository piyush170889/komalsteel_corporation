package co.in.replete.komalindustries.dao;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SettingDAOImpl implements SettingDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	@Override
	public String selectPassword(String value) {
		String sql = "select CONFIG_VAL from configuration where CONFIG_NAME=?";
		return jdbcTemplate.queryForObject(sql, new Object[] {value}, String.class);
	}
	
	
	@Override
	public int updatePassword(String newPassword, String name) {
		
		String sql ="update configuration set CONFIG_VAL=? where CONFIG_NAME=?";
		return jdbcTemplate.update(sql,new Object[] {newPassword,name});
	}

}
