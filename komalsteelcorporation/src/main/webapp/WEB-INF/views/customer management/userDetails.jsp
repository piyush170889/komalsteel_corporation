<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/user.tld"  prefix="user" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Trading Corporation | User Details</title>
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
    <style>
.labeltext {
    display: inline-block;
    width: 100px;
    height: 20px;
    margin: 10px;
    align:left;
}
</style>
 
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="userInjectedDAO" scope="page" value="${cartDAO}"/> 
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
            User Details
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
		                <form class="form-horizontal" action="/distributordetails" method="post">
		                  <div class="box-body">
	                    <div class="form-group">
	                      <label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
	                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${firstName}</label>
		                  <label for="inputEmail3" class="col-sm-2 control-label">Last Name</label>
		                  <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">
		                  	<c:choose>
		                  		<c:when test="${not empty lastName}">
		                  			${lastName}
		                  		</c:when>
		                  		<c:otherwise>
		                  			Not Specified
		                  		</c:otherwise>
		                  	</c:choose>
		                  </label>
                          <label for="inputEmail3" class="col-sm-2 control-label">Company Name</label>
                          <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${displayName }</label>
	                    </div>
		                <div class="form-group">
		                  <label for="inputEmail3" class="col-sm-2 control-label">User Type</label>
		                  <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${userType}</label>
		                  <label for="inputEmail3" class="col-sm-2 control-label">Contact No</label>
		                  <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${contactNo} </label>
	                      <label for="inputEmail3" class="col-sm-2 control-label">Status</label>
	                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${ status}</label>
	                    </div>
	                    <div class="form-group">
	                      <label for="inputEmail3" class="col-sm-2 control-label">Reg.Date </label>
	                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${regDate} </label>
	                      <label for="inputEmail3" class="col-sm-2 control-label">Vat.No</label>
	                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${vatNo} </label>
	                      <label for="inputEmail3" class="col-sm-2 control-label">Pan.No</label>
	                      <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">: ${panNo}</label>
	                    </div>
                        <div class="form-group">
							  <label for="inputEmail3" class="col-sm-2 control-label">Email Id</label>
							  <label for="inputEmail3" class="col-sm-6 control-label" style="text-align:left;">: ${loginId}</label>
		                        <%--<label for="inputEmail3" class="col-sm-2 control-label">Street2 Address: ${street2}</label>
		                          <label for="inputEmail3" class="col-sm-2 control-label">Street3 Address: ${street3}</label>
		                            <label for="inputEmail3" class="col-sm-2 control-label">City: ${city}</label>
		                              <label for="inputEmail3" class="col-sm-2 control-label">State: ${state} </label> --%>
		                    </div> 
		                     <%-- <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Country: ${country}</label>
		                        <label for="inputEmail3" class="col-sm-2 control-label">Postal Code: ${postalCode} </label>
		                    </div> --%>
		                  </div>
		                </form>
		              </div>
		              </div>
				</div>
			</div>
			
			
			<!-- Billing Address Details -->
				<div class="row">
	            <div class="col-xs-12">
	
				  <!-- Product Details Table -->
	              <div class="box">
	                <!-- /.Box-Header -->
	                <div class="box-header">
	                  <h3 class="box-title">Billing Address Details</h3>
	                </div>
	                <!-- /.Box-Header -->
	                
	                <div class="box-body">
						<div class="form-group">
	                       	<label for="inputEmail3" class="col-sm-2 control-label">Street Address</label>
		                    <label for="inputEmail3" class="col-sm-10 control-label" style="text-align:left;">
								<c:choose>
									<c:when test="${not empty street1}">
										: ${street1}
									</c:when>
									<c:otherwise>
										: Not Specified
									</c:otherwise>
								</c:choose>		                      
		                    </label> 
	                    </div>
	                    
	                    <div class="form-group">
	                       	<label for="inputEmail3" class="col-sm-2 control-label">State</label>
		                    <label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
								<c:choose>
									<c:when test="${not empty state}">
										: ${state}
									</c:when>
									<c:otherwise>
										: Not Specified
									</c:otherwise>
								</c:choose>		                      
		                    </label> 
		                     <label for="inputEmail3" class="col-sm-2 control-label">City</label>
			                  <label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
			                  	<c:choose>
			                  		<c:when test="${not empty city}">
			                  			: ${city}
			                  		</c:when>
			                  		<c:otherwise>
			                  			: Not Specified
			                  		</c:otherwise>
			                  	</c:choose>
			                  </label>
	                    </div>
	                </div>
	                <!-- /.box-body -->
	              </div>
	              <!-- /.box -->
	            </div><!-- /.col -->
	          </div><!-- /.row -->
			<!-- ./Billing Address Details -->
			
			<!-- Billing Details Details -->
			<div class="row">
	            <div class="col-xs-12">
	
				  <!-- Shipping Address Details -->
	              <div class="box">
	                <!-- /.Box-Header -->
	                <div class="box-header">
	                  <h3 class="box-title">Shipping Address Details</h3>
	                </div>
	                <!-- /.Box-Header -->
	                
	                <div class="box-body">
						<div class="form-group">
	                       	<label for="inputEmail3" class="col-sm-2 control-label">Mark</label>
		                    <label for="inputEmail3" class="col-sm-10 control-label" style="text-align:left;">
								<c:choose>
									<c:when test="${not empty mark}">
										: ${mark}
									</c:when>
									<c:otherwise>
										: Not Specified
									</c:otherwise>
								</c:choose>		                      
		                    </label> 
	                    </div>
	                    
	                    <div class="form-group">
	                       	<label for="inputEmail3" class="col-sm-2 control-label">Destination</label>
		                    <label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
								<c:choose>
									<c:when test="${not empty destination}">
										: ${destination}
									</c:when>
									<c:otherwise>
										: Not Specified
									</c:otherwise>
								</c:choose>		                      
		                    </label> 
		                     <label for="inputEmail3" class="col-sm-2 control-label">Transport Name</label>
			                  <label for="inputEmail3" class="col-sm-4 control-label" style="text-align:left;">
			                  	<c:choose>
			                  		<c:when test="${not empty transportName}">
			                  			: ${transportName}
			                  		</c:when>
			                  		<c:otherwise>
			                  			: Not Specified
			                  		</c:otherwise>
			                  	</c:choose>
			                  </label>
	                    </div>
	                </div>
	                <!-- /.box-body -->
	              </div>
	              <!-- /.box -->
	            </div><!-- /.col -->
	          </div><!-- /.row -->
	          <!-- ./Shipping Address Details -->
	          
			
			<!-- Associated Distributor -->
			<%-- <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Associated Distributor Details</h3>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
					<table id="example2" class="table table-bordered table-striped">
              			 <thead>
                 			<tr>
                  			 <th>Sr.No</th>
                  			 <th>Name</th>
                   			 <th>Company Name</th>
                   			 <th>Status</th>
                   		</tr>
               			</thead>
               			<tbody>
	                  <!-- user Details -->
					<user:getUserDetailsByTrackid cartDAO="${userInjectedDAO}" trackId="${distributerTrackId }" />
				<!-- ./user Details -->
	                    </tbody>
	                    <tfoot>
	                      <tr>
	                       <th>Sr.No</th>
                			 <th>Name</th>
                 			 <th>Company Name</th>
                 			 <th>Status</th>
	                      </tr>
	                    </tfoot>
	                  </table>
                </div>
                <!-- /.box-body -->
              </div>
              <!-- /.box -->
            </div><!-- /.col -->
          </div> --%><!-- /.row -->
			<!-- ./Associated Distributor -->
			
		<!-- Order History -->	
          <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Order History</h3>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr.No</th>
                        <th>Order Id</th>
                        <th>Order Date</th>
                        <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                      <!-- user Details -->
                         <user:userOrderList cartDAO="${userInjectedDAO}" trackId="${trackid}"/> 
                      <!-- ./user Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Sr.No</th>
                        <th>Order Id</th>
                        <th>Order Date</th>
                        <th>Status</th>
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

