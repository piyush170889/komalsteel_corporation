package co.in.replete.komalindustries.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.entity.Configuration;
import co.in.replete.komalindustries.utils.UDValues;

@Repository
public class ConfigurationDAOImpl extends BaseDAOImpl implements ConfigurationDAO {

	@Override
	@Cacheable(value="defaultImage", key="defImag", unless="#result==null")
	public byte[] getDefaultImage() {
		
		Configuration configuration = jdbcTemplate.query("select CONFIG_IMG from configuration where CONFIG_NAME=?", 
				new Object[]{UDValues.DEFAULT_IMAGE.toString()},new BeanPropertyRowMapper<Configuration>(Configuration.class)).get(0);
		
		return configuration.getConfigImg();
	}
}
