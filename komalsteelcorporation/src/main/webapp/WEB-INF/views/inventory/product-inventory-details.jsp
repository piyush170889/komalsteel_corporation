<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/ProductDetails.tld" prefix="product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Trading Corporation | Product Details</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
   
   	<!-- Required head CSS -->
	<jsp:include page="../includes/requiredheadcss.jsp" />
	<!-- ./Required head CSS -->   	
   	
   	 <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- Edit Product Script -->
<script type="text/javascript">
	function sendProductInventoryDetails(itemMasterDtlsId, invProdNm, invProdBookedQty, invProdAvl, invProdMrp) {
		document.getElementById("itemMasterDtlsId1").value=itemMasterDtlsId;
		document.getElementById("invProdNm1").value=invProdNm;
		document.getElementById("invProdBookedQty1").value=invProdBookedQty;
		document.getElementById("invProdAvl1").value=invProdAvl;
		document.getElementById("invProdMrp1").value=invProdMrp;
	}
</script>
<!-- ./Edit Product Script -->
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="productInjectedDAO" scope="page" value="${productDAO}"/>
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
            Product Stock Details
            <small>Product stock</small>
          </h1>
          <c:if test="${not empty param.errorMssg }" >
	        <h4 style="color:red;">${param.errorMssg }</h4>
	      </c:if>
	      <c:if test="${not empty param.successMssg }">
			<h4 style="color:green;">${param.successMssg }</h4>
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

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Products List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<a href="wproductinventorydetails?displaymode=1" class="btn btn-danger btn-md pull-right">
                			Show Out Of Stock Product
                		</a>
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Product No.</th>
                        <th>Status</th>
                        <th>Image</th>
                        <th>Product name</th>
                        <th>Category</th>
                        <th>Sub-category</th>
                        <th>Type</th>
                        <th>Avl Qty</th>
                        <th>Booked Qty</th>
                        <th>Threshold Val</th>
                        <th>MRP</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- Product Details -->
                      <product:productInventoryDetailsList productDAO="${productInjectedDAO }" doShowOnlyOutOfStockProducts="${display_mode }"/>
                      <!-- ./Product Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Product No.</th>
                        <th>Status</th>
                        <th>Image</th>
                        <th>Product name</th>
                        <th>Category</th>
                        <th>Sub-category</th>
                        <th>Type</th>
                        <th>Avl Qty</th>
                        <th>Booked Qty</th>
                        <th>Threshold Val</th>
                        <th>MRP</th>
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
    
    <c:if test="${not empty num }">
    	<c:set var="pageNum" value="${num }" />
    </c:if>
    <c:if test="${empty num }">
    	<c:set var="pageNum" value="0" />
    </c:if>
    <h1>${pageNum }</h1>
    <script>
      $(function () {
    	$('#example1').DataTable( {
    		  'displayStart': ${pageNum }
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
    
<!-- Update Inventory Details -->
<div id="editInventoryDetails" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update Product Stock Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Product Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="productInventoryDetails" method="post" modelAttribute="editInventory">
                  <div class="box-body" style="color:#333;">	
                  	<div class="col-md-12">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Name</label>
	                      <form:input class="form-control" path="itemName" id="invProdNm1" placeholder="Product Name" required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Availability</label>
	                      <input class="form-control" name="invProdAvl" id="invProdAvl1" placeholder="Product Availability" type="text" 
	                      required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Booked Quantity</label>
	                      <input class="form-control" name="invProdBookedQty" id="invProdBookedQty1" placeholder="Product Booked Quantity" type="text" required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Refill Quantity</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdRefillQty" id="invProdRefillQty1" placeholder="Product Refill Quantity" required="required"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Refill Date</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdRefillDt" id="invProdRefillDt1" placeholder="Product Refill Date" required="required" readonly="true"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product MRP</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdMrp" id="invProdMrp1" placeholder="Product MRP" required="required" />
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Comments</label>
	                      <form:textarea class="form-control" path="invRefillComments" placeholder="Comments"/>
	                    </div>	
                  	</div>
                  	
                  </div>
                  
                  <!-- Box Footer -->
                  <div class="box-footer">
                  	<form:input type="hidden" path="itemMasterDtlsId" id="itemMasterDtlsId1" />
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                  <!-- Box Footer -->                  
                  
                </form:form>
              </div>
        </div>
     </div>
    </div>
    </div>
    
   </div>
</div>
<!-- ./Update Inventory Details -->


 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
    <script src="plugins/daterangepicker/daterangepicker.js"></script>
    <script src="plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- InputMask -->
    <script src="plugins/input-mask/jquery.inputmask.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script> 
<script>
$('#invProdRefillDt1').datepicker();
</script>
  </body>
</html>
