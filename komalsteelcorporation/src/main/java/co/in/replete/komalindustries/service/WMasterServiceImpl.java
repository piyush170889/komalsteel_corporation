package co.in.replete.komalindustries.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.dao.WMasterDAO;

@Service
@Transactional(rollbackFor=Throwable.class)
public class WMasterServiceImpl implements WMasterService {

	@Autowired
	private WMasterDAO wMasterDAO;
	
	@Override
	public int doAddHsnDetails(HSNDetails hsnDetails) {
		
		return wMasterDAO.insertHsnDetails(hsnDetails);
	}
	
	@Override
	public int doActivateDeactivateHSN(int hsnId, String status) {
		
		return wMasterDAO.updateHSNStatusDetails(hsnId, status);
	};
	
	@Override
	public int doUpdateHSN(HSNDetails hsnDetails) {
		
		return wMasterDAO.updateHSNDetails(hsnDetails);
	}
	
	@Override
	public List<HSNDetails> doGetAllActiveHSNDetails() {
		
		return wMasterDAO.selectAllActiveHSNDetails();
	}
}