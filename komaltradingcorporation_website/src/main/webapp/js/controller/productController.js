app.controller('productController',function(productService,$scope,RESOURCES,$state,$stateParams,$localStorage,$filter){
	
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
   		if(value.modifiedTs > 0){
   			console.log(" in if condition ");
   	 var found = $filter('filter')($localStorage.cart, {itemMasterDtlsId: value.itemMasterDtlsId}, true);
     if (found.length) {
     	console.log( " in if "+found.length);
      $filter('filter') ($localStorage.cart, {itemMasterDtlsId : value.itemMasterDtlsId})[0].itemQty+=parseInt(value.modifiedTs);
         $scope.selected = JSON.stringify(found[0]);
     } else {
     	console.log( " in else "+found.length);
     	$localStorage.cart.push({itemMasterDtlsId:value.itemMasterDtlsId,itemNm:value.itemNm,itemPrice:value.itemPrice,itemsInMasterCarton:value.itemsInMasterCarton,uom:value.uom,itemQty:parseInt(value.modifiedTs),img:value.itemImage,"isOfferApld":"false","offerId":"0"});
     }
   		}
   	}); 
    $state.go('cart');
   
	   
	   
   }
   
   
   
   })