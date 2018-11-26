package co.in.replete.komalindustries.dao;

public interface SettingDAO {

	String selectPassword(String string);

	int updateConfigurationValues(String newPassword, String string);

	String getAdminEmailIds(String string);

	String getAdminContactNumbers(String string);


}
