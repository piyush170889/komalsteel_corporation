app.factory('taxService', function($http,RESOURCES) {
	
	return {
	        getServiceTax:function(){
	       return  $http.get(RESOURCES.SERVICE_TAX);
	    	},
	    	getVat:function(){
		    return     $http.get(RESOURCES.VAT_TAX);
		     }
	
	    }
});