<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Search Enquiry</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<!-- Required head CSS -->
<jsp:include page="../includes/requiredheadcss.jsp" />
<!-- ./Required head CSS -->

<!-- DataTables -->
<link rel="stylesheet"
	href="plugins/datatables/dataTables.bootstrap.css">

<!-- REQUIRED JS SCRIPTS -->
<jsp:include page="../includes/requiredbodyjs.jsp" />
<!-- ./REQUIRED JS SCRIPTS -->

<!-- Page Specific JS -->
<script src="plugins/jQueryUI/jquery-ui.min.js"></script>
<!-- ./Page Specific JS -->

 <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
    
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
	$(function() {
		$("#datepicker1").datepicker();
	});
</script>

</head>
<body class="hold-transition skin-blue sidebar-mini">

	<!-- Add User Modal -->

	<!-- ./Add User Modal -->


	<c:set var="list" scope="page" value="${enqlist }" />
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
					Enquiry Details
					<!-- <small>Details of the users in DB</small> -->
				</h1>
				<c:if test="${not empty errorMssg }">
					<h4 style="color: red;">${errorMssg }</h4>
				</c:if>
				<c:if test="${not empty successMssg }">
					<h4 style="color: green;">${successMssg }</h4>
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

						<!-- Configuration Details Table -->
						<div class="box">
							<!-- /.Box-Header -->

							<p></p>


							<div class="box-header">
								<div class="row">	
									<!-- Trigger the modal with a button -->
									<form action="getenquirybydate" method="get">
										<div class="col-md-1">
											<label>Start Date</label>
										</div> 
										<div class="col-md-4">
											<input type="text" placeholder="Start Date" id="datepicker"	name="startDate" style="width:100%;">
										</div>
										<div class="col-md-1">
											<label>End Date</label>
										</div>
										<div class="col-md-4">
											<input type="text" placeholder="End Date" id="datepicker1" name="endDate" style="width:100%;">
										</div>
										<div class="col-md-2">
											<button type="submit" class="btn btn-primary btn-md">SEARCH</button>
										</div>
									</form>
								</div>
								</div>


								<p></p>
							</div>
							<!-- Send message -->

							<!-- ./send message-->



							<!-- /.Box-Header -->

							<div class="box-body">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>ID</th>
											<th>Enquiry Date</th>
											<th>User Name</th>
											<th>User Email Id</th>
											<th>User Contact Number</th>
											<th>Enquiry Type</th>
											<th>Message</th>
											<th>City</th>
											<th>State</th>
										</tr>
									</thead>
									<tbody>
										<!-- Configuration Details -->
										<%-- <user:getUserDetails userManagementService="${userManagementServiceIns }"/> --%>
										<c:if test="${not empty list }">
											<c:forEach var="listitem" items="${list}">
												<%-- <div class="checkbox">
																<label><input type="radio"
																	value="${option.answer_set_id}"
																	name="${option.question_set_id}"
																	onclick="return ans(event)" onclick="return ans(event)">${option.answer_display_text}</label>
															</div> --%>
												<tr>
													<td>${listitem.enquiryDtlsId}</td>
													<td>${listitem.createdTs }</td>
													<td>${listitem.firstNm}${listitem.lastNm}</td>
													<td>${listitem.emailId}</td>
													<td>${listitem.phnNm}</td>
													<td>${listitem.enquiryType}</td>
													<td>${listitem.message}</td>
													<td>${listitem.city}</td>
													<td>${listitem.state}</td>
												</tr>
											</c:forEach>
										</c:if>
										<!-- ./Configuration Details -->
									</tbody>
									<tfoot>
										<tr>
											<th>ID</th>
											<th>Enquiry Date</th>
											<th>User Name</th>
											<th>User Email Id</th>
											<th>User Contact Number</th>
											<th>Enquiry Type</th>
											<th>Message</th>
											<th>City</th>
											<th>State</th>

										</tr>
									</tfoot>
								</table>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					</section>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Start Main Footer -->
		<jsp:include page="../includes/footer.jsp" />
		<!-- End Main Footer -->

	<!-- ./wrapper -->

	<!-- DataTables -->
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	
	 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
    <script src="plugins/daterangepicker/daterangepicker.js"></script>
    <script src="plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- InputMask -->
    <script src="plugins/input-mask/jquery.inputmask.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script> 
    
	<!-- AdminLTE for demo purposes -->
	<script src="dist/js/demo.js"></script>
	<!-- page script -->
	<script>
		$(function() {
			$('#example1').DataTable();
			$('#example2').DataTable({
				"paging" : true,
				"lengthChange" : false,
				"searching" : false,
				"ordering" : true,
				"info" : true,
				"autoWidth" : false
			});
		});
	</script>



	<!-- Edit User Modal -->

	<!-- ./Edit User Modal -->
</body>
</html>

