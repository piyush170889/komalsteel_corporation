package co.in.replete.komalindustries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.dao.SettingDAO;
import co.in.replete.komalindustries.utils.UDValues;

@Service
@Transactional(rollbackFor=Throwable.class)
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingDAO settingDAO;
	
	@Override
	public String getSettingsPassword() {
		
		return settingDAO.selectPassword(UDValues.SETTING_PASSWORD.toString());
	}
	
	@Override
	public int updatePassword(String newPassword) {
		return settingDAO.updatePassword(newPassword, UDValues.SETTING_PASSWORD.toString());
	}
}
