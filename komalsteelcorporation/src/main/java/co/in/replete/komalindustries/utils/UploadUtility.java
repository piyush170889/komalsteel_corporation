package co.in.replete.komalindustries.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;

import co.in.replete.komalindustries.beans.UploadFileResponse;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.ServicesException;

@Component
public class UploadUtility extends KomalIndustriesConstants {

	@Autowired
	private Properties configProperties;
	
	public UploadFileResponse uploadFile(InputStream fileInputStream, String fileName) throws ServicesException {
		 
		 String uploadDestination = configProperties.getProperty("upload.destination.name");
		 String filePathAndName = FILE_SEPARATOR + configProperties.getProperty("upload.destination.foldername") + FILE_SEPARATOR + fileName + FILE_IMG_EXT_PNG;
//		 String filePathAndName = FILE_SEPARATOR + fileName;
		 System.out.println("File Path And Name: " + filePathAndName);
		 
		try {
		   
			return uploadToDropbox(uploadDestination,filePathAndName,fileInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServicesException("608");
		}
	}
	
	
	// Upload file with provided extension
	public UploadFileResponse uploadFile(InputStream fileInputStream, String fileName,String fileExtension) throws ServicesException {
		 
		 String uploadDestination = configProperties.getProperty("upload.destination.name");
		 String filePathAndName = FILE_SEPARATOR + configProperties.getProperty("upload.destination.foldername") + FILE_SEPARATOR + fileName +FILE_EXT_PREFIX+fileExtension;
//		 String filePathAndName = FILE_SEPARATOR + fileName;
		 System.out.println("File Path And Name: " + filePathAndName);
		 
		try {
		   
			return uploadToDropbox(uploadDestination,filePathAndName,fileInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServicesException("608");
		}
	}
	
	
	private UploadFileResponse uploadToDropbox(String uploadDestination,String filePathAndName,InputStream fileInputStream) throws IOException, UploadErrorException, DbxException, ServicesException{
		 String url = "";
			
		switch(uploadDestination) {
		case UPLOAD_DEST_DROPBOX :
			final String ACCESS_TOKEN=configProperties.getProperty("upload.destination.accesstoken");
	        
	        DbxRequestConfig config = new DbxRequestConfig(configProperties.getProperty("upload.destination.requestconfig"));
	        
	        //Set acces token and config for upload
	        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
	        SharedLinkMetadata slm;
			try {
				//Upload the file
				try (InputStream in = fileInputStream) {
				    FileMetadata metadata = client.files()
				    		.uploadBuilder(filePathAndName)
				    		.withMode(com.dropbox.core.v2.files.WriteMode.OVERWRITE)
				        .uploadAndFinish(in);
				}
				
				//Get Shared Link
				slm = client.sharing().createSharedLinkWithSettings(filePathAndName, 
						SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC)
						.build());
				  url = slm.getUrl();
			} catch (CreateSharedLinkWithSettingsErrorException e) {
				ListSharedLinksResult result = client.sharing().listSharedLinksBuilder()
					    .withPath(filePathAndName).withDirectOnly(true).start();
				url = result.getLinks().get(0).getUrl();
			}
			//TODO: Put the replace URL's in cnfigurable file
			String contentUrl = url.replace("www.dropbox.com", "dl.dropboxusercontent.com");
			UploadFileResponse uploadFileResponse = new UploadFileResponse(url, contentUrl);
			return uploadFileResponse;
			
		default:
			throw new ServicesException("730");
		}
	}
	
	public UploadFileResponse uploadFile(MultipartFile file, String fileName) throws ServicesException {
		
		try {
			return uploadFile(file.getInputStream(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServicesException("608");
		}
	}
}
