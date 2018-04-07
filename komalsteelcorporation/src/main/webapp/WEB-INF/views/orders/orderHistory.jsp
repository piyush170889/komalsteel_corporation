<%@page import="co.in.replete.komalindustries.utils.UDValues"%>
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
   	
   	 <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  <script type="text/javascript">
  function sendEditOrderDetails(orderId , orderStatus, orderDate, dlvryDate, city, state, dlvryAddr, alternateContact) {
		try {
		  	document.getElementById("orderId1").value = orderId;
			document.getElementById("orderStatus1").value = orderStatus;
			document.getElementById("orderDate1").value = orderDate;
			document.getElementById("dlvryDate1").value = dlvryDate;
			getCityList1(state,city);
			document.getElementById("alternateContact1").value = alternateContact; 
			document.getElementById("state1").value = state;
			/* document.getElementById("paymode1").value = paymode;
			document.getElementById("paymentStatus1").value = payStatus;
			document.getElementById("orderprice1").value = orderPrice;  */
			document.getElementById("dlvryAddr1").value=dlvryAddr;
		} catch(e) {
			alert(e);
		}
	}
 	
 	function sendEditLrNo(lrNo, cartDtlsId, lrNoDate, noOfCarton, mark, transporterName, destination, courierName, docateNo, dlvryDate) {
 		if(lrNo == "null") {
 			lrNo = "";
 		}
 		if(lrNoDate == "null") {
 			lrNoDate = "";
 		}
 		if(noOfCarton == "null") {
 			noOfCarton = "";
 		}
 		if(mark == "null") {
 			mark = "";
 		}
 		if(transporterName == "null") {
 			transporterName = "";
 		}
 		if(destination == "null") {
 			destination = "";
 		}
 		if(courierName == "null") {
 			courierName = "";
 		}
 		if(docateNo == "null") {
 			docateNo = "";
 		}
 		if(dlvryDate == "null") {
 			dlvryDate = "";
 		}
 		document.getElementById("lrNo1").value=lrNo;
 		document.getElementById("cartDtlsId2").value=cartDtlsId;
 		document.getElementById("lrdate1").value=lrNoDate;
 		document.getElementById("noofcarton1").value=noOfCarton;
 		document.getElementById("transporterNm1").value=transporterName;
 		document.getElementById("destination1").value=destination;
 		document.getElementById("mark1").value=mark;
 		document.getElementById("courierNm1").value=courierName;
 		document.getElementById("docateNo1").value=docateNo;
 		document.getElementById("delvryDate1").value=dlvryDate;
 	}
  </script>
  
   <!-- Custom JS -->
  <script src="js/locationdetails.js"></script>
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
				           	
				           	 <div class="box-body">
		              <div class="box box-info">
		                <div class="box-header with-border">
		                  <h3 class="box-title">Search Order</h3>
		                </div>

		                <form class="form-horizontal" action="${pageContext.request.contextPath }/order" method="get">
		                  <div class="box-body">
		                    <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Order Status :</label>
		                      <div class="col-sm-2">
			                      <select class="form-control" name="searchBy">
			                        <option value="All">All</option>
			                        <c:forEach var="orderStatus" items="${orderStatusList }">
			                        	<option value="${orderStatus }">${orderStatus }</option>
			                        </c:forEach>
			                      </select>
		                      </div>
		                    </div>
		                    <!-- <div class="form-group">
		                      <label for="inputPassword3" class="col-sm-2 control-label">Search By LR NO :</label>
		                      
			                    <div class="col-md-6">  
			                      <div class="form-group">
				                    <div class="input-group">
				                      <div class="input-group-addon">
				                        <i class="fa fa-calendar"></i>
				                      </div>
				                      <input class="form-control" type="text" name="lrno">
				                    </div>/.input group
				                  </div>
			                    </div>
				               </div> -->
		                     <div class="form-group">
		                      <label for="inputPassword3" class="col-sm-2 control-label">Search By Date</label>
		                      
			                    <div class="col-md-6">  
			                      <div class="form-group">
				                    <div class="input-group">
				                      <div class="input-group-addon">
				                        <i class="fa fa-calendar"></i>
				                      </div>
				                      <input class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask="" type="text" id="reservation" name="searchDateRange">
				                    </div><!-- /.input group -->
				                  </div>
			                    </div>
				               </div>
		                  </div>
		                  <div class="box-footer">
		                    <button type="submit" class="btn btn-info pull-left">Search</button>
		                  </div>
		                </form>
		              </div>
		              </div>
				</div>
			</div>
        
       
          <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Order List</h3>
                </div>
                <!-- <div class="row">
                	<div class="col-xs-12">
                		Trigger the modal with a button
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addProduct">Add New Order</button>
                	 	<button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button>
                	</div>
                </div> -->
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Order#</th>
						<th>Order Date</th>
						<th>Dlvry Address</th>
						<!-- <th>Price</th> -->
						<th>Notes</th>
                        <th>Name</th>
                        <th>Contact</th>
						<th>Order Items (Type) - Qty</th>                        
						<!-- <th>Pay Status</th>
                        <th>Invoice#</th> -->
                        <th>Order Status</th>
                        <th>LR</th>
                        <th>Actions</th>  
                      </tr>
                    </thead>
                    <tbody>
                      <!-- order Details -->
                        <order:orderDetails cartDAO="${orderInjectedDAO }" searchBy="${param.searchBy }" searchDateRange="${param.searchDateRange }" /> 
                      <!-- ./order Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Order#</th>
						<th>Order Date</th>
						<th>Dlvry Address</th>
						<!-- <th>Price</th> -->
						<th>Notes</th>
                        <th>Name</th>
                        <th>Contact</th>
						<th>Order Items - Carton Qty</th>                        
						<!-- <th>Pay Status</th>
                        <th>Invoice#</th> -->
                        <th>Order Status</th>
                        <th>LR</th>
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
    	$('#example1').DataTable({
   		  "columns": [
              null,
              null,
              { "width": "5%" },
              null,
              null,
              null,
              { "width": "30%" },
              null,              
              { "width": "10%" },
              null,/* 
              null,
              null,
              null */
            ]
          });  
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
    
<!-- Add Order Modal -->
<div id="addProduct" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a new Order</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">order Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="#" method="post" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-12">
        				<div class="form-group">
                      <label for="exampleInputPassword1">Order Id</label>
                      <input class="form-control" placeholder="Order Id" type="text"  disabled="disabled" />
                    </div>
        			</div>
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputEmail1">Order Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <select class="form-control" required="required">
	                        <option value="select">--Select--</option>
	                        <c:forEach var="orderStatus" items="${orderStatusList }">
	                        	<option value="${orderStatus }">${orderStatus }</option>
	                        </c:forEach>
	                      </select>
	                      <!-- <!-- select -->
	                      <select class="form-control" required="required" >
	                        <option value="select">--Select--</option>
	                        <option value="Category One">Booked</option>
	                        <option value="Category Two">Pending</option>
	                        <option value="Category Three">Dispatched</option>
	                        <option value="Category Three">Canceled</option>
	                        <option value="Category Three">Ordered</option>
	                        <option value="Category Three">Completed</option>
	                      </select> -->
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Address Street1</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Street 1 Address" type="text"  required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Address Street3</label>
                      <input class="form-control" placeholder="Street 3 Address" type="text" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">State</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="state" type="text"   required="required" />
                    </div>
                    <!-- <div class="form-group">
                      <label for="exampleInputEmail1">Payment Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       select
	                      <select class="form-control" >
	                        <option value="select">--Select--</option>
	                        <option value="Category Two">Pending</option>
	                        <option value="Category Three">Completed</option>
	                      </select>
                    </div> -->

                     <div class="form-group">
                      <label for="exampleInputPassword1">ItemMasterDtlId</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Item Master Detail Id" type="text"  required="required" />
                    </div>
                    
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Order Notes</label>
                      <input class="form-control" placeholder=" Order Notes" type="text" />
                    </div>
                  <div class="form-group">
                      <label for="exampleInputPassword1">Address Street2</label>
                      <input class="form-control" placeholder="Street Address 2" type="text" />
                    </div>                  
                      <div class="form-group">
                      <label for="exampleInputPassword1">City</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="City" type="text" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Country</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Country" type="text" required="required"/>
                    </div>
                   <!--  <div class="form-group">
                      <label for="exampleInputEmail1">Pay Mode</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       select
	                      <select class="form-control" >
	                        <option value="select">--Select--</option>
	                        <option value="Category Two">Online</option>
	                        <option value="Category Three">COD</option>
	                        <option value="Category Three">Cash</option>
	                      </select>
                    </div> -->
                   
                    <div class="form-group">
                      <label for="exampleInputPassword1">Item Qty</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Item Quantity" type="text" required="required" />
                    </div>
                    
                     <div class="form-group">
                      <label for="exampleInputPassword1">Total Amount</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Total Amount" type="text" required="required" />
                    </div>
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                </form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Add Product Modal -->


<!-- Edit Order Modal -->
<div id="editOrder" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Order</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
			 <form:form role="form" action="${pageContext.request.contextPath }/editOrder" method="post" modelAttribute="orderEdit">
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Order Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-12">
        				<div class="form-group">
                      <label for="exampleInputPassword1">Order Id</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Order Id" path="orderId" id="orderId1" readonly="true"/>
                    </div>
        			</div>
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputEmail1">Order Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
	                      <form:select class="form-control" path="orderStatus" id="orderStatus1" required="required">
	                        <option value="select">--Select--</option>
	                        <c:forEach var="orderStatus" items="${orderStatusList }">
	                        	<option value="${orderStatus }">${orderStatus }</option>
	                        </c:forEach>
	                      </form:select>
                    </div>
                    
                    <div class="form-group">
                      <label for="exampleInputPassword1">Order Date</label>
                      <form:input class="form-control" placeholder="Order date" path="orderDate" id="orderDate1" type="text" readonly="true"/>
                    </div>
                    
                    <%-- <div class="form-group">
                      <label for="exampleInputEmail1">Payment Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
	                      <form:select class="form-control" id="paymentStatus1" path="paymentstatus">
	                        <option value="select">--Select--</option>
	                        <c:forEach var="paymentStatus" items="${paymentStatusList }">
	                        	<option value="${paymentStatus }">${paymentStatus }</option>
	                        </c:forEach>
	                      </form:select>
                    </div> --%>

                    </div>
                    
                    <div class="col-md-6">
                    
                   <%--  <div class="form-group">
                      <label for="exampleInputPassword1">Order Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Total Amount" path="orderprice" required="required" id="orderprice1" readonly="true"/>
                    </div> --%>
                    
                    <div class="form-group">
                      <label for="exampleInputPassword1">Delivery Date</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Delivery Date" path="dlvryDate" required="required" id="dlvryDate1" readonly="true"/>
                    </div>
                    
                     <div class="form-group">
                      <label for="exampleInputPassword1">Contact</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Alternate Contact" path="alternateContact" required="required" id="alternateContact1"/>
                    </div>
                   <%--  <div class="form-group">
                      <label for="exampleInputEmail1">Pay Mode</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
	                      <form:select class="form-control" id="paymode1" path="paymode">
	                        <option value="select">--Select--</option>
	                        <c:forEach var="paymode" items="${paymodeList }">
	                        	<option value="${paymode }">${paymode }</option>
	                        </c:forEach>
	                      </form:select>
                    </div> --%>
                      <%-- <form:hidden class="form-control" id="paymode1" path="paymode" /> --%>
                    </div>
                    </div>
                  </div>
                  
                  <!-- Shipping Address -->
                   <div class="box box-primary">
                		<div class="box-header with-border">
                  			<h3 class="box-title">Shipping Details</h3>
                		</div><!-- /.box-header -->
                <!-- form start -->
                  <div class="box-body" style="color:#333;">
	        			<div class="col-md-6">
		                  	<div class="form-group">
		                      <label for="exampleInputPassword1">State</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
							  <form:select class="form-control" id="state1" path="state" onchange="getCityList1(this.value)" >
								<option value="Select">--Select--</option>
								<c:forEach var="stateObj" items="${stateList }">
									<option value="${stateObj.locationName }">${stateObj.locationName }</option>
								</c:forEach>
							  </form:select>
		                    </div>
		                                        
                  		</div>
                  		<div class="col-md-6">
                  			<div class="form-group">
		                      <label for="exampleInputPassword1">City</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      <form:select class="form-control" id="cityId1" path="city" ></form:select>
		                    </div>
                  		</div>
                  		<div class="col-md-12">
                  			<div class="form-group">
	                      		<label for="exampleInputPassword1">Address</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
						  		<form:textarea class="form-control" placeholder="Address" id="dlvryAddr1" path="dlvryAddr" />
	                    	</div>
                  		</div>
                  </div>
                  </div>
                  <!-- /.box-body -->
					
                  <div class="box-footer">
                    <input type="submit" class="btn btn-primary" value="Submit" />
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>

  </div>
<!-- ./Edit Product Modal -->

<!-- Edit LR No Modal -->
<div id=editLrNo class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit LR NO</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
			 <form role="form" action="editLrNo" method="post">
              <div class="box box-primary">

					<!-- Transportation Details -->
              		<div class="box-header with-border">
	                  <h3 class="box-title">Transportation Details</h3>
	                </div><!-- /.box-header -->
	                
	                <div class="box-body" style="color:#333;">
		                <div class="row">	
		                	<div class="col-md-12">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Transporter Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Transporter Name" 
		                      		id="transporterNm1" name="transporterNm" required="required" />
		                    	</div>
	        				</div>
	        			</div>
	        			<div class="row">
	        				<div class="col-md-6">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Destination</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Destination" 
		                      		id="destination1" name="destination" required="required" />
		                    	</div>
	        				</div>
	        				<div class="col-md-6">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Mark</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Mark" 
		                      		id="mark1" name="mark" required="required" />
		                    	</div>
	        				</div>
        				</div>
	                <!-- </div> -->
	                <!-- Transportation Details -->
	                
	                <!-- LR Details -->
	                <div class="box-header with-border">
	                  <h3 class="box-title">LR NO Details</h3>
	                </div><!-- /.box-header -->
	                <!-- form start -->
                  	<!-- <div class="box-body" style="color:#333;"> -->
                  		<div class="row">
        				<div class="col-md-12">
	        				<div class="form-group">
	                      		<label for="exampleInputPassword1">LR No</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      		<input class="form-control" placeholder="LR NO" id="lrNo1" name="lrNo" />
	                    	</div>
        				</div>
        				</div>
        				<div class="row">
        				<div class="col-md-6">
	        				<div class="form-group">
	                      		<label for="exampleInputPassword1">LR Date</label>
	                      		<input class="form-control" placeholder="LR Date" id="lrdate1" name="lrdate" readonly="readonly" />
	                    	</div>
        				</div>
        				<div class="col-md-6">
	        				<div class="form-group">
	                      		<label for="exampleInputPassword1">No. Of Carton Loaded</label>
	                      		<input class="form-control" placeholder="No. Of Carton Loaded" id="noofcarton1" name="noofcarton" />
	                    	</div>
        				</div>
        				</div>
        			<!-- LR NO Details -->
        				
        				<!-- Courier Details -->
              		<div class="box-header with-border">
	                  <h3 class="box-title">Courier Details</h3>
	                </div><!-- /.box-header -->
	                
	                <!-- <div class="box-body" style="color:#333;"> -->
		                <div class="row">	
		                	<div class="col-md-12">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Courier Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Courier Name" 
		                      		id="courierNm1" name="courierNm" required="required" />
		                    	</div>
	        				</div>
        				</div>
        				<div class="row">	
	        				<div class="col-md-6">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Docate No.</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Docate No." 
		                      		id="docateNo1" name="docateNo" required="required" />
		                    	</div>
	        				</div>
	        				<div class="col-md-6">
		        				<div class="form-group">
		                      		<label for="exampleInputPassword1">Delivery Date</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
		                      		<input class="form-control" placeholder="Delivery Date" 
		                      		id="delvryDate1" name="delvryDate" required="required" readonly="readonly" />
		                    	</div>
	        				</div>
        				</div>
	                <!-- </div> -->
	                <!-- Courier Details -->
	                
						<div class="box-footer">
							<input type="hidden" name="cartDtlsId" id="cartDtlsId2" />
	                    	<input type="submit" class="btn btn-primary" value="Submit" />
	                  	</div>
	                  	
                  	</div>
                  	
                  	
               </div>
              </form>
        </div><!-- /.box -->
        </div>
       </div>
      </div>
    </div>

  </div>
<!-- ./Edit LR No Modal -->

 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
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
</script>
  </body>
</html>

