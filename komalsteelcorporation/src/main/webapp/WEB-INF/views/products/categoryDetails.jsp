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
		function sendCategoryDetailsEdit(id,name,desc/* ,url */) {
			/* document.getElementById("catUrl1").value=url; */
			document.getElementById("catDesc1").value=desc;
			document.getElementById("catName1").value=name;
			document.getElementById("catId1").value=id;
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
            Category Details
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
				  <!-- Any HTML and JSP TAG will go here -->   
				</div>
			</div>
        
          <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Category List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addCategory">Add New Category </button>
                	<!--  	<button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button> -->
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr.No</th>
                        <th>Category ID</th>
                        <th>Category Name</th>
                        <th>Category Description</th>
                        <!--  <th>Image Url</th> -->
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- configuration Details -->
                        <category:categoryDetailsList productDAO="${categoryInjectedDAO }"/>
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
                       <th>Category ID</th>
                       <th>Category Name</th>
                       <th>Category Description</th>
                       <!--  <th>Image Url</th> -->
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
<div id="addCategory" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New Category </h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Category Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="categorymaster" method="post" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                     <div class="form-group">
                      <label for="exampleInputPassword1">Category NAME</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Name" type="text" name="catName" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Category Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Name" type="text" name="catDesc" required="required" />
                    </div>
                    </div>
                    <div class="col-md-6">
                   <!--  <div class="form-group">
                      <label for="exampleInputPassword1">Image URL</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder=" Url" name="url"  type="text" required="required" />
                    </div> -->
                      <input class="form-control" placeholder=" Url" name="parentId" value="0" type="hidden" required="required" />
                      <input class="form-control" placeholder=" Url" name="isCategory"  type="hidden" value="true" required="required" />
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
<div id="editCategory" class="modal modal-primary fade" role="dialog">
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
                  <h3 class="box-title">Category  Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="editcatDetails" method="post" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                      	<input class="form-control" placeholder="Category Id" type="hidden" id="catId1" name="catId"  required="required" />
	                     <div class="form-group">
	                      <label for="exampleInputPassword1">Category Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <input class="form-control" placeholder="Category Name" type="text" id="catName1" name="catName" required="required"/>
	                    </div>
	                    <div class="form-group">
	                      <label for="exampleInputPassword1">Category Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <input class="form-control" placeholder="Category Name" type="text" id="catDesc1" name="catDesc" required="required"/>
	                    </div>
                    </div>
                    <div class="col-md-6">
                     <!-- <div class="form-group">
                      <label for="exampleInputPassword1">Image URL</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <input class="form-control" placeholder="Image Url" type="text" required="required" id="catUrl1" name="catUrl"/>
                    </div> -->
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

