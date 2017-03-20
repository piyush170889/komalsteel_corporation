package com.replete.komalapp.Config;

/**
 * Created by MR JOSHI on 05-Jan-16.
 */
public class ConfigUrls {

//    public static String IP = "http://192.168.0.104:8080/basemodule";//test url

    public static String IP = "http://66.45.245.70:81/komal";//new live url
//    public static String IP = "http://ko|malindustriesportal-env.us-west-2.elasticbeanstalk.com";//live url

    //        public static String IP = "http://komalindustries-env.us-west-2.elasticbeanstalk.com";
//        public static String IP = "http://192.168.1.101:8080/komalindustries";
    public static final String URL_FORGET_PASSWORD = IP + "/forgetpassword/";//{contactNo}
    public static String URL_LOGIN = IP + "/login";
    public static String URL_UPDATE_PROFILE = IP + "/userdetails/";//{trackid}
    public static final String URL_REGISTER = IP + "/userdetails";
    public static final String URL_GET_PRODUCT_LIST = IP + "/categoryandsubcategory/";//{companyinfoid}";
    public static Object URL_GET_PRODUCT_DETAILS = IP + "/mproductdetails";

    public static String URL_DEALER_ASSOCIATED_DISTRIBUTOR = IP + "/delearassociateddistributors/";//{trackid}";
    public static String URL_DELETE_DEALER_ASSOCIATED_DISTRIBUTOR = IP + "/deletedelearassociateddistributors/";//{trackid}";
    public static final String URL_GET_ALL_DISTRIBUTOR = IP + "/distributors/";//{DealerTrackId}/";
    public static final String URL_SEND_OTP = IP + "/sendotp";
    public static final String URL_CONFIRM_OTP = IP + "/verifyotp";
    public static final String URL_GET_ORDER_STATUS = IP + "/cartdetails/";//{trackId}/{pageNum}";
    public static final String URL_CHANGE_PASSWORD = IP + "/changepassword/";//{id}";
    public static final String URL_PLACE_ORDER = IP + "/cartdetails/";//{trackId}";
    public static final String URL_GET_TAXES = IP + "/configuration/VatTax";
    public static final String URL_GET_SERVICE_TAXES = IP + "/configuration/ServiceTax";
    public static final String URL_GET_CATEGORY = IP + "/getcategory/";
    public static final String URL_RESET_PASSWORD = IP + "/resetpassword/";
    public static final String URL_GET_SUBCATEGORY = IP + "/getsubcategory/";
    public static final String URL_GET_PROFILE = IP + "/getuserprofileinfo/";//{trackId}";
    public static final String URL_GET_SUBCATEGORY_OF_CATEGORY = IP + "/categoryandsubcategorydetailsbycategoryid/";
    public static final String URL_GET_STATE = IP + "/getStateList";
    public static final String URL_GET_CITY = IP + "/getCityList";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "ANHIVE";
    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";


}
