package co.in.replete.komalindustries.service;

public interface SettingService {

	String getSettingsPassword();

	int updatePassword(String newPassword);

	int updateAdminEmailIds(String email);

	int updateContactNumbers(String contact);


}
