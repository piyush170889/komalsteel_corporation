<%@page import="co.in.replete.komalindustries.utils.UDValues"%>
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
    <title>Komal Trading Corporation | Product Refill Details</title>
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
            Product Refill Details
          </h1>
          <c:if test="${not empty errorMssg }" >
	        <h4 style="color:red;">${errorMssg }</h4>
	      </c:if>
	      <c:if test="${not empty successMssg }">
			<h4 style="color:green;">${successMssg }</h4>
		  </c:if>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li><a href="#">Inventory Management</a></li>
            <li class="active"><a href="/wproductrefillhistory">Stock History</a></li>
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
		                  <h3 class="box-title">Search Refill Details</h3>
		                </div>

		                <form class="form-horizontal" action="${pageContext.request.contextPath }/wproductrefillhistory" method="get">
		                  <div class="box-body">
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
                  <h3 class="box-title">Refill Details List</h3>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Product No.</th>
						<th>Image</th>
						<th>Name</th>
						<th>Status</th>
                        <th>Category</th>
                        <th>Sub-Category</th>
						<th>Refill Date</th>                        
                        <th>Refill Qty</th>
                        <th>Refill Price</th>
                        <th>Per Unit Cost</th>  
                        <th>MRP</th>
                        <th>Comments</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- order Details -->
                        <product:productInventoryRefillDetails productDAO="${productInjectedDAO }" searchDateRange="${param.searchDateRange }"  />
                      <!-- ./order Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Product No.</th>
						<th>Image</th>
						<th>Name</th>
						<th>Status</th>
                        <th>Category</th>
                        <th>Sub-Category</th>
						<th>Refill Date</th>                        
                        <th>Refill Qty</th>
                        <th>Refill Price</th>
                        <th>Per Unit Cost</th>  
                        <th>MRP</th>
                        <th>Comments</th>                      
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
        $('#reservation').daterangepicker();
</script>
  </body>
</html>

