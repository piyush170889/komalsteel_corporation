<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/ProductDetails.tld" prefix="category" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Industries | Category Details</title>
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
   function sendEditPackagingInfo(packageInfoId, packageInfo, packageInfoDesc) {
		document.getElementById("packagingInfoId1").value=packageInfoId;
		document.getElementById("packagingInfoName1").value=packageInfo;
		document.getElementById("packagingDesc1").value=packageInfoDesc;
	}
   </script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="categoryInjectedDAO" scope="page" value="${productDAO}"/>
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
            Packaging Info Details
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
                  <h3 class="box-title">Packaging Info List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addPackagingInfo">Add New Packaging Info </button>
                	<!--  	<button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button> -->
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr.No</th>
                        <th>Packaging Info ID</th>
                        <th>Packaging Info Name</th>
                        <th>Packaging Info Description</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- configuration Details -->
                        <category:packagingInfoDetailsList productDAO="${categoryInjectedDAO }"/>
               <!--     <tr>
                  <td>1</td>
                  <td>service tax</td>
                  <td>10</td>
                  <td><a data-toggle="modal" data-target="#editProduct"><i class="fa fa-pencil"></i></a>
                  <a onclick="return confirm('Are you sure?')"><i class="fa fa-trash"></i></a></td>
                  </tr>-->
                      <!-- ./configuration Details --> 
                    </tbody>
                    <tfoot>
                      <tr>
                       <th>Sr.No</th>
                        <th>Packaging Info ID</th>
                        <th>Packaging Info Name</th>
                        <th>Packaging Info Description</th>
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
 
<!-- Add Category Master Modal -->
<div id="addPackagingInfo" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New Packaging Info </h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Packaging Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="${pageContext.request.contextPath }/addPackagingInfo" method="post">
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Packaging Info Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Name" type="text" name="packagingInfoName" id="name" required="required" />
                    </div>
                    </div>
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Packaging Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Description" id="url"  type="text" name="packagingDesc" required="required" />
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
<!-- ./Add category Modal -->


<!-- Edit Category Master Modal -->
<div id="editPackagingInfo" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Category </h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Packaging Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="${pageContext.request.contextPath }/editPackagingInfo" method="post" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Packaging Info ID</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Packaging Info Id" type="text" id="packagingInfoId1" name="packagingInfoId" required="required" readonly="readonly"/>
                    </div>
                     <div class="form-group">
                      <label for="exampleInputPassword1">Packaging Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Packaging Name" type="text" id="packagingInfoName1" name="packagingInfoName" required="required"/>
                    </div>
                    </div>
                    <div class="col-md-6">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Packaging Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Packaging Description" type="text" id="packagingDesc1" name="packagingDesc" required="required"/>
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
<!-- ./Edit category Modal -->
  </body>
</html>