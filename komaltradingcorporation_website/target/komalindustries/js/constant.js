app.constant('RESOURCES',(function(){
	 // Define your variable
	  var resource = ' http://192.168.1.17:8081/basemodule';
	  // Use the variable in your constants
	  return {
		BASE_URL:  resource,
		STATE_LIST:resource+'/getStateList',  
		CITY_LIST :resource+'/getCityList?stateId=',
		CATEGORIES:resource+'/categoryandsubcategoryalldetails',
		SERVICE_TAX:resource+'/configuration/ServiceTax',
		VAT_TAX:resource+'/configuration/VatTax',
		PRODUCT_DETAIL:resource+'/productdetails?companyinfoid=56&categoryid=1&subcategoryid=28',
	    USERS_DOMAIN: resource,
	    USERS_API: resource + '/users',
	    BASIC_INFO: resource + '/api/info'
	  }
})());

/*
app.controller("ExampleCtrl", function(RESOURCES){
  $scope.domain = RESOURCES.USERS_DOMAIN;
});*/

