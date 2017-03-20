app.controller('productController',function(productService,$scope,RESOURCES,$state,$stateParams,$localStorage){
	
var productPromise=productService.getProductDetails($stateParams.cid,$stateParams.scid);
    productPromise.then(function(data){
    $scope.productDetails=data.data.resposne;	
	//console.log(" product "+JSON.stringify($scope.productDetails));
    },
		function(error){
	
    })

     $scope.mySplit = function(string, nb) {
        var array = string.split('-');
        return array[nb];
    }
    
   $scope.getRange=function(masterCartonQtyRange,masterCartonQtyIncVal){
    	var input=['0'];
    	var value=masterCartonQtyRange.toString();
    	var values=value.split('-');

        var minValue = values[0];
        var maxvalue = values[1];
        var count="";
        for (var i=0; i<=maxvalue; i++) {
          	count=i;
          	if(count < 1){
          		i=i+parseInt(masterCartonQtyIncVal);
          	}else{
          		i=i+parseInt(masterCartonQtyIncVal)-1;
          	}
          	
        	 input.push(i);
          }

          return input;
    } 
   
   
   $scope.addToCart=function(product){
	   console.log(" product deta "+product);

   	if($localStorage.cart == undefined){
   		$localStorage.cart=[];
   	}
   	
   	angular.forEach(product, function(value, key) {  
   		console.log(value.itemNm);  
   	}); 
   	
   //    console.log(item.itemMasterDtlsId);
       
       var found = $filter('filter')($localStorage.cart, {itemMasterDtlsId: item.itemMasterDtlsId}, true);
       if (found.length) {
       	//console.log( " in if "+found.length);
        $filter('filter') ($localStorage.cart, {itemMasterDtlsId : item.itemMasterDtlsId})[0].qty+=parseInt($scope.quantity);
           $scope.selected = JSON.stringify(found[0]);
       } else {
       	//console.log( " in if "+found.length);
       	$localStorage.cart.push({itemMasterDtlsId:item.itemMasterDtlsId,itemNm:item.itemNm,mrp:item.mrp,qty:parseInt($scope.quantity),img:itemPhoto[0].itemImgData,"isOfferApld":"false","offerId":"0"});
       }
      $state.go('cart');
   
	   
	   
   }
   
   
   
   })