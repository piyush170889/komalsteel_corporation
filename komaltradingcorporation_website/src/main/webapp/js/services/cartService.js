app.factory('cartService', function($http,RESOURCES) {
	return {
	     
	      addCartDetails:function(data,uid){
	    	 return $http.post(RESOURCES.CART_DETAIL+uid,JSON.stringify(data));
	      }
	
	    }
});