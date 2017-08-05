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
    <title>Komal Industries | HSN Details</title>
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
     	function sendEditHSNValues(hsnDtlsId, hsnNo, iGst, sGst , cGst) {
    	 	document.getElementById("hsnDtlsId1").value=hsnDtlsId;
    	 	document.getElementById("hsnNo1").value=hsnNo;
			document.getElementById("iGst1").value=iGst;
			document.getElementById("cGst1").value=cGst;
			document.getElementById("sGst1").value=sGst;
		}
	</script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
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
            HSN Details
          </h1>
          
          <!-- Success Or Error Message -->
	          <c:if test="${not empty param.errorMssg }" >
		        <h4 style="color:red;">${param.errorMssg }</h4>
		      </c:if>
		      <c:if test="${not empty param.successMssg }">
				<h4 style="color:green;">${param.successMssg }</h4>
			  </c:if>
		  <!-- Success Or Error Message -->
		  
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
                  <h3 class="box-title">HSN List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addHsn">Add New HSN</button>
                	<!--  	<button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button> -->
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr.No</th>
                        <th>HSN NO</th>
                        <th>IGST (%)</th>
                        <th>CGST (%)</th>
                        <th>SGST (%)</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- HSN Details -->
                        <c:forEach var="hsnDetails" items="${hsnDetailsList }" varStatus="loopCount">
                        	<tr>
                        		<td>${loopCount.count }</td>
                        		<td>${hsnDetails.hsnNo }</td>
                        		<td>${hsnDetails.iGst }</td>
                        		<td>${hsnDetails.cGst }</td>
                        		<td>${hsnDetails.sGst }</td>
                        		<td>
                        			<c:choose>
                        				<c:when test="${hsnDetails.isActive eq 'Y' }">
                        					Active
                        				</c:when>
                        				<c:otherwise>
                        					Inactive
                        				</c:otherwise>
                        			</c:choose>
                        		</td>
                        		<td>
                        			<a data-toggle="modal" data-target="#hsnEdit" onclick="sendEditHSNValues('${hsnDetails.hsnDtlsId }','${hsnDetails.hsnNo }',
                        			'${hsnDetails.iGst }','${hsnDetails.sGst }','${hsnDetails.cGst }')">
                        				<i class="fa fa-pencil"></i>
                        			</a>
                        			<c:choose>
                        				<c:when test="${hsnDetails.isActive eq 'Y' }">
                        					<a href="activateinactivatehsn?hsnId=${hsnDetails.hsnDtlsId }&status=N" onclick="return confirm('Are You Sure?')" >
                        						<i class="fa fa-ban"></i>
                        					</a>
                        				</c:when>
                        				<c:otherwise>
                        					<a href="activateinactivatehsn?hsnId=${hsnDetails.hsnDtlsId }&status=Y" onclick="return confirm('Are You Sure?')" >
                        						<i class="fa fa-check"></i>
                        					</a>
                        				</c:otherwise>
                        			</c:choose>
                        			
                        		</td>
                        	</tr>
                        </c:forEach>
                      <!-- ./HSN Details --> 
                    </tbody>
                    <tfoot>
                      <tr>
                       	<th>Sr.No</th>
                        <th>HSN NO</th>
                        <th>IGST (%)</th>
                        <th>CGST (%)</th>
                        <th>SGST (%)</th>
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
 
<!-- Add HSN Master Modal -->
<div id="addHsn" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New HSN</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">HSN Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="hsnDetails" method="post" modelAttribute="hsnAdd" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
	                     <div class="form-group">
	                      <label for="exampleInputPassword1">HSN No</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="HSN No" path="hsnNo" required="required" />
	                    </div>
						<div class="form-group">
	                      <label for="exampleInputPassword1">CGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="CGST (%)" path="cGst" required="required" />
	                    </div>	                    
                    </div>
                    <div class="col-md-6">
                   		<div class="form-group">
	                      <label for="exampleInputPassword1">IGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="IGST (%)" path="iGst" required="required" />
	                    </div>
	                    <div class="form-group">
	                      <label for="exampleInputPassword1">SGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="SGST (%)" path="sGst" required="required" />
	                    </div>
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Add HSN Modal -->


<!-- Edit HSN Master Modal -->
<div id="hsnEdit" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit HSN </h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">HSN  Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="hsnDetailsEdit" method="post" modelAttribute="hsnEdit">
                  <div class="box-body" style="color:#333;">
					  <div class="col-md-6">
	                     <div class="form-group">
	                      <label for="exampleInputPassword1">HSN No</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="HSN No" path="hsnNo" required="required" id="hsnNo1" />
	                    </div>
	                    <div class="form-group">
	                      <label for="exampleInputPassword1">IGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="IGST (%)" path="iGst" required="required" id="iGst1" />
	                    </div>
                    </div>
                    <div class="col-md-6">
                   		<div class="form-group">
	                      <label for="exampleInputPassword1">CGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="CGST (%)" path="cGst" required="required" id="cGst1" />
	                    </div>
	                    <div class="form-group">
	                      <label for="exampleInputPassword1">SGST (%)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" placeholder="SGST (%)" path="sGst" required="required" id="sGst1" />
	                    </div>
                    </div>      			
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<form:hidden path="hsnDtlsId" id="hsnDtlsId1" />
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Edit HSN Modal -->
  </body>
</html>

