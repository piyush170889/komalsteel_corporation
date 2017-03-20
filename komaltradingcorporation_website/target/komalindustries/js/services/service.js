
var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {

	return $resource('rest/security/:action', {}, {
		authenticate : {
			method : 'POST',
			params : {
				'action' : 'authenticate'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},
	});
});



services.factory('userRegisterService', function($resource){
	return $resource('rest/register',{});
});

services.factory('changePasswordService', function($resource){
	return $resource('rest/user/changepassword',{oldPassword:'@oldPassword' ,newPassword:'@newPassword' });
});

services.factory('forgotPasswordService', function($resource){
	return $resource('rest/forgotpassword/checkemail',{email:'@email'});
});

services.factory('checkPwdService',function($resource){
	return $resource('rest/user/checkpwd',{pwd:'@password'});
});


services.factory('resetPasswordService', function($resource){
	return $resource('rest/forgotpassword/resetpassword',{email:'@email' ,ans :'@hintAnswer'});
});

app.factory('Scopes', function ($rootScope) {
    var mem = {};

    return {
        store: function (key, value) {
            $rootScope.$emit('scope.stored', key);
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        } 
    };
});


/*$==============login page validation ================$*/
app.service('loginValidationService', function($rootScope){
	this.loginForm = function() {
		var username = $rootScope.username;
		var password = $rootScope.password;
		if ((username == null) || (username == "")) {
			$rootScope.uerror = "Please enter email ID....!";
			return false;
		}
		$rootScope.uerror = "";
		
		if ((password == null) || (password == "")) {
			$rootScope.passerror = "Please enter password...!";
			return false;
		}
		$rootScope.passerror = "";
	};
});


