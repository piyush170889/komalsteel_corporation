package co.in.replete.komalindustries.dao;

public interface SettingDAO {

	String selectPassword(String string);

	int updatePassword(String newPassword, String string);

}
