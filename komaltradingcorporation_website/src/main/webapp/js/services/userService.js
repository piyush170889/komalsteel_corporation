services.factory('userService', function($http,RESOURCES) {

	return {
		authenticate:function(){
			return $http.post(RESOURCES.LOGIN);
		},
		login:function(data){
			return $http.post(RESOURCES.LOGIN,JSON.stringify(data));
		},
		
		register:function(data){
			return $http.post(RESOURCES.REGISTER,JSON.stringify(data));
		},
		
		updateUser:function(data,userId){
			return $http.put(RESOURCES.UPDATE+userId,JSON.stringify(data));
		},
		
		sendOTP:function(data){
			return $http.post(RESOURCES.SEND_OTP,JSON.stringify(data));
		},
		
		verifyOTP:function(data){
			return $http.post(RESOURCES.VERIFY_OTP,JSON.stringify(data));
		},
        forgotPasword:function(contact_num){
        	return $http.get(RESOURCES.FORGOT_PASSWORD+contact_num);
        },
        resetPassword:function(data){
        	return $http.post(RESOURCES.FORGOT_PASSWORD,JSON.stringify(data));
        },
        changePassword:function(trackid){
        	return $http.post(RESOURCES.CHANGE_PASSWORD+trackid);
        },
        getUser:function(userId){
        	return $http.get(RESOURCES.GET_USER+userId);
        },
        getOrderHistory:function(userId,pageNo){
        	return $http.get(RESOURCES.GET_ORDER_HISTORY+userId+'/'+pageNo);
        }
        
        
		
	         
	
		
	}
	
});