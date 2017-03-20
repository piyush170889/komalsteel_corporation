app.factory('stateService', function($http,RESOURCES) {
	return {
        getSateList:function(){
          return $http.get(RESOURCES.STATE_LIST);
    	}

    }
});


app.factory('cityService', function($http,RESOURCES) {
	return {
        getCityList:function(stateId){
          return $http.get(RESOURCES.CITY_LIST+stateId);
    	}

    }
});