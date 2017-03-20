app.directive('state',['cityService',function(cityService,$rootScope){
	return{
		restrict:'EA',
		controller :function($scope,stateService){
			var promise=stateService.getSateList();
			promise.then(function(data){
				$scope.stateList=data.data.response;
				console.log(" dsd "+JSON.stringify($scope.stateList));
			},function(error){
				
			})
		},
		link:function(scope,attrs,elements,controller){
			scope.stateList=controller.stateList;
            scope.$watch('$root.state', function(newValue, oldValue) {
               if (newValue){
            	   var stateId=scope.$root.state.locationId;
          	var cityPromise=cityService.getCityList(stateId);
       			cityPromise.then(function(data){
       			scope.$root.cityList=data.data.response;
       		    	});
       		     }
           }, true);
     	},
		 
     	//template:"<select ng-model='$root.state' class='form-control'  ng-disabled='isAddReadOnly' ><option ng-repeat='s in stateList' ng=value='{{s.locationName}}' ng-selected='stateSelected == s.locationName'>{{s.locationName}}</option></select>"
    	template:"<select ng-model='$root.state' ng-value='stateValue' class='form-control' ng-options='state as state.locationName for state in stateList track by state.locationName' ></select>"
	}
}])


app.directive('city',function(){
	return{
		restrict:'EA',
		link:function(scope,attrs,elements,controller){
			
		},
		//template:"<select ng-model='$root.city' class='form-control'  ng-disabled='isAddReadOnly' ><option ng-repeat='c in cityList' ng-value='{{c.locationName}}' ng-selected='citySelected == c.locationName'>{{c.locationName}}</option></select>"
	    	
		template:"<select ng-model='$root.city' class='form-control' ng-value='cityValue' ng-options='city as city.locationName for city in cityList track by city.locationName' ></select>"
	}
})


/*app.directive('pwCheck', [function () {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        var firstPassword = '#' + attrs.pwCheck;
        elem.add(firstPassword).on('keyup', function () {
          scope.$apply(function () {
            var v = elem.val()===$(firstPassword).val();
            ctrl.$setValidity('pwmatch', v);
          });
        });
      }
    }
  }]);*/
