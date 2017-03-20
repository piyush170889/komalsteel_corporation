
app.factory('categoryService', function($http,RESOURCES) {
	return {
	      getMovie: function(id) {
	         return $http.get('/api/v1/movies/' + id);
	      },
	     
	      getAllCategories:function(){
	    	 return $http.get(RESOURCES.CATEGORIES);
	      }
	
	    }
});