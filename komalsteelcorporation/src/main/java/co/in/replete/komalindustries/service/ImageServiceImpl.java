package co.in.replete.komalindustries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.dao.ImageDAO;

@Service
@Transactional(rollbackFor=Throwable.class)
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageDAO imageDAO;
	
	@Override
	public byte[] getImageByProductId(String pId) {
		
		ItemMasterDtl itemMasterDtl = imageDAO.selectProductImageById(pId);
		return itemMasterDtl.getItemImage();
	}
}
