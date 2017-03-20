app.constant('RESOURCES',(function(){
	 // Define your variable
	  var resource = 'http://192.168.1.15:8081/basemodule/';
	  // Use the variable in your constants
	  return {
		BASE_URL:  resource,
		STATE_LIST:resource+'/getStateList',  
		CITY_LIST :resource+'/getCityList?stateId=',
		CATEGORIES:resource+'/categoryandsubcategoryalldetails',
		SERVICE_TAX:resource+'/configuration/ServiceTax',
		VAT_TAX:resource+'/configuration/VatTax',
		PRODUCT_DETAIL:resource+'/productdetails?companyinfoid=56&categoryid=1&subcategoryid=28',
		CART_DETAIL:resource+'/cartdetails/',
		LOGIN:resource+'/login',
		REGISTER:resource+'/userdetails',
		UPDATE:resource+'/userdetails/',
		SEND_OTP:resource+'/sendotp',
		VERIFY_OTP:resource+'/verifyotp',
		FORGOT_PASSWORD:resource+'/forgetpassword/',
		RESET_PASSWORD:resource+'/resetpassword',
		CHANGE_PASSWORD:resource+'/changepassword/',
		GET_USER:resource+'/userdetails/',
		GET_ORDER_HISTORY:resource+'/cartdetails/',
		GET_SUB_CATEGORIES:resource+'/categoryandsubcategory/56',
	    USERS_DOMAIN: resource,
	    USERS_API: resource + '/users',
	    BASIC_INFO: resource + '/api/info'
	  }
})());

/*
app.controller("ExampleCtrl", function(RESOURCES){
  $scope.domain = RESOURCES.USERS_DOMAIN;
});*/

