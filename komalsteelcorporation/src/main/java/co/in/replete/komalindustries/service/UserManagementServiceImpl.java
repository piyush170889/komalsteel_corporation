package co.in.replete.komalindustries.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.beans.entity.SmsDtls;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.ServicesException;
@Service
public class UserManagementServiceImpl implements UserManagementService {
	@Autowired
	UserManagementDAO userDAO;
	
	@Override
	public int addContactDirectories(ContactDtls contactDtls, String contactNo) throws ServicesException {
		
		int contactNumCount = userDAO.selectContact(contactNo);
		
		System.out.println(contactNumCount);
		
		if(contactNumCount >0) {
			throw new ServicesException("Contact Number already exists");
			
		}
		
		return	 userDAO.addContactDirectories(contactDtls);
	}

	
	
	@Override
	public List<SmsDtls> getAllSmsDtls() {
		
		return userDAO.getAllSmsDtls();
	}

	@Override
	public List<ContactDtls> addSmsDtls(String contactNo) {
	
		String  str =	 userDAO.select(contactNo);
		
		System.out.println(str);
		
		userDAO.addSmsDtls(contactNo,str);
		
		return null;
		}
	}
