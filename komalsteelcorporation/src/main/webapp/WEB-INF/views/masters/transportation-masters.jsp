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
    <title>Komal Industries | Transportation Details</title>
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
		function sendEditValues(name,description, transportationMasterId) {
			document.getElementById("name1").value=name;
			document.getElementById("description1").value=description;
			document.getElementById("transportationMasterId1").value=transportationMasterId;
		}
		
		function sendValueToAddFom(){
			$("#name").val("");
			$("#description").val("");  
		}
	</script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
 <%--  <c:set var="configurationInjectedDAO" scope="page" value="${adminDAO}"/> --%>
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
            Transportation Details
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
                  <h3 class="box-title">Transportation List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addTransportationDetails" onclick="sendValueToAddFom()">Add New Transportation Details</button>
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                         <th>Sr.No</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Actions</th>  
                      </tr>
                    </thead>
                    <tbody>
                      <!-- Courier Details -->
                      	<c:forEach items="${transportationMasterDtlsList }" var="transportationDetails" varStatus="loopCount" >
							 <tr>
	                        	<td>${loopCount.count }</td>
	                        	<td>${transportationDetails.name }</td>
	                        	<td>${transportationDetails.description }</td>
	                        	<td>
	                        		<a href="javascript:void(0)" data-toggle="modal" data-target="#updateTransportationDetails" 
	                        		onclick="sendEditValues('${transportationDetails.name}','${transportationDetails.description }', ${transportationDetails.transportationMasterId })" ><i class="fa fa-pencil"></i></a>
	                        	</td>
	                        </tr>                      	
                      	</c:forEach>
                      <!-- ./Courier Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Sr.No</th>
                        <th>Name</th>
                        <th>Description</th>
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
 
<!-- Edit Product Modal -->
<div id="updateTransportationDetails" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update Transportation Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Transportation Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="editTransportationDetails" method="post" >
                  <div class="box-body" style="color:#333;">
        			
        			<div class="col-md-12">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Name" type="text" name="name" id="name1" required="required" />
                    </div>
                    </div>
                    
                    <div class="col-md-12">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <textarea class="form-control" placeholder="Description" name="description" id="description1" ></textarea>
                    </div>
                    </div>
                    
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<input type="hidden" name="transportationMasterDtlsId" id="transportationMasterId1" />
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
<!-- ./Edit Product Modal -->


<!-- Add Courier Modal -->
<div id="addTransportationDetails" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Transportation Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Transportation Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="addTransportationDetails" method="post" id="addForm">
                  <div class="box-body" style="color:#333;">
        			
        			<div class="col-md-12">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Name" type="text" name="name" id="name" required="required" />
                    </div>
                    </div>
                    
                    <div class="col-md-12">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Description</label> 
                      <!-- <input class="form-control" placeholder="Description" name="description" type="text" /> -->
                      <textarea class="form-control" placeholder="Description" name="description" id="description"></textarea>
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
<!-- ./Add Courier Modal -->



  </body>
</html>

