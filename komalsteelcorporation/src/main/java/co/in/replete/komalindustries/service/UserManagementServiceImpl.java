package co.in.replete.komalindustries.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.SmsDtlsWrapper;
import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.ServicesException;

@Service
@Transactional(rollbackFor=Throwable.class)
public class UserManagementServiceImpl implements UserManagementService {
	@Autowired
	UserManagementDAO userDAO;

	@Override
	public int addContactDirectories(ContactDtls contactDtls) throws ServicesException {

			return	 userDAO.addContactDirectories(contactDtls);
	}



	@Override
	public List<SmsDtlsWrapper> getAllSmsDtls() {

		return userDAO.getAllSmsDtls();
	}

	@Override
	public int  addSmsDtls(String contactNo, String finalMsgToStore) throws ServicesException {

		ContactDtls contactDtls = userDAO.getContactDetails(contactNo);
		int isRowUpdated = userDAO.addSmsDtls(contactDtls.getContactDtlsId(),finalMsgToStore);
		System.out.println("isRowUpdated : "+isRowUpdated);
		if(isRowUpdated == 0) {
			throw new ServicesException("Error Occured while Inserting SMS details.");
		}
		return isRowUpdated;
	}



	@Override
	public int checkContactNumber(String contactNumber) {
		
		int contactNumCount = userDAO.selectContact(contactNumber);
		System.out.println("request.getParameter(\"contactNumber\") : " +contactNumber);
		return contactNumCount;
	}



}
