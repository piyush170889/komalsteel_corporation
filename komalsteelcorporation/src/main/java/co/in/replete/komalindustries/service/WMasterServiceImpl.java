package co.in.replete.komalindustries.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import co.in.replete.komalindustries.beans.entity.CourierMasterDtls;
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
	
	@Override
	public ModelAndView doGetCourierView() {
		
		List<CourierMasterDtls> courierDetailsList = wMasterDAO.selectActiveCourierDetailsList();
		
		ModelAndView modelAndView = new ModelAndView("masters/courier-masters");
		modelAndView.addObject("courierDetailsList", courierDetailsList);
		
		return modelAndView;
	}
	
	@Override
	public void doAddCourierView(HttpServletRequest servletRequest) {
		
		String courierName = servletRequest.getParameter("courierName");
		String trackingUrl = servletRequest.getParameter("trackingUrl");
		
		wMasterDAO.insertCourierDetails(courierName, trackingUrl);
	}
	
	@Override
	public void doUpdateCourierView(HttpServletRequest servletRequest) {
		
		String courierName = servletRequest.getParameter("courierName");
		String trackingUrl = servletRequest.getParameter("trackingUrl");
		int courierDtlsId = Integer.parseInt(servletRequest.getParameter("courierDtlsId"));
		
		wMasterDAO.updateCourierDetails(courierDtlsId, courierName, trackingUrl);
	}
}