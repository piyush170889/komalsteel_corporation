package co.in.replete.komalindustries.service;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.PayUMoneyResponseDetails;
import co.in.replete.komalindustries.beans.PaymentDetailsRequest;
import co.in.replete.komalindustries.dao.PaymentDAO;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentDAO paymentDAO;
	
	@Autowired
	Properties configProperties;
	
	/**
	 * Description :
	 * @param  {@link PaymentDetailsRequest}
	 * @param  {@link HttpServletResponse}
	 * @throws Exception 
	 */
	public BaseWrapper doAcceptPayment(PaymentDetailsRequest request/*, HttpServletResponse servletResponse*/) throws Exception {
		
		String surl = configProperties.getProperty("payment.surl");
		String furl = configProperties.getProperty("payment.furl");
        String testServerMerchantKey = configProperties.getProperty("payment.merchantKey");
        String testServerServiceProvider=configProperties.getProperty("serviceProvider");
        String testServerPaymentUrl = configProperties.getProperty("payment.paymentUrl");
        String testServerSalt = configProperties.getProperty("payment.salt");
        
        String txnId = "";
        String hash = null;
        MultiValueMap<String, Object> paramsToPost = new LinkedMultiValueMap<String, Object>();
        RestTemplate rest = new RestTemplate();
        
        //Get Transaction Id
        txnId = getTransactionId();

        if (!txnId.isEmpty()) {
        	
    		//Prepare parameters to post to gateway
        	
        	//For Production Server use this merchan key
        	//paramsToPost.add("key", productionServerMerchantKey);
            paramsToPost.add("key", testServerMerchantKey);
            
            paramsToPost.add("txnid", txnId);
            paramsToPost.add("amount", request.getPaymentAmount());
            paramsToPost.add("firstname", request.getFirstName().trim());
            paramsToPost.add("email", request.getEmailId().trim());
            paramsToPost.add("phone", request.getContact().trim());
            paramsToPost.add("productinfo", request.getProductInfo().trim());
            paramsToPost.add("surl", surl);
            paramsToPost.add("furl", furl);
            paramsToPost.add("udf1", request.getPaymentDtlsId().trim());
            paramsToPost.add("udf2", "");
            paramsToPost.add("udf3", "");
            paramsToPost.add("udf4", "");
            paramsToPost.add("udf5", "");
            paramsToPost.add("udf6", "");
            paramsToPost.add("udf7", "");
            paramsToPost.add("udf8", "");
            paramsToPost.add("udf9", "");
            paramsToPost.add("udf10", "");
            
            paramsToPost.add("service_provider", testServerServiceProvider);

            //For Production Server use this salt 
            //hash = calculateHash(paramsToPost, productionServerSalt);
            hash = calculateHash(paramsToPost, testServerSalt);
            
            paramsToPost.add("hash", hash);

            //For Production Server use this 
            //URI result = rest.postForLocation(productionServerPaymentUrl, paramsToPost);
    		URI result = rest.postForLocation(testServerPaymentUrl, paramsToPost);
    		
    		/*//Prepare response and return
    		SingleValueCommonClass singleValueCommonClass = new SingleValueCommonClass();
    		singleValueCommonClass.setValue(result.toString());
    		
    		PaymentResponse response = new PaymentResponse();
    		response.setSingleValueCommonClass(singleValueCommonClass);
    		return response;*/
    		System.out.println(result.toString());
    		return new BaseWrapper();
/*    		System.out.println(result.toString());
    		System.out.println(txnId);
    		servletResponse.sendRedirect(result.toString());*/
        } else {
        	throw new Exception("error occured");
        }
	}
	
	
	/**
	 * Description :
	 * @param  {@link PaymentDetailsRequest}
	 * @param  {@link HttpServletResponse}
	 * @throws Exception 
	 */
	public BaseWrapper doAcceptPayment(PaymentDetailsRequest request, HttpServletResponse servletResponse) throws Exception {
		
		String surl = configProperties.getProperty("payment.surl");
		String furl = configProperties.getProperty("payment.furl");
        String merchantKey = configProperties.getProperty("payment.merchantKey");
        String serviceProvider=configProperties.getProperty("serviceProvider");
        String paymentUrl = configProperties.getProperty("payment.paymentUrl");
        String salt = configProperties.getProperty("payment.salt");
        
        String txnId = "";
        String hash = null;
        MultiValueMap<String, Object> paramsToPost = new LinkedMultiValueMap<String, Object>();
        RestTemplate rest = new RestTemplate();
        
        //Get Transaction Id
        txnId = getTransactionId();

        if (!txnId.isEmpty()) {
        	
    		//Prepare parameters to post to gateway
        	
        	//For Production Server use this merchan key
        	//paramsToPost.add("key", productionServerMerchantKey);
            paramsToPost.add("key", merchantKey);
            
            paramsToPost.add("txnid", txnId);
            paramsToPost.add("amount", request.getPaymentAmount());
            paramsToPost.add("firstname", request.getFirstName().trim());
            paramsToPost.add("email", request.getEmailId().trim());
            paramsToPost.add("phone", request.getContact().trim());
            paramsToPost.add("productinfo", request.getProductInfo().trim());
            paramsToPost.add("surl", surl);
            paramsToPost.add("furl", furl);
            paramsToPost.add("udf1", request.getPaymentDtlsId().trim());
            paramsToPost.add("udf2", "");
            paramsToPost.add("udf3", "");
            paramsToPost.add("udf4", "");
            paramsToPost.add("udf5", "");
            paramsToPost.add("udf6", "");
            paramsToPost.add("udf7", "");
            paramsToPost.add("udf8", "");
            paramsToPost.add("udf9", "");
            paramsToPost.add("udf10", "");
            
            paramsToPost.add("service_provider", serviceProvider);

            hash = calculateHash(paramsToPost, salt);
            
            paramsToPost.add("hash", hash);

    		URI result = rest.postForLocation(paymentUrl, paramsToPost);
    		System.out.println(result.toString());
    		servletResponse.sendRedirect(result.toString());
    		return new BaseWrapper();
        } else {
        	throw new Exception("error occured");
        }
	}
	
	
	/**
	 * Description : Updates the status of the payment in the DB
	 * @param {@link PayUMoneyResponseDetails}
	 * @throws {@link ServicesException}
	 */
	@Override
	public void doUpdatePaymentDetails(PayUMoneyResponseDetails payUMoneyResponseDetails, HttpServletResponse servletResponse) throws Exception {
		
		String surl = configProperties.getProperty("payment.surl");
		String furl = configProperties.getProperty("payment.furl");
		String testServerMerchantKey = configProperties.getProperty("payment.testServerMerchantKey");
		String testServerSalt = configProperties.getProperty("payment.testServerSalt");
		String testServerServiceProvider=configProperties.getProperty("testServerServiceProvider");
		String updateSURL = configProperties.getProperty("updateSURL");

//      For Production Server use these values
//      String productionServerMerchantKey = "";
//      String productionServerServiceProvider = "payu_paisa";
//      String productionServerPaymentUrl = "https://secure.payu.in/_payment";
//      String productionServerSalt = "";
		
		String calculatedHash="";
		MultiValueMap<String, Object> paramsToPost = new LinkedMultiValueMap<String, Object>();
        
        //Prepare params to calculate hash
        
        //For Production Server use this merchan key
    	//paramsToPost.add("key", productionServerMerchantKey);
        paramsToPost.add("key", testServerMerchantKey);
        
        paramsToPost.add("txnid", payUMoneyResponseDetails.getTxnid().trim());
        paramsToPost.add("amount", payUMoneyResponseDetails.getAmount().trim());
        paramsToPost.add("firstname", payUMoneyResponseDetails.getFirstname().trim());
        paramsToPost.add("email", payUMoneyResponseDetails.getEmail().trim());
        paramsToPost.add("phone", payUMoneyResponseDetails.getPhone().trim());
        paramsToPost.add("productinfo", payUMoneyResponseDetails.getProductinfo().trim());
        paramsToPost.add("surl", surl);
        paramsToPost.add("furl", furl);
        paramsToPost.add("udf1", payUMoneyResponseDetails.getUdf1().trim());
        paramsToPost.add("udf2", "");
        paramsToPost.add("udf3", "");
        paramsToPost.add("udf4", "");
        paramsToPost.add("udf5", "");
        paramsToPost.add("udf6", "");
        paramsToPost.add("udf7", "");
        paramsToPost.add("udf8", "");
        paramsToPost.add("udf9", "");
        paramsToPost.add("udf10", "");
        paramsToPost.add("status", payUMoneyResponseDetails.getStatus().trim());

        //For Production Server use this service provider
        //paramsToPost.add("service_provider", productionServerServiceProvider);
        paramsToPost.add("service_provider", testServerServiceProvider);
        
        //For Production Server use this salt 
        //calculatedHash = calculateHashFromResponseParams(paramsToPost, productionServerSalt);
        calculatedHash = calculateHashFromResponseParams(paramsToPost, testServerSalt);
        
        if(calculatedHash.equals(payUMoneyResponseDetails.getHash().trim())){
//        	System.out.println("Here 1");
        	paymentDAO.updatePaymentDetails(payUMoneyResponseDetails);
        	servletResponse.sendRedirect(updateSURL);
        } else {
//        	System.out.println("Here 2");
        	servletResponse.sendRedirect(furl);
        }
	}
	
	//****************************************HELPER METHODS******************************************
		/**
	    *
	    * @return
	    */
	   private String getTransactionId(){
	       String txnid ="";
	       Random rand = new Random();
	       String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
	       txnid=hashCal("SHA-256",rndm).substring(0,20);
	       return txnid;
	   }

	   /**
	    *
	    * @param params
	    * @return
	    */
	   private String calculateHash( MultiValueMap<String, Object> params, String salt){
	       String hash="";
	       String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	       String hashString="";

	       try{
	           if( empty(params.get("key").toString())
	                   || empty(params.get("txnid").get(0).toString())
	                   || empty(params.get("amount").get(0).toString())
	                   || empty(params.get("firstname").get(0).toString())
	                   || empty(params.get("email").get(0).toString())
	                   || empty(params.get("phone").get(0).toString())
	                   || empty(params.get("productinfo").get(0).toString())
	                   || empty(params.get("surl").get(0).toString())
	                   || empty(params.get("furl").get(0).toString())/*
	                   || empty(params.get("service_provider").get(0).toString())*/
	                   )
	           {
	               throw new Exception("Required Values are empty");
	           }else{
	               String[] hashVarSeq=hashSequence.split("\\|");

	               for(String part : hashVarSeq)
	               {
	                   hashString= (empty(params.get(part).get(0).toString()))?hashString.concat(""):hashString.concat(params.get(part).get(0).toString());
	                   hashString=hashString.concat("|");
	               }
	               hashString=hashString.concat(salt);

	               hash=hashCal("SHA-512",hashString);
	           }

	       }catch(Exception e){
	           e.printStackTrace();
	       }
	       return hash;
	   }

	   private String calculateHashFromResponseParams(MultiValueMap<String, Object> params, String salt) {
		   String hash="";
	       String hashSequence = "status|udf10|udf9|udf8|udf7|udf6|udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|txnid|key";
	       String hashString="";

	       try{
	           if( empty(params.get("key").toString())
	                   || empty(params.get("txnid").get(0).toString())
	                   || empty(params.get("amount").get(0).toString())
	                   || empty(params.get("firstname").get(0).toString())
	                   || empty(params.get("email").get(0).toString())
	                   || empty(params.get("phone").get(0).toString())
	                   || empty(params.get("productinfo").get(0).toString())
	                   || empty(params.get("surl").get(0).toString())
	                   || empty(params.get("furl").get(0).toString())
	                   || empty(params.get("status").get(0).toString())/*
	                   || empty(params.get("service_provider").get(0).toString())*/
	                   )
	           {
	               throw new Exception("Required Values are empty");
	           }else{
	               String[] hashVarSeq=hashSequence.split("\\|");

	               for(String part : hashVarSeq)
	               {
	                   hashString= (empty(params.get(part).get(0).toString()))?hashString.concat(""):hashString.concat(params.get(part).get(0).toString());
	                   hashString=hashString.concat("|");
	               }
	               hashString=salt.concat(hashString);

	               hash=hashCal("SHA-512",hashString);
	           }

	       }catch(Exception e){
	           e.printStackTrace();
	       }
	       return hash;
	   }
	   
	   /**
	    *
	    * @param type
	    * @param str
	    * @return
	    */
	   private String hashCal(String type,String str){
	       byte[] hashseq=str.getBytes();
	       StringBuffer hexString = new StringBuffer();
	       try{
	           MessageDigest algorithm = MessageDigest.getInstance(type);
	           algorithm.reset();
	           algorithm.update(hashseq);
	           byte messageDigest[] = algorithm.digest();

	           for (int i=0;i<messageDigest.length;i++) {
	               String hex=Integer.toHexString(0xFF & messageDigest[i]);
	               if(hex.length()==1) hexString.append("0");
	               hexString.append(hex);
	           }

	       }catch(NoSuchAlgorithmException nsae){ }

	       return hexString.toString();
	   }

	   
	   /**
	    *
	    * @param s
	    * @return
	    */
	   private boolean empty(String s)
	   {
	       if(s== null || s.trim().equals(""))
	           return true;
	       else
	           return false;
	   }
}
