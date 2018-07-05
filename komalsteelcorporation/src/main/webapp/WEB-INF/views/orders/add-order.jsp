<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
   	
   	 <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    
  <!-- Custom JS -->
  <script src="js/locationdetails.js"></script>
  <!-- ./Custom JS -->
  
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <%-- <c:set var="orderInjectedDAO" scope="page" value="${cartDAO}"/> --%>
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
            Create New Order
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
        	<!--  Form Starts -->
        	<form action="add-order" method="post" id="placeOrderForm" onsubmit="destoyTable()" >
            <div class="row">
            	<div class="col-xs-12">
            	
				  <!-- Any HTML and JSP TAG will go here -->   
				    <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">User Details</h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
		                  <div class="box-body">
		                 	<div class="row">
		                 		<div class="col-md-2">
		                 			<label for="inputEmail3" class="control-label">Customer Name</label>
		                 		</div>
		                 		<div class="col-md-4">
		                 			<label for="inputEmail3" class="align-left control-label" style="text-align:left;">: ${userDetails.firstName } ${userDetails.lastName }</label>
		                 		</div>
		                 		<div class="col-md-2">
									<label for="inputEmail3" class="control-label">Email ID</label>
		                 		</div>
		                 		<div class="col-md-4">
									<label for="inputEmail3" class="control-label" style="text-align:left;">: ${userDetails.emailId }</label>		                 		
		                 		</div>
		                    </div>
		                    <div class="row">
		                 		<div class="col-md-2">
		                 			<label for="inputEmail3" class="control-label">Company Name</label>
		                 		</div>
		                 		<div class="col-md-4">
		                 			<label for="inputEmail3" class="align-left control-label" style="text-align:left;">: ${userDetails.displayName }</label>
		                 		</div>
		                 		<div class="col-md-2">
									<label for="inputEmail3" class="control-label">GST No.</label>
		                 		</div>
		                 		<div class="col-md-4">
									<label for="inputEmail3" class="control-label" style="text-align:left;">: ${userDetails.gstNo }</label>		                 		
		                 		</div>
		                    </div>
		                  </div>
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
			                  <h3 class="box-title">Shipping Details 
			                  	<a href="javascript:void(0)" data-toggle="modal" data-target="#updateAddress" onclick="clearUpdateAddressFields()" >
			                  		<i class="fa fa-pencil"></i>
			                  	</a>
			                  </h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
	                <div class="box-body">
	                <div class="form-group">
	                	 	<label for="inputEmail3" class="col-sm-2 control-label">Address</label>
		                    <label for="inputEmail3" class="col-sm-10 control-label" id="addressVal" style="text-align:left;">: ${userDetails.street1 }, ${userDetails.city } - ${userDetails.postalCode }, ${userDetails.state }</label>
		                    <input type="hidden" name="submitStAddressVal" id="submitStAddressVal1" />
		                    <input type="hidden" name="submitCityVal" id="submitCityVal1" />
		                    <input type="hidden" name="submitPincoeVal" id="submitPincoeVal1" />
		                    <input type="hidden" name="submitStateVal" id="submitStateVal1" />	
	                    </div>
	                    <div class="form-group">
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Mark</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" id="markVal" style="text-align:left;">: ${userDetails.mark }</label>
	                      	<input type="hidden" name="submitMarkVal" id="submitMarkVal1" />
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Destination</label>
		                    <label for="inputEmail3" class="col-sm-4  align-left control-label" id="destVal" style="text-align:left;">: ${userDetails.destination }</label>
		                    <input type="hidden" name="submitDestVal" id="submitDestVal1" />
	                    </div>
	                    <div class="form-group">
	                      	<label for="inputEmail3" class="col-sm-2 control-label">Transportation</label>
	                      	<label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;" id="transVal" >
	                      		: ${userDetails.tranNm }
			                </label>
			                <input type="hidden" name="submitTransVal" id="submitTransVal1" />
	                    </div>
	                    
	                    <!-- Address Hidden Fields -->
	                    <input type="hidden" name="addressId" id="addressId1" value="${userDetails.addressId }" />
	                    <!-- ./Address Hidden Fields -->
	                </div>
	             </div>
	           </div>
	       </div>  
	       </div> 
	       <!-- ./Delivery Details -->
	       
	       <!-- Cart Notes and Other Details -->
		  <div class="row">
	            <div class="col-xs-12">
				  <div class="box">
				    	<!-- /.Box-Header -->
			                <div class="box-header">
			                  <h3 class="box-title">Cart Details</h3>
			                </div>
		                <!-- /.Box-Header -->
		              <div class="box box-info">
	                <div class="box-body">
	                	<div class="row">
	                		<div class="col-sm-2">
	                	 		<label for="inputEmail3" class="control-label">Cart Notes</label>
	                	 	</div>
		                    <div class="col-sm-4">
		                    	<textarea id="cartNotes1" name="cartNotes" cols=40 ></textarea>
		                    </div>
	                      	<div class="col-sm-2">
	                	 		<label for="inputEmail3" class="control-label">Alternate Contact</label>
	                	 	</div>
	                	 	<div class="col-sm-4">
		                    	<input type="text" id="alternateContact1" value="${userDetails.contactNo }" name="alternateContact" />
		                    </div>
	                    </div>
	                </div>
	             </div>
	           </div>
	       </div>  
	       </div> 
		  <!-- ./Cart Notes and Other Details -->
		  
	       
	       <!-- Order Items -->
            <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Order Items</h3>
                </div>
                
                 <div class="box-body">
                  	<div class="row">
                  		<div class="col-md-6">
                  			<table id="example1" class="table table-bordered table-striped">
			                    <thead>
			                      <tr>
			                        <th>#</th>
									<th>Type</th>
									<th>Item Name</th>
									<th>Quantity</th>
									<th>Actions</th>  
			                      </tr>
			                    </thead>
			                    <tbody>
			                    	<c:forEach var="products" items="${productsList }" varStatus="loopCount" >
			                    		<c:set value="${products.itemMasterDtlsId }" var="item_id"></c:set>
										<tr id="productRowToRemove_${item_id }">
				                      		<td>
				                      			<span id="item_srno_${item_id }" >${loopCount.count }</span>
				                      		</td>
				                      		<td><span id="item_uom_${item_id }">${products.uom }</span></td>
				                      		<td><span id="item_name_${item_id }">${products.itemNm }</span></td>
				                      		<td>
				                      			<c:set var="range" value="${fn:split(products.masterCartonQtyRange, '-')}" />
				                      			<select class="form-control" id="item_qty_${item_id }" >
													<c:forEach var="count" begin="${range[0] }" end="${range[1] }" step="${products.masterCartonQtyIncVal }" >
					                      				<option value="${count }">${count }</option>
					                      			</c:forEach>
				                      			</select>
				                      			<input type="hidden" id="item_range_begin_${item_id }" value="${range[0] }" />
				                      			<input type="hidden" id="item_range_end_${item_id }" value="${range[1] }" />
				                      			<input type="hidden" id="item_incval_${item_id }" value="${products.masterCartonQtyIncVal }" />
				                      		</td>
				                      		<td>
				                      			<a href="javascript:void(0)" 
				                      			onclick="if (confirm('Are you sure you want to add this material to the order?')) {addMaterialToCart('${item_id }')}" >
				                      				<i class="fa fa-plus"></i>
				                      			</a>
				                      		</td>
				                      	</tr>			                    	
			                    	</c:forEach>
			                    </tbody>
			                    <tfoot>
			                      <tr>
			                        <th>#</th>
									<th>Type</th>
									<th>Item Name</th>
									<th>Quantity</th>
									<th>Actions</th>                      
			                      </tr>
			                    </tfoot>
                  			</table>
                  		</div>
                  		<div class="col-md-6">
                  			<table id="example2" class="table table-bordered table-striped">
			                    <thead>
			                      <tr>
			                        <th>#</th>
									<th>Type</th>
									<th>Item Name</th>
									<th>Quantity</th>
									<th>Actions</th>  
			                      </tr>
			                    </thead>
			                    <tbody>
			                      	
			                    </tbody>
			                    <tfoot>
			                      <tr>
			                        <th>#</th>
									<th>Type</th>
									<th>Item Name</th>
									<th>Quantity</th>
									<th>Actions</th>                      
			                      </tr>
			                    </tfoot>
                  			</table>
                  			
                  			<!-- Place Order Button -->
							  <div class="row">
							  	<div class="col-md-4 col-md-offset-4">
							  		<input type="hidden" name="userId" value="${userId }" />
							  		<button id="placeOrderBtn" class="form-control btn-primary" ><strong>Place Order</strong></button>
							  	</div>
							  </div>
							  <!-- ./Place Order Button -->
							  
                  		</div>
                  	</div>
                </div>
                <!-- /.box-body -->
              </div>
              <!-- /.box -->
            </div><!-- /.col -->  
          </div><!-- /.row -->
		  <!-- Order Items -->
		  
		  </form>
		  <!--  Form Ends -->
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

  
    <!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
    <!-- page script -->
    <script>
      $(function () {
    	$('#example1').DataTable();  
    	
        $('#example2').DataTable();
      });
    </script>


 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
    <script src="https://cdn.datatables.net/plug-ins/1.10.16/dataRender/ellipsis.js"></script>
    <script src="plugins/daterangepicker/daterangepicker.js"></script>
    <script src="plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- InputMask -->
    <script src="plugins/input-mask/jquery.inputmask.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script> 
<script>       
 //Date range picker 
        $('#dlvryDate1').datepicker();
        $('#lrdate1').datepicker();
        $('#delvryDate1').datepicker();
        $('#reservation').daterangepicker();
        
        function showData(dataId) {
        	$('#dataDisplayDiv').html('');
        	var data = $('#ellipsis_'+dataId).html();
        	$('#dataDisplayDiv').html(data);
        	$('#dataDisplayModel').modal("toggle");
        }
        
        function randomString() {
        	return Math.floor((1 + Math.random()) * 0x10000)
        	    .toString(16)
        	    .substring(1);
        }
        
        function addMaterialToCart(itemId) {
        	
        	try {
        		var itemQty = $('#item_qty_'+itemId).val();
            	
            	if (itemQty == 0) {
            		alert('Please select a quantity greater than zero to add the material');
            	} else{
            		var srNoCell = '<span id="order_item_srno_'+itemId+'">'+$('#item_srno_'+itemId).html()+'</span>' + 
            		'<input type="hidden" name="itemMasterDtls[]" class="item" value='+itemId+' />';
            		var itemUom = '<span id="order_item_uom_'+itemId+'">'+$('#item_uom_'+itemId).html()+'</span>';
            		var itemName = '<span id="order_item_name_'+itemId+'">'+$('#item_name_'+itemId).html()+'</span>';
    				var itemQtyCell = '<span id="order_item_qty_'+itemId+'">'+itemQty+'</span>'+
    				'<input type="hidden" id="order_item_range_begin_'+itemId+'" value="'+$('#item_range_begin_'+itemId).val()+'" />'+
    				'<input type="hidden" id="order_item_range_end_'+itemId+'" value="'+$('#item_range_end_'+itemId).val()+'" />'+
    				'<input type="hidden" id="order_item_incval_'+itemId+'" value="'+$('#item_incval_'+itemId).val()+'" />'+
    				'<input type="hidden" name="itemQtyDtls[]" class="item-qty" value='+itemQty+' />';
            		var actionsCell = '<a href="javascript:void(0)" onclick="if (confirm(&quot;Are you sure you want to remove this material from order?&quot;)) {removeOrderedItems('+itemId+')}" ><i class="fa fa-times"></i></a>';
            		
    				var orderListTable = $('#example2').DataTable();
    				orderListTable.row.add( [
        				srNoCell,
        				itemUom,
        				itemName,
        				itemQtyCell,
        				actionsCell
        	        ] ).node().id = 'orderRowToRemove_'+itemId;
    				orderListTable.draw( false );
        			
        			var productListTable = $('#example1').DataTable();
        			productListTable.row($("#productRowToRemove_"+itemId)).remove().draw(false);
            	}        		
        	} catch (e) {
        		console.log(e);
        		alert(e);
        	}
        }
        
        function removeOrderedItems(itemId) {
        	
        	try {
        		var srNoCell = '<span id="item_srno_'+itemId+'">'+$('#order_item_srno_'+itemId).html()+'</span>';
        		var itemUom = '<span id="item_uom_'+itemId+'">'+$('#order_item_uom_'+itemId).html()+'</span>';
        		var itemName = '<span id="item_name_'+itemId+'">'+$('#order_item_name_'+itemId).html()+'</span>';
        		
        		var rangeStart = $('#order_item_range_begin_'+itemId).val();
        		var rangeEnd = $('#order_item_range_end_'+itemId).val();
        		var itemIncVal = $('#order_item_incval_'+itemId).val();
        		
        		var itemQtyHidden = '<input type="hidden" id="item_range_begin_'+itemId+'" value="'+rangeStart+'" />'+
				'<input type="hidden" id="item_range_end_'+itemId+'" value="'+rangeEnd+'" />'+
				'<input type="hidden" id="item_incval_'+itemId+'" value="'+itemIncVal+'" />';
        		var itemQty = '<select class="form-control" id="item_qty_'+itemId+'" >';
        		
        		while(rangeStart<=rangeEnd) { 
        			itemQty = itemQty + "<option value="+rangeStart+">"+rangeStart+"</option>";
        			rangeStart = parseInt(rangeStart)+ parseInt(itemIncVal);
        		}
        		
        		itemQty = itemQty + "</select>" + itemQtyHidden;

    			var actionsCell = '<a href="javascript:void(0)" onclick="if (confirm(&quot;Are you sure you want to add this material to the order?&quot;)) {addMaterialToCart(&quot;'+itemId+'&quot;)}" ><i class="fa fa-plus"></i></a>';
        		
    			var productListTable = $('#example1').DataTable();
    			productListTable.row.add( [
    				srNoCell,
    				itemUom,
    				itemName,
    				itemQty,
    				actionsCell
    	        ] ).node().id = 'productRowToRemove_'+itemId;
    			productListTable.draw( false );
    			
    			var orderListTable = $('#example2').DataTable();
    			orderListTable.row($("#orderRowToRemove_"+itemId)).remove().draw(false);	
        	} catch (e) {
        		console.log(e);
        		alert(e);
        	}
        }
        
        function destoyTable() {
        	var orderListTable = $('#example2').DataTable();
        	orderListTable.destroy();
        	return true;
        }
        
        function useThisAddress() {
        	
        	if ($('#stAddressCaptr').val() && $('#pincodeCaptr').val() && $('#markCaptr').val() && $('#destCaptr').val() && $('#transCaptr').val() ) {
        		if ( $('#cityId').val() != 'undefined' && $('#cityId').val() != 'Select' && $('#cityId').val() && $('#stateCaptr').val() ) {
        			var stAddr = $('#stAddressCaptr').val();
        			var cityId = $('#cityId').val();
        			var pincode = $('#pincodeCaptr').val();
        			var mark = $('#markCaptr').val();
        			var dest = $('#destCaptr').val();
        			var trans = $('#transCaptr').val();
        			var state = $('#stateCaptr').val();
        			
        			$('#submitStAddressVal1').val(stAddr);
        			$('#submitCityVal1').val(cityId);
        			$('#submitPincoeVal1').val(pincode);
        			$('#submitStateVal1').val(state);
        			$('#submitMarkVal1').val(mark);
        			$('#submitDestVal1').val(dest);
        			$('#submitTransVal1').val(trans);
        			
        			$('#addressVal').html(stAddr + ', ' + cityId + ' - ' + pincode + ', ' + state + ', India');
                	$('#markVal').html(mark);
                	$('#destVal').html(dest);
                	$('#transVal').html(trans);
                	$('#addressId1').val("");
                	
                	$('#updateAddress').modal('toggle');        			
        		} else {
        			$('#errorMssgModal').html('Please select appropriate State and City to proceed');
        		}
        	} else {
        		$('#errorMssgModal').html('Please fill all details to proceed');
        	}
        }
        
        function clearUpdateAddressFields() {
        	$('#stAddressCaptr').val("");
        	$('#cityId').html("");
        	$('#pincodeCaptr').val("");
        	$('#stateCaptr').val("select");
        	$('#markCaptr').val("");
        	$('#destCaptr').val("");
        	$('#transCaptr').val("");
        }
        
        $('#placeOrderForm').on('submit', function() {
			if ($('.item').length > 0) {
				return true;
			} else {
				alert ('Please select atleast 1 material for the order');
				return false;
			}
        });
</script>

<!-- Add Address Details -->
<div id="updateAddress" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Address Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Shipping Address Details</h3>
                </div>
                <!-- /.box-header -->
                   <!-- Shipping Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Shipping Address</h3>
					<div id="errorMssgModal" style="font-size:14px; color:red; text-align:center;"></div>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">State</label>
							<!-- select -->
							<select class="form-control" id="stateCaptr" name="state" onchange="getCityList(this.value)" required="required" >
							<option value="select">--Select--</option>
							<c:forEach var="stateObj" items="${stateList }">
								<option value="${stateObj.locationName }">${stateObj.locationName }</option>
							</c:forEach>
							</select>
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">Street Address</label>
						  <textarea class="form-control" placeholder="Street Address" id="stAddressCaptr" name="staddress1" required="required"  ></textarea>
						</div>
                    </div>
                    
                    <div class="col-md-6">
						<div class="form-group">
						 	<label for="exampleInputPassword1">City</label>
							<!-- select -->
							<select class="form-control" name="city" id="cityId" required="required" ></select>                  
						</div>				
						<div class="form-group">
						  <label for="exampleInputPassword1">Pincode</label>
						  <input class="form-control" placeholder="Pincode" id="pincodeCaptr" type="text" name="pincode" required="required"  />
						</div>
                    </div>
                  </div>  
                  
                  <!-- Billing Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Billing Address</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">Mark</label>
							<!-- select -->
							<input class="form-control" placeholder="Mark" id="markCaptr" type="text" name="mark" required="required"  />
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">Destination</label>
						  <input class="form-control" placeholder="Destination" id="destCaptr" type="text" name="destination" required="required"  />
						</div>
                    </div>
                    
                    <div class="col-md-6">
						<div class="form-group">
						 	<label for="exampleInputPassword1">Transport Name</label>
						  <input class="form-control" placeholder="Transport Name" id="transCaptr" type="text" name="transportName" required="required"  />                  
						</div>				
                    </div>
                  </div>  
                  <!-- ./Billing Address -->
                  
                  <div class="box-footer">
                    <button class="btn btn-primary" onclick="useThisAddress()" >Use This Address</button>
                  </div>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Add Address Details -->

  </body>
</html>