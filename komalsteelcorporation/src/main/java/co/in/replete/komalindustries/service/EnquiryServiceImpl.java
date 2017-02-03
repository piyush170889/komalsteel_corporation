package co.in.replete.komalindustries.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.EnquiryDetailsTo;
import co.in.replete.komalindustries.dao.EnquiryDao;

@Service
@Transactional(rollbackFor=Throwable.class)
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	EnquiryDao enquiryDao;
	
	@Override
	public List<EnquiryDetailsTo> getEnquiryList() {
		return enquiryDao.selectEnquiryList();
	}

	@Override
	public List<EnquiryDetailsTo> getEnquiryListByDate(String startDate, String endDate) {
		return enquiryDao.selectEnquiryListByDate(startDate, endDate);
	}
	
}
