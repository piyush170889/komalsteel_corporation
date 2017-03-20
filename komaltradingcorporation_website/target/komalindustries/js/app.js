var app = angular.module('exampleApp', ['ui.router','ngCookies','exampleApp.services', 'ngStorage'])
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

            $stateProvider

                .state('login', {
                    url: '/login',
                    templateUrl: 'pages/login.html',
                    controller: 'LoginController',
                    data: {
                        requireLogin: false
                    }
                })

                .state('index', {
                    url: '/',
                    templateUrl: 'pages/home.html',
                    controller:'homeController'	
                }) 
                
                .state('contact', {
                    url: '/contact-us',
                    templateUrl: 'pages/contact-us.html'
                })
                
                .state('product',{
                	url:'/product/:cid/:scid',
                	templateUrl:'pages/single-product.html',	
                	controller:'productController'	
                })
                
                 .state('cart',{
                	url:'/cart',
                	templateUrl:'pages/cart.html'	
                })
                

                $urlRouterProvider.otherwise('/');

            /*
             * Register error provider that shows message on
             * failed requests or redirects to login page on
             * unauthenticated requests
             */
            $httpProvider.interceptors.push(function ($q, $rootScope, $location, $timeout, $injector, $log) {
                return {
                    'responseError': function (rejection) {
                    	var status = rejection.status;
                    	//$log.debug(" interceptors Response Error "+JSON.stringify(rejection));
                        
                    	if(rejection.data.responseMessage != undefined){
                    	$rootScope.showAlert = true;
  			 		    $rootScope.alertType="danger";
  			 		 
  			 		    $rootScope.alertMsg=rejection.data.responseMessage.message;
  			 		     $timeout(function () {
  			 			  $rootScope.showAlert =false;
  			    	      }, 3000);
                    }
                    	 
                        
                        
                        return $q.reject(rejection);
                    },

                    // On response success
                    'response': function (response) {
                      /*alert(response);*/
                    //	$log.debug(" interceptors Response "+JSON.stringify(response));
                    	$rootScope.error = false;
                    	$rootScope.oldPwdWrong =false;
                    	$rootScope.myStatus = response.status;
                        var status = response.status;
                        var config = response;
                       /* $log.debug(status + "responseeeeee");*/
                        /*   $log.debug(config); */
                        if(status == 401){
                        	$rootScope.error =status + "Unathorized";
                        	$timeout(function () {
                        		  $rootScope.error = false;
                        	}, 4000);
                        }
                        
                        if(status == 203){
                        	$rootScope.oldPwdWrong =" Please check your old password...!";
                        	$timeout(function () {
                        		$rootScope.oldPwdWrong = false;
                        		/*$injector.get('$state').transitionTo('');*/
                        	}, 4000);
                        }
                       
                        // Return the response or promise.
                        return response || $q.when(response);
                    }
                };
            });
            
            /*
             * Registers auth token interceptor, auth token is
             * either passed by header or by query parameter as
             * soon as there is an authenticated user
             */
            $httpProvider.interceptors
                .push(function ($q, $rootScope, $location,$log,$sessionStorage) {
                    return {
                        'request': function (config) {
                            	var status =config.status;
                            	/* var url =config.url;
                             */
                        	/*$log.debug(config+"requestttttttttt");*/
                            var isRestCall = config.url
                                    .indexOf('rest') == 0;
                            
                            if($sessionStorage.authToken !=undefined){
                           	 $rootScope.authToken= $sessionStorage.authToken;
                           }
                           
                           if($sessionStorage.user !=undefined){
                           	 $rootScope.user=$sessionStorage.user;
                           }
                            if (isRestCall
                                && angular
                                    .isDefined($rootScope.authToken)) {
                                var authToken = $rootScope.authToken;
                                if (exampleAppConfig.useAuthTokenHeader) {
                                    config.headers['X-Auth-Token'] = authToken;
                                } else {
                                    config.url = config.url
                                        + "?token="
                                        + authToken;
                                }
                            }
                            return config
                                || $q.when(config);
                        },
                    
                    requestError: function(rejectReason) {
                    	/*$log.debug(rejectReason+"requestErrrorrr");*/
                    }
                    };
                });

        }]
).run(
    function ($rootScope, $location, $cookieStore, UserService,
              $state,$sessionStorage) {
        // ------------ mock $http requests ---------------------
        $rootScope.$state = $state;
        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function () {
            delete $rootScope.error;
        });
        
        $rootScope.$on('scope.stored', function (event, data) {
           /* console.log("scope.stored.......", data);*/
        });
        
        $rootScope.$on('user', function (event, data) {
            /* console.log("scope.stored.......", data);*/
         });
        
        $rootScope.$on('scope.storeds', function (event, data) {
            /* console.log("scope.stored.......", data);*/
         });

        $rootScope.hasRole = function (role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.roles[role] === undefined) {
                return false;
            }

            return $rootScope.user.roles[role];
        };

        $rootScope.$watch('user', function() {
        	//alert(JSON.stringify($rootScope.user));
        	//alert(JSON.stringify($sessionStorage.user));
            $rootScope.user = $sessionStorage.user;
        });
        
        $rootScope.logout = function () {
            delete $rootScope.user;
            delete $rootScope.authToken;
            delete $sessionStorage.authToken;
            delete $sessionStorage.user;
            $cookieStore.remove('authToken');
            $location.path("/login");
        };

        /* Try getting valid user from cookie or go to login page */
        var originalPath = $location.path();
             if($sessionStorage.authToken === undefined){
            // $location.path("/login");
             }
        var authTokenSession=$sessionStorage.authToken;
        if(sessionStorage.authToken !=undefined){
        	 $rootScope.authToken = authTokenSession;
             UserService.get(function (user) {
                 $rootScope.user = user;
                 $sessionStorage.user=user;
                 $location.path(originalPath);
             });
        }
        
        var authToken = $cookieStore.get('authToken');
        if (authToken !== undefined) {
            $rootScope.authToken = authToken;
            UserService.get(function (user) {
                $rootScope.user = user;
            	$sessionStorage.user=user;
            	$location.path(originalPath);
            });
        }
        
        $rootScope.initialized = true;
    });



