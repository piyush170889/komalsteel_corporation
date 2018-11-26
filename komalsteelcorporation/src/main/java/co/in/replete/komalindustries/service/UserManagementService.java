package co.in.replete.komalindustries.service;

import java.util.List;

import co.in.replete.komalindustries.beans.SmsDtlsWrapper;
import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.exception.ServicesException;

public interface UserManagementService {

	int addContactDirectories(ContactDtls contactDtls) throws ServicesException;

	List<SmsDtlsWrapper> getAllSmsDtls();

	int addSmsDtls(String contactNo, String finalMsgToStore) throws ServicesException;

	int checkContactNumber(String contactNumber);

}
