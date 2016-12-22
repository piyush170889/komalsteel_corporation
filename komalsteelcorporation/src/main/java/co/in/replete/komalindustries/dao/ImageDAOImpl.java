package co.in.replete.komalindustries.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;

@Repository
public class ImageDAOImpl extends BaseDAOImpl implements ImageDAO {

	@Override
	public ItemMasterDtl selectProductImageById(String pId) {
		return jdbcTemplate.query("select ITEM_IMAGE from item_master_dtls where ITEM_MASTER_DTLS_ID=?", 
				new Object[] {pId}, new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class)).get(0);
	}
}
