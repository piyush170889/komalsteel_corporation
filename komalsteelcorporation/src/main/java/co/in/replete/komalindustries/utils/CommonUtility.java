package co.in.replete.komalindustries.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtility {

	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	Properties configProperties;
	
	
	public static Date getParsedDate(String dateToParse) throws ParseException{
		 
		String[] formatStrings = new String[] {"M/y", "M/d/y", "M-d-y","dd-mm-yy","dd/mm/yyyy","dd.mm.yyyy","d month yyyy","yyyy-mm-dd"," MMM/DD/YYYY","yyyymmdd","dd-mm-yyyy","DD-MM-YY ","YYYYMMDD","dd-mmm-yyyy HH:MM:SS","yyyymmddTHHMMSS","yyyy-mm-dd HH:MM:SS",
				"mmm.dd.yyyy HH:MM:SS"};

		System.out.println("date To Parse : " + dateToParse);
		    return DateUtils.parseDateStrictly(dateToParse, formatStrings);
	}
	
	/**
	 * Description : Validates the input parameters for null check and empty
	 * @param Map<Object, Object>
	 * @return Object
	 */
	public Object isInputValid(Map<Object, Object> inputParams) {
		
		Object returnVal = null;
		
		Set<Object> keySet = inputParams.keySet();
		for(Object key : keySet) {
			Object inputParam = inputParams.get(key);
			if(null == inputParam) {
				returnVal = key;
				break;
			} else if ((inputParam instanceof String) && ((String) inputParam).isEmpty()) {
				returnVal = key;
				break;
			}
		}
		
		return returnVal;
	}
	
	/**
	 * Sends specified OTP to the specified contact number
	 * 
	 * @param		contactNumber		The contact number provided on registration form
	 * @param 		otp					The generated otp for the contact number 
	 * @return		isMessageSent		true if message sent successfully. false if message sending failed.
	 */
	/*public boolean sendOTP(String contactNumber, String otp) throws Exception{
//		final String ACCOUNT_SID = "AC6130640cfd9a61a2fc8b57db47469a91";		//Twilio Account SID
//		final String ACCOUNT_SID = "ACd19a1aadd92677851d9a29f1e3f8ba42";
		final String ACCOUNT_SID = "AC151401698d9f4c3ebf63aad80d317bd7";
		final String AUTH_TOKEN = "9319d868794a97d54b389c9238d9e369";
//		final String AUTH_TOKEN = "76261c96877af5ab1661a078f1df4ca8";
//		final String AUTH_TOKEN = "d9e42412213c2d53e00c9dbf2f5a82d8";			// Twilio Authentication Token
		final String EXT = "+91";												// Exension to be appended to the mmobile number	
		boolean isMessageSent = false;
//		final String FROM = "+15005550006";
//		final String FROM = "(205) 289-7085";
		final String FROM = "+12052897085";
		try{
			// Create a rest client
		    final TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		    // Get the main account (The one we used to authenticate the client)
		    final Account mainAccount = client.getAccount();

		    // Send an SMS (Requires version 3.4+)
		    final SmsFactory messageFactory = mainAccount.getSmsFactory();
		    final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
		    messageParams.add(new BasicNameValuePair("To", EXT + contactNumber.trim()));
//		    messageParams.add(new BasicNameValuePair("From", "(720) 606-4615")); 
		    messageParams.add(new BasicNameValuePair("From", FROM));
		    messageParams.add(new BasicNameValuePair("Body", otp));
		    messageFactory.create(messageParams);		//Send Messsage
		    isMessageSent = true;
		    return isMessageSent;
		}catch (TwilioRestException e) {
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.sms"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.sms"));
		}
		
	}*/
	
	/**
	 * Sends email to the toAddress specified, with message body containing the baseURL appended with activate user account page. This forms the activation link for the user's account
	 * 
	 * @param 		toAddress		The email address to which mail is to be sent
	 * @param 		baseURL			The base URl of the application
	 * @return		isEmailSent		true if mail is sent successfully.false if mail sending fails
	 */
	public boolean sendEmail(String toAddress, String message, String subject){
		
		final String PROP_USERNAME = configProperties.getProperty("email.username");
		
		final String PROP_PASSWORD = configProperties.getProperty("email.password");
		
		
		boolean isEmailSend = false;
		try{
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put(PROP_SMTP_HOST, PROP_HOST);
		properties.put(PROP_SMTP_PORT, PROP_PORT);
		properties.put(PROP_SMTP_AUTH, PROP_AUTH);
		properties.put(PROP_SMTP_STARTTLS_ENABLE, PROP_STARTTLS_ENABLE);
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROP_USERNAME, PROP_PASSWORD);
			}
		};
		Session session = Session.getInstance(properties, auth);
		// creates a new e-mail message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(PROP_USERNAME));
		String[] emailIds = toAddress.split(",");
				
		InternetAddress[] toAddresses = new InternetAddress[emailIds.length];
		for(int i=0; i<emailIds.length; i++) {
			toAddresses[i] = new InternetAddress(emailIds[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(message,"text/html");
		// sends the e-mail
		Transport.send(msg);
		isEmailSend = true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return isEmailSend;
	}
	
	
	/**
	 * Sends email to the toAddress specified, with message body containing the baseURL appended with activate user account page. This forms the activation link for the user's account
	 * 
	 * @param 		toAddress		The email address to which mail is to be sent
	 * @param 		baseURL			The base URl of the application
	 * @return		isEmailSent		true if mail is sent successfully.false if mail sending fails
	 */
	public boolean sendEmail(String toAddress, String message, String subject, String attachmentFilePath){
		
		final String PROP_USERNAME = configProperties.getProperty("email.username");
		
		final String PROP_PASSWORD = configProperties.getProperty("email.password");
		
		
		boolean isEmailSend = false;
		try{
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put(PROP_SMTP_HOST, PROP_HOST);
		properties.put(PROP_SMTP_PORT, PROP_PORT);
		properties.put(PROP_SMTP_AUTH, PROP_AUTH);
		properties.put(PROP_SMTP_STARTTLS_ENABLE, PROP_STARTTLS_ENABLE);
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROP_USERNAME, PROP_PASSWORD);
			}
		};
		Session session = Session.getInstance(properties, auth);
		// creates a new e-mail message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(PROP_USERNAME));
		String[] emailIds = toAddress.split(",");
				
		InternetAddress[] toAddresses = new InternetAddress[emailIds.length];
		for(int i=0; i<emailIds.length; i++) {
			toAddresses[i] = new InternetAddress(emailIds[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		
		// Create a multipar message
        Multipart multipart = new MimeMultipart();

		// Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        multipart.addBodyPart(messageBodyPart);	// Set text message part
        

        // Part two is attachment
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentFilePath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(attachmentFilePath);
        multipart.addBodyPart(attachmentBodyPart);

        // Send the complete message parts
        msg.setContent(multipart);
        
//		msg.setContent(message,"text/html");
		// sends the e-mail
		Transport.send(msg);
		isEmailSend = true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return isEmailSend;
	}
	

	/*public void sendEmailToAdmin(String message, String subject) {
final String userName =configProperties.getProperty("email.username");
		
		final String password = configProperties.getProperty("email.password");
		boolean isEmailSent = false;
		try{	
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put(configProperties.getProperty("smtp.server.host"),configProperties.getProperty("smtp.server.gmail"));
			properties.put("mail.smtp.port",587);
			properties.put("mail.smtp.auth", "true");
			properties.put(configProperties.getProperty("smtp.server.starttls"), configProperties.getProperty("smtp.server.starttls.value"));
			System.out.println(configProperties.getProperty("smtp.server.gmail"));
			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);
			// creates a new e-mail message
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(userName));
//			InternetAddress[] toAddresses = { new InternetAddress("tekchandbheda@gmail.com"), new InternetAddress("komaldahanu@gmail.com") };
			InternetAddress[] toAddresses = { new InternetAddress("piyush.jadhav@repleteinc.com"), new InternetAddress("piyushjadhav65@gmail.com") };
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
			mimeMessage.setSubject(subject);
			mimeMessage.setSentDate(new Date());
			
			// Create the message part 
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setContent(message,"text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			mimeMessage.setContent(multipart);
			
			mimeMessage.setText(message);
			
			// sends the e-mail
			Transport.send(mimeMessage);
			isEmailSent = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}*/
	
private final String PROP_SMTP_HOST = "mail.smtp.host";
	
	private final String PROP_SMTP_PORT = "mail.smtp.port";
	
	private final String PROP_SMTP_AUTH = "mail.smtp.auth";
	
	private final String PROP_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	private final String PROP_HOST = "smtp.gmail.com";
	
	private final String PROP_PORT = "587";
	
	private final String PROP_AUTH = "true";
	
	private final String PROP_STARTTLS_ENABLE = "true";
	
	public boolean sendEmailToAdmin(String message, String subject) {
		
		final String PROP_USERNAME = configProperties.getProperty("email.username");
		
		final String PROP_PASSWORD = configProperties.getProperty("email.password");
		
		
		boolean isEmailSend = false;
		try{
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put(PROP_SMTP_HOST, PROP_HOST);
		properties.put(PROP_SMTP_PORT, PROP_PORT);
		properties.put(PROP_SMTP_AUTH, PROP_AUTH);
		properties.put(PROP_SMTP_STARTTLS_ENABLE, PROP_STARTTLS_ENABLE);
		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROP_USERNAME, PROP_PASSWORD);
			}
		};
		Session session = Session.getInstance(properties, auth);
		// creates a new e-mail message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(PROP_USERNAME));
		String adminEmailList = configProperties.getProperty("email.admin.list");
		String[] emailIds = adminEmailList.split(",");
				
		InternetAddress[] toAddresses = new InternetAddress[emailIds.length];
		for(int i=0; i<emailIds.length; i++) {
			toAddresses[i] = new InternetAddress(emailIds[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(message,"text/html");
		// sends the e-mail
		Transport.send(msg);
		isEmailSend = true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return isEmailSend;
	}

	public String getGstCode(String gstNo) {
		System.out.println("Gst No.: " + gstNo + ", GstCode: " + gstNo.substring(0, 2));
		return gstNo.substring(0, 2);
	}

	public BigDecimal roundUpToTwoDecimal(Float value, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
	}
	
}
