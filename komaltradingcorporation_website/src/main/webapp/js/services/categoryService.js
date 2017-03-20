
app.factory('categoryService', function($http,RESOURCES) {
	return {
	      getMovie: function(id) {
	         return $http.get('/api/v1/movies/' + id);
	      },
	     
	      getAllCategories:function(){
	    	 return $http.get(RESOURCES.CATEGORIES);
	      },
	      
	      getAllSubCategories:function(){
	    	  return $http.get(RESOURCES.GET_SUB_CATEGORIES); 
	      }
	
	    }
});