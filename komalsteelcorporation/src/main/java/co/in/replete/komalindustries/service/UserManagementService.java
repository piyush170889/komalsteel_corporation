package co.in.replete.komalindustries.service;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.beans.entity.SmsDtls;
import co.in.replete.komalindustries.exception.ServicesException;

public interface UserManagementService {

	int addContactDirectories(ContactDtls contactDtls, String contactNo) throws ServicesException;

	List<SmsDtls> getAllSmsDtls();

	List<ContactDtls> addSmsDtls(String contactNo);

}
