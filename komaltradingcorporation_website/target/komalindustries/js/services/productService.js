app.factory('productService', function($http,RESOURCES) {
	
	return {
	        getProductDetails:function(ctgId,subId){
	         
	        	return $http.get(RESOURCES.BASE_URL+'/productdetails?companyinfoid=56&categoryid='+ctgId+'&subcategoryid='+subId);
	    	}
	
	    }
});


