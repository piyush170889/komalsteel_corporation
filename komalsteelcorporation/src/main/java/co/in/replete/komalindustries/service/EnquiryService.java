package co.in.replete.komalindustries.service;

import java.util.List;

import co.in.replete.komalindustries.beans.EnquiryDetailsTo;

public interface EnquiryService {

	List<EnquiryDetailsTo> getEnquiryList();
	
	List<EnquiryDetailsTo> getEnquiryListByDate(String startDate,String endDate);
	
}
