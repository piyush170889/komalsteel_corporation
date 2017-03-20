app.controller('cartController',function($scope,categoryService,$log,RESOURCES,$localStorage,$rootScope,taxService,cartService,$state){
	console.log(" in cart  "+JSON.stringify($localStorage.cart));
	$scope.cartDetails=$localStorage.cart;
	 
	
	//calculate no of items in cart
	$rootScope.getTotalQuantity=function(){
		 var total = 0;
		    angular.forEach($localStorage.cart, function(value, key) {
		    	  console.log(key + ': ' +value);
		          total +=value.itemQty;
		          console.log(value.itemQty);
		    });
		    return total;
	}
	
	//calculate sub total
	$scope.getSubTotal=function(){
	    var total = 0;
	    angular.forEach($localStorage.cart, function(value, key) {
	    	  total +=value.itemPrice * value.itemQty;
	          console.log(value.itemPrice * value.itemQty);
	    });
	    return total;
	}
	
	 var promise=taxService.getServiceTax();
	 promise.then(function(data){
	 var response=data.data.request;	
	 console.log(" service tax "+response.configVal);
	 $scope.serviceTax=response.configVal;
	 })
	
	
	var promiseVat=taxService.getVat();
	 promiseVat.then(function(data){
	 var response=data.data.request;	
	 console.log(" vat "+response.configVal);
	 $scope.vat=response.configVal;
	 })
	
	
	//calcualte grand total
	$rootScope.getGrandTotal=function(){
		return ($scope.getSubTotal()* ($scope.serviceTax/100)) +$scope.getSubTotal();
	}
	
	
	//drop items from cart
	 $scope.dropProduct=function(){
		 $localStorage.cart.splice(this.$index, 1); 
	 }	
	 
	 
	 //print a div
	 $scope.printDiv = function(divName) {
		  var printContents = document.getElementById('#content').innerHTML;
		  var popupWin = window.open('', '_blank', 'width=300,height=300');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
		  popupWin.document.close();
	};
	 
	 

	
	
	 
	 $scope.completeOrder=function(){
		 
		 if($localStorage.user == undefined){
				angular.element( document.querySelector('#login')).modal('show');
			    return;	 
		 }
		 
		 console.log("getSubTotal  "+$scope.getSubTotal());
		 console.log("getGrandTotal  "+$rootScope.getGrandTotal());
		 console.log("serviceTax  "+$scope.serviceTax);
		 console.log("vat  "+$scope.vat);
		 $scope.cartItemsList=[];
		 angular.forEach($localStorage.cart, function(value, key) {
		 $scope.cartItemsList.push({"itemName":value.itemNm,
		        "itemPrice":value.itemPrice,
		        "itemQty":value.itemQty,
		        "itemMasterDtlsId":value.itemMasterDtlsId,
		        "isOfferApld":"false",
		         "offerId":"0" })
	    });
		 
		 var data ={"request": [{
				"cartNotes": "Test Notes",
				"cartPrice": $scope.getSubTotal(),
				"isOfferApld": "false",
				"offerApldId": "0",
				"alternateCntc":$rootScope.bphone,
				"dlvryType": "COD",
				"addressType": "Shipping",
				"city": $scope.$root.city.locationName,
				"country": "India",
				"postalCode":$scope.$root.bzipcode,
				"state":$scope.$root.state.locationName,
				"paymentAmount":$scope.getSubTotal(),
				"paymentGateway":"None",
				"paymentMode":"COD",
				"amountBal":$scope.getSubTotal(),
				"amountPaid":"0",
				"grandTotal":$rootScope.getGrandTotal(),
				"miscCharges":"0",
				"serviceTax":$scope.serviceTax,
				"serviceTaxValue":$scope.serviceTax,
				"shippingCharges":"0",
				"subTotal": $scope.getSubTotal(),
				"vat":$scope.vat,
				"vatValue":$scope.vat,
				"latitude":"0",
				"longitude":"0",
				"stAddress1":$rootScope.baddress,
				"cartItemsList": $scope.cartItemsList
		    }]}
		 
		 
		 if($localStorage.user != undefined){
			    var trackId=$localStorage.user.userTrackId;
				var promise=cartService.addCartDetails(data,trackId);
			   	promise.then(function(data){
			   		if(data.data.responseMessage.status == '200'){
			   		  console.log(" added success fully");	
					  $state.go('checkout-complete');
			   		}
			   		else{
			   			alert("Error while placing order")
			   		}
				},function(error){
					console.log("error while adding cart details  ");
				})
		 }
		
		
	 }
	 
	
	 
	 
});