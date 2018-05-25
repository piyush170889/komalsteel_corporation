package co.in.replete.komalindustries.dao;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.CourierMasterDtls;
import co.in.replete.komalindustries.beans.entity.HSNDetails;

public interface WMasterDAO extends BaseDAO {

	List<HSNDetails> selectAllHsnDetails();

	int insertHsnDetails(HSNDetails hsnDetails);

	int updateHSNStatusDetails(int hsnId, String status);

	int updateHSNDetails(HSNDetails hsnDetails);

	List<HSNDetails> selectAllActiveHSNDetails();

	HSNDetails selectHsnDetailsByHsnDtlsId(int hsnDtlsId);

	List<CourierMasterDtls> selectActiveCourierDetailsList();

	void insertCourierDetails(String courierName, String trackingUrl);

	void updateCourierDetails(int courierDtlsId, String courierName, String trackingUrl);

}
