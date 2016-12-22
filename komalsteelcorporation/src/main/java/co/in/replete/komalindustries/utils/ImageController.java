package co.in.replete.komalindustries.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.in.replete.komalindustries.dao.ConfigurationDAO;
import co.in.replete.komalindustries.service.ImageService;

@Controller
@RequestMapping(value="/Image")
public class ImageController {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ConfigurationDAO configurationDAO; 
	
	@RequestMapping(value="productImg", method=RequestMethod.GET)
	public void showImage(@RequestParam("pid") String pId, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

		byte[] imageData = null;
		
	    imageData = imageService.getImageByProductId(pId);        
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    
	    if(null == imageData) {
	    	imageData = configurationDAO.getDefaultImage();
	    } 

	    response.getOutputStream().write(imageData);
	    response.getOutputStream().close();
	}
	
	
}
