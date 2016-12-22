package co.in.replete.komalindustries.dao;

import org.springframework.dao.DataAccessException;

public interface BaseDAO {

	String getUUID() throws DataAccessException;
}
