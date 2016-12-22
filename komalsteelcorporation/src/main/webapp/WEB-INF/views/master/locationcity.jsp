<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/configuration.tld" prefix="configuration" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Industries | Configuration Details</title>
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
    <script type="text/javascript">
		function sendEditLocationValues(locationId,locName, locDesc, locParentId, locStatus) {
			document.getElementById("locationId1").value=locationId;
			document.getElementById("locName1").value=locName;
			document.getElementById("locDesc1").value=locDesc;
			document.getElementById("locParentId1").value=locParentId;
			document.getElementById("locStatus1").value=locStatus;
		}
	</script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="configurationInjectedDAO" scope="page" value="${adminDAO}"/>
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
            Location Details
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
				</div>
			</div>
        
          <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Cities List Under State : <strong>${statename }</strong></h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addState">Add New State</button>
                		<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addCity">Add New City</button>
                	<!--  	<button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button> -->
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr.No</th>
                        <th>City Name</th>
                        <th>City Desc</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- Location Details -->
                        <c:forEach var="city" items="${cityList }">
                        	<tr>
                        		<td>${city.locationId }</td>
                        		<td>${city.locationName }</td>
                        		<td>${city.locationDesc }</td>
                        		<td>${city.isActive }</td>
                        		<td>
                        			<a data-toggle="modal" data-target="#editCity" onclick="sendEditLocationValues('${city.locationId }','${city.locationName }'
                        			, '${city.locationDesc }', '${city.locationParentId }', '${city.isActive }')">
                        				<i class="fa fa-pencil" ></i>
                        			</a>
                        		</td>
                        	</tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Sr.No</th>
                        <th>City Name</th>
                        <th>City Desc</th>
                        <th>Status</th>
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
 
<!-- Add State Modal -->
<div id="addState" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New State</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">State Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="locationdetailsadd" method="post" >
                  <div class="box-body" style="color:#333;">
        			
        			<div class="col-md-6">
                   
                     <div class="form-group">
                      <label for="exampleInputPassword1">State Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="State Name" type="text" name="locationName" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">States Desc</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="States Desc" name="locationDesc"  type="text" required="required" />
                    </div>
                    
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <select class="form-control" name="isActive"  required="required" >
                      	<option value="Active">Active</option>
                      	<option value="Inactive">Inactive</option>
                      </select>
                    </div>
                 
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<input type="hidden" name="locationParentId" value="0" />
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
<!-- ./Add State Modal -->

<!-- Add City Modal -->
<div id="addCity" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New City</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">City Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="locationdetailsadd" method="post" >
                  <div class="box-body" style="color:#333;">
        			
        			<div class="col-md-6">
                   
                     <div class="form-group">
                      <label for="exampleInputPassword1">City Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="State Name" type="text" name="locationName" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">City Desc</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="States Desc" name="locationDesc"  type="text" required="required" />
                    </div>
                    
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">State</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <select class="form-control" name="locationParentId" required="required" >
                      	<option value="99999">Select State</option>
                      	<c:forEach var="state" items="${statesList }">
                      		<option value="${state.locationId }">${state.locationName }</option>
                      	</c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <select class="form-control" name="isActive"  required="required" >
                      	<option value="Active">Active</option>
                      	<option value="Inactive">Inactive</option>
                      </select>
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
<!-- ./Add City Modal -->

<!-- Edit City Modal -->
<div id="editCity" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit City Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">City Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="locationdetailsedit" method="post" >
                  <div class="box-body" style="color:#333;">
        			
        			<div class="col-md-6">
                   
                     <div class="form-group">
                      <label for="exampleInputPassword1">City Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="City Name" type="text" name="locationName" required="required" id="locName1" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">City Desc</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="City Desc" name="locationDesc"  type="text" required="required" id="locDesc1"/>
                    </div>
                    
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">State</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <select class="form-control" name="locationParentId" required="required" id="locParentId1">
                      	<option value="${stateid }">${statename }</option>
                      	<c:forEach var="state" items="${statesList }">
                      		<c:if test="${state.locationId ne stateid }">
                      			<option value="${state.locationId }">${state.locationName }</option>
                      		</c:if>
                      	</c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <select class="form-control" name="isActive"  required="required" id="locStatus1">
                      	<option value="Active">Active</option>
                      	<option value="Inactive">Inactive</option>
                      </select>
                    </div>
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<input type="hidden" name="locationId" id="locationId1"/>
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
<!-- ./Edit City Modal -->

  </body>
</html>