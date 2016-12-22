<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/Orders.tld"  prefix="order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Industries | Order Details</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
   
   	<!-- Required head CSS -->
	<jsp:include page="../includes/requiredheadcss.jsp" />
	<!-- ./Required head CSS -->   	
   	
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
 <!-- Custom Js -->
 	<script>
		 function getSubCatDetails(catId){
			$("#prodSubCat").html("");
			$("#prodSubCat").prop("disabled", true);
			$("#product1").html("");
			$("#product1").prop("disabled", true);
			
			if(catId == "Select") {
				alert("Please Select a Category");
			} else {
				$.ajax({
			        type : 'GET',
			        url : 'getProductSubcategory',
			        dataType : 'json',
			        data: "catId="+catId,
			        success : function(data){
			        	var listItems = "<option value='Select'>Select Product Sub Category</option>"; 
			        	for (var i = 0; i < data.length; i++){
			       	      listItems+= "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
			       	    }
			        	$("#prodSubCat").prop("disabled", false);
			        	$("#prodSubCat").html(listItems);
			        }
			    });
			}
		}
		 
		function populateproductsbysubcatid(subCatId) {
			$("#product1").html("");
			$("#product1").prop("disabled", true);
			
			if(subCatId == "Select") {
				alert("Please Select a Sub Category");
			} else {
				$.ajax({
			        type : 'GET',
			        url : 'getProductBySubcategoryId',
			        dataType : 'json',
			        data: "subCatId="+subCatId,
			        success : function(data){
			        	var listItems = "<option value='0'>Select Product</option>"; 
			        	for (var i = 0; i < data.length; i++){
			       	      listItems+= "<option value='" + data[i].itemMasterDtlsId + "'>" + data[i].itemNm + "</option>";
			       	    }
			        	$("#product1").prop("disabled", false);
			        	$("#product1").html(listItems);
			        }
			    });
			}
		}
		
		function getproductprice(prodId) {
			if(prodId != "0") {
				$('.masterCartonPrice').val("0");
				$('.totalPrice').val("0");
				$('.qty').val("0");
				$.ajax({
			        type : 'GET',
			        url : 'getProductPriceById',
			        dataType : 'json',
			        data: "productId="+prodId,
			        success : function(data){
			        	$('.masterCartonPrice').val(data.masterCartonPrice);
			        }
			    });
			} else {
				$('.masterCartonPrice').val("0");
				$('.totalPrice').val("0");
				$('.qty').val("0");
			}
		}
	</script>
	<script>
		function incrementQty() {
			 var $qty = $('.qty');
	         var qtyCounter = parseInt($qty.val());
	         qtyCounter++;
	         $qty.val(qtyCounter);
	         var totalPrice = $('.totalPrice');
	         var masterCartonPrice = $('.masterCartonPrice').val();
	         totalPrice.val(qtyCounter * parseInt(masterCartonPrice));
		}
		
		function decrementQty() {
			 var $qty = $('.qty');
	         var qtyCounter = parseInt($qty.val());
	         qtyCounter--;
	         if(qtyCounter < 0){
            	alert("Quantity must be positive number");
            }else{
	            $qty.val(qtyCounter);
	            var totalPrice = $('.totalPrice');
		        var masterCartonPrice = $('.masterCartonPrice').val();
		        totalPrice.val(parseInt(totalPrice.val()) - parseInt(masterCartonPrice));
            }
		}
		
		function incrementQtyForEdit() {
			 var $qty = $('.editqty');
	         var editqtyCounter = parseInt($qty.val());
	         editqtyCounter++;
	         $qty.val(editqtyCounter);
	         var totalPrice = $('.edittotalPrice');
	         var masterCartonPrice = $('.editmasterCartonPrice').val();
	         totalPrice.val(editqtyCounter * parseInt(masterCartonPrice));
		}
		
		function decrementQtyForEdit() {
			 var $qty = $('.editqty');
	         var editqtyCounter = parseInt($qty.val());
	         editqtyCounter--;
	         if(editqtyCounter < 0){
           	alert("Quantity must be positive number");
           }else{
	            $qty.val(editqtyCounter);
	            var totalPrice = $('.edittotalPrice');
		        var masterCartonPrice = $('.editmasterCartonPrice').val();
		        totalPrice.val(parseInt(totalPrice.val()) - parseInt(masterCartonPrice));
           }
		}
		
	   function sendEditCartItemDetails(cartItemDtlsId, itemNm, itemQty, itemMasterCartonPrice, itemTotalPrice){
		   document.getElementById("cartItemDtlsId1").value=cartItemDtlsId;
		   document.getElementById('itemNm1').value=itemNm;
		   document.getElementById('itemQty1').value=itemQty;
		   document.getElementById('itemPrice1').value=itemTotalPrice;
		   document.getElementById('itemCartonPrice1').value=itemMasterCartonPrice;
	   }
	</script>
 <!-- ./Custom JS -->
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="orderInjectedDAO" scope="page" value="${cartDAO}"/> 
    <div class="wrapper">
       <!-- Start Main Header -->
      <jsp:include page="../includes/header.jsp"></jsp:include>
      <!-- End Main Header -->
      
      <!-- Start Left Sidebar Menu -->
      <jsp:include page="../includes/leftsidebarmenu.jsp"></jsp:include>
	  <!-- End Left sidebar menu -->

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Order Details
          </h1>
          <c:if test="${not empty errorMssg }" >
	        <h4 style="color:red;">${errorMssg }</h4>
	      </c:if>
	      <c:if test="${not empty successMssg }">
			<h4 style="color:green;">${successMssg }</h4>
		  </c:if>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li><a href="#">Tables</a></li>
            <li class="active">Data tables</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
            	<div class="col-xs-12">
            	
				  <!-- Any HTML and JSP TAG will go here -->   
				    <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">Order Details</h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
		                <form class="form-horizontal" action="#" method="post">
		                  <div class="box-body">
		                 	<div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">OrderID</label>
		                      <label for="inputEmail3" class="col-sm-2  align-left control-label" style="text-align:left;">: ${ userorderDetails.cartDtlId}</label>
		                    </div>
		                    <div class="form-group">
			                	<label for="inputEmail3" class="col-sm-2 control-label">Order Date</label>
			                    <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.orderDate } </label>    	
			                    <label for="inputEmail3" class="col-sm-2 control-label">Order Status</label>
			                    <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.cartStatus } </label>
			                    <%-- <label for="inputEmail3" class="col-sm-2 control-label">Expected Delivery Date</label>
			                    <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${expectedDlvryDate}</label>
		                       --%>
		                   	</div>
		                   <%-- 	<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">Order Price</label>
		                  	  	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.cartPrice}</label>	
		                  	  	<label for="inputEmail3" class="col-sm-2 control-label">Payment Status</label>
		                       	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.paymentStatus} </label>	                   	
		                   	</div> --%>
		                   	<div class="form-group">
		                   		<label for="inputEmail3" class="col-sm-2 control-label">Order Notes</label>
		                  	  	<label for="inputEmail3" class="col-sm-8 control-label" style="text-align:left;">: ${userorderDetails.cartNotes }</label>	
		                   	</div>
		                  </div>
		                </form>
		              </div>
		              </div>
				</div>
		  </div> 
		  
		  <!-- Delivery Details -->
			<div class="row">
	            <div class="col-xs-12">
	
				  <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">Billing Details</h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
		                <form class="form-horizontal" action="#" method="post">
	                <div class="box-body">
	                    <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Contact </label>
		                      <label for="inputEmail3" class="col-sm-2  align-left control-label" style="text-align:left;">: ${userorderDetails.alternateContactNo }</label>
		                </div>
		                <div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Delivery Address</label>
		                     <label for="inputEmail3" class="col-sm-6 control-label"  style="text-align:left;">: ${userorderDetails.st1 }</label>	                    	
	                    </div>
	                    <div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">State</label>
	                      	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.state }</label>
	                    	<label for="inputEmail3" class="col-sm-2 control-label">City</label>
	                      	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.city }</label>
	                    </div>
	                    
	                </div>
	                </form>
	             </div>
	           </div>
	       </div>  
	       </div>   
	       <!-- ./Delivery Details -->
	       
	       <!-- Other Details -->
	       <div class="row">
	            <div class="col-xs-12">
	
				  <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">Shipping Details</h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
		                <form class="form-horizontal" action="#" method="post">
	                <div class="box-body">
	                <div class="form-group">
	                	 	<label for="inputEmail3" class="col-sm-2 control-label">LR NO </label>
		                    <label for="inputEmail3" class="col-sm-4 control-label"  style="text-align:left;">: ${userorderDetails.lrNo }</label>	
		                    <label for="inputEmail3" class="col-sm-2 control-label">LR Date</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">: ${userorderDetails.lrDate }</label>
	                    </div>
	                    <div class="form-group">
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Items Loaded</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">: ${userorderDetails.itemsLoaded }</label>
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Delivery Date </label>
		                      <label for="inputEmail3" class="col-sm-4  align-left control-label" style="text-align:left;">
			                      <c:choose>
			                      	<c:when test="${not empty expectedDlvryDate }">
			                      		: ${userorderDetails.expectedDlvrDate }
			                      	</c:when>
			                      	<c:otherwise>
			                      		: Not Specified
			                      	</c:otherwise>
			                      </c:choose>
		                      </label>
	                    </div>
	                	 <div class="form-group">
	                	 	<label for="inputEmail3" class="col-sm-2 control-label">Mark</label>
		                    <label for="inputEmail3" class="col-sm-4 control-label"  style="text-align:left;">
		                    	<c:choose>
			                      	<c:when test="${not empty userorderDetails.mark }">
			                      		: ${userorderDetails.mark }
			                      	</c:when>
			                      	<c:otherwise>
			                      		: Not Specified
			                      	</c:otherwise>
			                      </c:choose>
			                </label>
			                <label for="inputEmail3" class="col-sm-2 control-label">Destination</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
	                      		<c:choose>
			                      	<c:when test="${not empty userorderDetails.destination }">
			                      		: ${userorderDetails.destination }
			                      	</c:when>
			                      	<c:otherwise>
			                      		: Not Specified
			                      	</c:otherwise>
			                      </c:choose>
			                </label>
	                    </div>
	                    <div class="form-group">
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Transportation</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
	                      		<c:choose>
			                      	<c:when test="${not empty userorderDetails.tranNm }">
			                      		: ${userorderDetails.tranNm }
			                      	</c:when>
			                      	<c:otherwise>
			                      		: Not Specified
			                      	</c:otherwise>
			                      </c:choose>
			                </label>
			                <label for="inputEmail3" class="col-sm-2 control-label">TIN NO. : </label>
		                      <label for="inputEmail3" class="col-sm-4  align-left control-label" style="text-align:left;">
			                      <c:choose>
			                      	<c:when test="${not empty userorderDetails.tinNo }">
			                      		: ${userorderDetails.tinNo }
			                      	</c:when>
			                      	<c:otherwise>
			                      		: Not Specified
			                      	</c:otherwise>
			                      </c:choose>
		                      </label>
	                    </div>
	                </div>
	                </form>
	             </div>
	           </div>
	       </div>  
	       </div> 
	       <!-- ./Other Details -->
	       
            <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Ordered Items</h3>
                </div>
                <div class="row">
	                <div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
                		<%-- <form action="" method="get">
	                  		<input type="hidden" name="invoiceId" value="${invoiceId }" /> --%>
	                  		<form action="addCartItems" method="post">
								<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addCartProduct" >ADD ITEMS</button>
							</form>
						<%-- </form> --%>
                	</div>
               	</div>
                <!-- /.Box-Header -->
                
                 <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                         <th>Sr.No</th>
                        <th>Item Name</th>
                        <th>Carton Quantity</th>
                        <!-- <th>Carton Price</th>
                         <th>Total Price</th> --> 
                         <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                      <!-- user Details -->
                         <order:orderItemDetails cartDAO="${orderInjectedDAO}" orderId="${orderId}"/> 
                      <!-- ./user Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                         <th>Sr.No</th>
                        <th>Item Name</th>
                        <th>Carton Quantity</th>
                        <!-- <th>Carton Price</th>
                         <th>Total Price</th>  -->
                         <th>Actions</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
                <!-- /.box-body -->
              </div>
              <!-- /.box -->
            </div><!-- /.col -->  
          </div><!-- /.row -->
          
          <%-- <div class="row">
            	<div class="col-xs-12">
            	
				 <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">
			                  	Invoice Details
			                  </h3>
			                <div class="row">
				                <div class="col-xs-12">
			                		<!-- Trigger the modal with a button -->
			                		<form action="" method="get">
				                  		<input type="hidden" name="invoiceId" value="${invoiceId }" />
										<button type="button" class="btn btn-primary btn-md" >PRINT INVOICE</button>
									</form>
									<form action="" method="get">
				                  		<input type="hidden" name="invoiceId" value="${invoiceId }" />
				                  		<button type="button" class="btn btn-danger btn-md" data-toggle="modal" data-target="#addDiscount">ADD DISCOUNT</button>
				                  	</form>
			                	</div>
		                	</div>
		                	</div>
		                <!-- /.Box-Header -->
		              <div class="box box-info" style="overflow-x:hidden;">
		                <form class="form-horizontal" action="#" method="get">
		                  <div class="box-body">
		                  	<div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Invoice Id</label>
		                      <label for="inputEmail3" class="col-sm-6 control-label align-left" style="text-align:left;">: ${userorderDetails.invoiceId }</label>
		                    </div>
		                 	<div class="form-group">
		                 		<label for="inputEmail3" class="col-sm-2 control-label">Invoice Date</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label align-left" style="text-align:left;">: ${userorderDetails.createdTs}</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label ">SubTotal</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label align-left" style="text-align:left;">: ${userorderDetails.subTotal }</label>
		                    </div>
		                    <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Discount:</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.discount } </label>
		                     <label for="inputEmail3" class="col-sm-2 control-label">Discount Value</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.discountValue } </label>
		                    </div>
		                    <div class="form-group">
		                     <label for="inputEmail3" class="col-sm-2 control-label">Vat: </label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.vat }</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label">Vat Value</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.vatValue } </label>
		                    </div>
		                    <div class="form-group">
		                    	<label for="inputEmail3" class="col-sm-2 control-label">Service Tax</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.serviceTax }</label>
		                        <label for="inputEmail3" class="col-sm-2 control-label">Service Tax Value</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.serviceTaxValue }</label>
		                    </div>
		                    <div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">Misc Charges</label>
		                      	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.miscCharges }</label>
		                      	<label for="inputEmail3" class="col-sm-2 control-label">TaxRef No</label>
		                    	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.taxRefNo }</label>
		                  </div>
		                  <hr/>
		                  <div class="form-group">
		                  	  <label for="inputEmail3" class="col-sm-2 control-label">Grand Total</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.grandTotal }</label>
		                  </div>
		                  <div class="form-group">
		                  	  <label for="inputEmail3" class="col-sm-2 control-label">Amount Paid</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.amountPaid }</label> 
		                  </div>
		                  <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Amount Balance</label>
		                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userorderDetails.amountBal }</label>
		                  </div>
		                  </div>
		                </form>
		              </div>
		              </div>
				</div>
		  </div> --%> 
		  
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
       <!-- Start Main Footer -->
      <jsp:include page="../includes/footer.jsp" />
      <!-- End Main Footer -->

    </div>
    <!-- ./wrapper -->

    <!-- REQUIRED JS SCRIPTS -->
	<jsp:include page="../includes/requiredbodyjs.jsp" />
    <!-- ./REQUIRED JS SCRIPTS -->

	<!-- Add Cart Items -->
  	<div id="addCartProduct" class="modal modal-primary fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Add a New Item in the Order Cart</h4>
	      </div>
	      <div class="modal-body">
	      <div class="row">
	        <div class="col-md-12">
	              <!-- general form elements -->
	              <div class="box box-primary">
	                <div class="box-header with-border">
	                  <h3 class="box-title">Cart Item Details</h3>
	                </div>
	                <!-- /.box-header -->
	                <!-- form start -->
	                <form:form role="form" action="orderdetails" method="post" modelAttribute="addCartItem">
	                  <div class="box-body" style="color:#333;">
	                  		<div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputEmail1">Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <select class="form-control" onchange="getSubCatDetails(this.value);">
			                         <option value="Select">--Select--</option>
			                         <c:forEach var="category" items="${categoryList }">
										<option value="${category.id }">${category.name }</option>	                        
			                         </c:forEach>
			                        </select>
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Product Sub-Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <select class="form-control" id="prodSubCat" onchange="populateproductsbysubcatid(this.value)" disabled="disabled">
				                      </select>
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-12">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Product</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <%-- <form:select class="form-control" path="itemMasterDtlsId" id="product1"  disabled="true" onchange="getproductprice(this.value);" > --%>
				                      <form:select class="form-control" path="itemMasterDtlsId" id="product1"  disabled="true">
				                      </form:select>
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-6">
	                  			<div class="form-group">
				                    <label for="exampleInputPassword1">Carton Qty</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                  			<!-- <div class="col-sm-2">
		                  				<i class="fa fa-minus"></i>
		                  			</div>  -->
		                  			<div class="row">
		                  				<div class="col-md-2">
		                  					<i class="fa fa-minus-circle" onclick="decrementQty();" style="font-size: 22px;padding: 6px;color:#286090;"></i>
		                  				</div>
	  			                        <div class="col-md-6">
	  			                        	<form:input class="form-control qty" style="text-align:center;" path="cartonQty" id="cartonQty1" readonly="true" />
	  			                        </div>
	  			                        <div class="col-md-2">
	  			                        	<i class="fa fa-plus-circle"  onclick="incrementQty();" style="font-size: 22px;padding: 6px;color:#286090;"></i>
	  			                        </div>
	  			                    </div>
		                  			<!-- </div>
		                  			<div class="col-sm-2">
		                  				<i class="fa fa-plus"></i>
		                  			</div> -->
	                  			</div>
	                  		</div>
	                  		
	                  		<%-- <div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Product Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <input class="form-control masterCartonPrice" type="text" readonly="readonly" />
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Total Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <form:input class="form-control totalPrice" path="cartonPrice" value="0" id="cartonPrice1" readonly="true" />
			                    </div>
	                  		</div> --%>
	                  		
	                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<form:hidden path="orderDtlsId" value="${userorderDetails.cartDtlId }" />
                    <button type="submit" class="btn btn-primary">ADD ITEM TO CART</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>
    </div>
    </div>
  <!-- ./Add Cart Items -->
 
	<!-- Edit Cart Items -->
  	<div id="editCartProduct" class="modal modal-primary fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Edit Item in the Order Cart</h4>
	      </div>
	      <div class="modal-body">
	      <div class="row">
	        <div class="col-md-12">
	              <!-- general form elements -->
	              <div class="box box-primary">
	                <div class="box-header with-border">
	                  <h3 class="box-title">Cart Item Detail</h3>
	                </div>
	                <!-- /.box-header -->
	                <!-- form start -->
	                <form:form role="form" action="editCartItems" method="post" modelAttribute="editCartItemDetails">
	                  <div class="box-body" style="color:#333;">
	                  		<div class="col-md-12">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Product Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <input class="form-control" name="itemName" id="itemNm1" readonly="readonly" />
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-6">
	                  			<div class="form-group">
				                    <label for="exampleInputPassword1">Carton Qty</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                  			<!-- <div class="col-sm-2">
		                  				<i class="fa fa-minus"></i>
		                  			</div>  -->
		                  			<div class="row">
		                  				<div class="col-md-2">
		                  					<i class="fa fa-minus-circle" onclick="decrementQtyForEdit();" style="font-size: 22px;padding: 6px;color:#286090;"></i>
		                  				</div>
	  			                        <div class="col-md-6">
	  			                        	<form:input class="form-control editqty" style="text-align:center;" path="itemQty" id="itemQty1" readonly="true" />
	  			                        </div>
	  			                        <div class="col-md-2">
	  			                        	<i class="fa fa-plus-circle"  onclick="incrementQtyForEdit();" style="font-size: 22px;padding: 6px;color:#286090;"></i>
	  			                        </div>
	  			                    </div>
		                  			<!-- </div>
		                  			<div class="col-sm-2">
		                  				<i class="fa fa-plus"></i>
		                  			</div> -->
	                  			</div>
	                  		</div>
	                  		
	                  		<%-- <div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Product Carton Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <input class="form-control editmasterCartonPrice" name="itemCartonPrice" id="itemCartonPrice1" type="text" readonly="readonly" >
			                    </div>
	                  		</div>
	                  		
	                  		<div class="col-md-6">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Total Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
			                        <!-- select -->
				                      <form:input class="form-control edittotalPrice" path="itemPrice" id="itemPrice1" type="text" readonly="true" />
			                    </div>
	                  		</div> --%>
	                  		
	                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<form:hidden path="orderDtlsId" value="${userorderDetails.cartDtlId }" />
                  	<form:hidden path="cartItemDtlsId" id="cartItemDtlsId1" />
                    <button type="submit" class="btn btn-primary">UPDATE</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>
    </div>
    </div>
  <!-- ./Edit Cart Items -->
  
  <!-- Add Discount -->   
 <%--  	<div id="addDiscount" class="modal modal-primary fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Add discount to existing order</h4>
	      </div>
	      <div class="modal-body">
	      <div class="row">
	        <div class="col-md-12">
	              <!-- general form elements -->
	              <div class="box box-primary">
	                <div class="box-header with-border">
	                  <h3 class="box-title">Discount Detail</h3>
	                </div>
	                <!-- /.box-header -->
	                <!-- form start -->
	                <form role="form" action="addDiscount" method="post">
	                	<div class="box-body" style="color:#333;">
	                	<input type="hidden" name="orderDtlsId" value="${userorderDetails.cartDtlId }"/>
		                	<div class="col-md-12">
	                  			<div class="form-group">
			                      <label for="exampleInputPassword1">Discount Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
				                    <input class="form-control" name="discountPrice" type="text" />
			                    </div>
	                  		</div>
  			 <!-- /.box-body -->

                  <div class="box-footer">
                    <button type="submit" class="btn btn-primary">ADD DISCOUNT</button>
                  </div>
                  </div>
                </form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>
    </div>
    </div> --%>
  <!-- ./Add Discount -->
  
    <!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
    <!-- page script -->
    <script>
      $(function () {
    	$('#example1').DataTable();  
        $('#example2').DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false
        });
      });
    </script>
  </body>
</html>
