package co.in.replete.komalindustries.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.beans.entity.TransportationMasterDtls;

public interface WMasterService {

	int doAddHsnDetails(HSNDetails hsnDetails);

	int doActivateDeactivateHSN(int hsnId, String status);

	int doUpdateHSN(HSNDetails hsnDetails);

	List<HSNDetails> doGetAllActiveHSNDetails();

	ModelAndView doGetCourierView();

	void doAddCourierView(HttpServletRequest servletRequest);

	void doUpdateCourierView(HttpServletRequest servletRequest);

	List<TransportationMasterDtls> doGetTransportationView();

	void doAddTransportDetails(HttpServletRequest servletRequest);

	void doUpdateTransportationDetails(HttpServletRequest servletRequest);

}
