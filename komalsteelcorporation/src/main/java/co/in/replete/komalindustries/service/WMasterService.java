package co.in.replete.komalindustries.service;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.HSNDetails;

public interface WMasterService {

	int doAddHsnDetails(HSNDetails hsnDetails);

	int doActivateDeactivateHSN(int hsnId, String status);

	int doUpdateHSN(HSNDetails hsnDetails);

	List<HSNDetails> doGetAllActiveHSNDetails();

}
