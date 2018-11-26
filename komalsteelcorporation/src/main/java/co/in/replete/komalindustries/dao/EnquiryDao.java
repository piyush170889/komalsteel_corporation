package co.in.replete.komalindustries.dao;

import java.util.List;

import co.in.replete.komalindustries.beans.EnquiryDetailsTo;

public interface EnquiryDao {

	List<EnquiryDetailsTo> selectEnquiryList();

	List<EnquiryDetailsTo> selectEnquiryListByDate(String startDate, String endDate);

}
