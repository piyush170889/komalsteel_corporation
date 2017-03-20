app.controller('homeController',function($scope,categoryService,$log,RESOURCES){
	
	console.log(" console res "+RESOURCES.CATEGORIES);
	
	  var promise =categoryService.getAllCategories();
      promise.then(
         function(data) {
        	 $scope.categories = data.data.categoryAndSubCatDetails;
        	 console.log("data  "+JSON.stringify($scope.categories));
             
         },
         function(error) {
             console.log('failure loading movie', error);
         });
	

})