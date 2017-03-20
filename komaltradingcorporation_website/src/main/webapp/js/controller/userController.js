app.controller('userController',function(userService,$rootScope,$scope,$localStorage,$state,$stateParams,$sessionStorage){
	$scope.login=function(){
	//	alert(" Login called");
		
		var	data= { request:{
			"cmpnyInfoId":56,
			"loginId":$scope.mobileNo,
			"password":$scope.password
		}}
	 var promise=userService.login(data);
	 promise.then(function(data){
		 angular.element( document.querySelector( '#login')).modal('toggle');
		 $rootScope.user=$localStorage.user=data.data.userDetails;
		 $state.go($state.current,{},{reload:true});
	//	 alert(" login successfully "+JSON.stringify(data.data));
	 },function(error){
	//	 alert("Error while login");
	 })
	}
	
	$scope.register=function(){
		var data={
				"userDetails": {
					"firstName":$scope.firstName,
					"lastName":$scope.lastName,
					"cntc_num":$scope.$root.mobileNo,
					"cmpnyInfoId":"56",
					"password":$scope.password,
					"loginId":$scope.loginId
				}
		 }
		
	//	alert(" registration ");
		var promise=userService.register(data);
		promise.then(function(data){
		 angular.element( document.querySelector( '#signup')).modal('toggle');
			alert(" Registration success "+JSON.stringify(data.data));
		},function(error){
			alert(" Error while registration");
		})
	}
	
	//send otp
	
	$scope.sendOtp=function(){
		var data= { "request": {
			"cellnumber":$scope.$root.mobileNo,
			"deviceInfo":"9096409749"
		}}
		var promise=userService.sendOTP(data);
		promise.then(function(data){
		angular.element( document.querySelector('#register1')).modal('toggle');
		angular.element( document.querySelector('#register2')).modal('show');
		},function(error){
			
		})
	}
	
	
	//verify otp
	$scope.verifyOtp=function(){
		var data= { 
				"request": {
					"cellnumber":$scope.$root.mobileNo,
					"deviceInfo":"9096409749",
					"otp":$scope.otp
				}}
		var promise=userService.verifyOTP(data);
		promise.then(function(data){
		angular.element( document.querySelector('#register2')).modal('toggle');
		angular.element( document.querySelector('#signup')).modal('show');
		},function(error){
			
		})
	} 
	
	//logout user
	$rootScope.logout=function(){
		$localStorage.$reset();
	 $state.go('index',{},{reload:true});
	}
	
	//edit profile
	
	$scope.isReadOnly=true;
	$rootScope.isAddReadOnly=false;
	
	$scope.editProfile=function(){
		$scope.isReadOnly=false;
	}
	
	$scope.editAddress=function(){
		$rootScope.isAddReadOnly=false;
	}
	
	if($state.includes('my-profile')){
		//alert("in my profile ");
		var promise=userService.getUser($localStorage.user.cntc_num);
		promise.then(function(data){
		$scope.userDetails=data.data.getUserDetails[0];
		$rootScope.stateSelected="Maharashtra";
		$rootScope.citySelected="pune";
		/*$scope.$root.state={locationName:"Mumbai"};
		$scope.$root.city={locationName:"pune"};*/
	//	alert(JSON.stringify($scope.$root.state))
		//	alert(JSON.stringify($scope.$root.city))
		},function(error){
			
		})
	}
	
	if($state.includes('order-history')){
	//	alert("In order history");
     	var promiseOrder=userService.getOrderHistory($localStorage.user.userTrackId,1);
	//	var promiseOrder=userService.getOrderHistory('2d74965e-3234-11e6-9768-0ac722f45c59',1);
		promiseOrder.then(function(data){
			$scope.orderHistory=data.data.request;
	    },function(error){
			
		})
	}
	
	$scope.updateUser=function(){
		alert(" in update user ");
        var data={
		"request": {
			"firstName":$scope.userDetails.firstName,
			"lastName":$scope.userDetails.lastName,
			"cntc_num":$scope.userDetails.cntc_num,
			"cmpnyInfoId":"56",
			"password":"piyushqwe",
			"email":$scope.userDetails.loginId,
			"displayName":$scope.userDetails.displayName,
			"pincode":"",
			"address":"",
			"city":"",
			"state":""
		}}
        
        
        var promise=userService.updateUser(data,$localStorage.user.userTrackId);
        promise.then(function(data){
         alert(" in success "+JSON.stringify(data));
          $state.go($state.current,{},{reload:true});
        },function(error){
        	alert("Error while updating user");
        })
	     
	}
})