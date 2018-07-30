<%@page import="co.in.replete.komalindustries.utils.UDValues"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/Orders.tld" prefix="order"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Komal Industries | Order Details</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://jqueryui.com/resources/demos/style.css">

<script>

 $(function () {
    var availableTags = ${transportationName};
    var availableTagsContact = ${appendedContactDtlsList};
    
    
    $( "#tags").autocomplete ({
      source: availableTags,
      appendTo : $('#editLrNo')
    });
    
    $( "#contacttags").autocomplete ({
	      source: availableTagsContact,
	    });
  }); 
 

 function showView(viewName) {
	 if (viewName == 'Select' || viewName == 'NEW_ORDER') {
		 disableAll();
	 } else {
		 disableAll();
		 enable(viewName);
	 }
 }
 
 function disableAll() {
	 $('#LR_SMS').css("display", "none");
	 $('#COURIER_SMS').css("display", "none");
 }
 
 function enable(viewName) {
	 $('#'+viewName).css("display", "block");
 }
  </script>


<!-- Required head CSS -->
<jsp:include page="../includes/requiredheadcss.jsp" />
<!-- ./Required head CSS -->

<!-- daterange picker -->
<link rel="stylesheet"
	href="plugins/daterangepicker/daterangepicker-bs3.css">
<link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
<!-- DataTables -->
<link rel="stylesheet"
	href="plugins/datatables/dataTables.bootstrap.css">


<!-- Custom JS -->
<script src="js/locationdetails.js"></script>
<!-- ./Custom JS -->

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

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
				<h1>Unregistered Customer Messaging</h1>
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
						<!-- Any HTML and JSP TAG will go here -->

						<div class="box-body">
							<div class="box box-info">
								<div class="box-header with-border">
									<h3 class="box-title">Send SMS</h3>
								</div>

								<form class="form-horizontal"
									action="${pageContext.request.contextPath }/customer-messaging"
									method="POST">
									<div class="box-body">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">SMS
												Type: </label>
											<div class="col-sm-2">
												<select class="form-control" name="smsType"
													onchange="showView(this.value)">
													<option value="Select">Select</option>
													<option value="NEW_ORDER">New Order</option>
													<option value="LR_SMS">LR Details SMS</option>
													<option value="COURIER_SMS">Courier SMS</option>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="inputPassword3" class="col-sm-2 control-label">Order
												No.: </label>

											<div class="col-md-6">
												<div class="form-group">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-shopping-cart"></i>
														</div>
														<input class="form-control" type="text" name="orderNo">
													</div>
													<!-- /.input group -->
												</div>
											</div>
										</div>

										<div class="form-group">
											<label for="tags" class="col-sm-2 control-label"
												path="contactNumber">Customer Contact No.: </label>
											<div class="col-sm-2">
												<input type="text" class="form-control" id="contacttags" name="custContact" />
											</div>
										</div>


										<!-- LR NO -->
										<div id="LR_SMS" style="display: none;">
											<div class="form-group">
												<label class="col-sm-2 control-label" for="tags">Transporter
													Name<i class="fa fa-asterisk"
													style="color: red; font-size: 9px;"></i>
												</label>
												<div class="col-sm-6">
													<input class="form-control" placeholder="Transporter Name"
														id="tags" name="transporterNm" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="exampleInputPassword1">Destination<i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i></label>
												<div class="col-sm-6">
													<input class="form-control" placeholder="Destination"
														id="destination1" name="destination" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="exampleInputPassword1">Mark<i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i></label>
												<div class="col-sm-6">
													<input class="form-control" placeholder="Mark" id="mark1"
														name="mark" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="exampleInputPassword1">LR No</label><i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i>
												<div class="col-sm-6">
													<input class="form-control" placeholder="LR NO" id="lrNo1"
														name="lrNo" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="exampleInputPassword1">LR Date</label>
												<div class="col-sm-6">
													<input class="form-control" placeholder="LR Date"
														id="lrdate1" name="lrDate" readonly="readonly" />
												</div>
											</div>

											<div class="form-group">
												<label for="exampleInputPassword1"
													class="col-sm-2 control-label">No. Of Carton
													Loaded</label>
												<div class="col-sm-6">
													<input class="form-control"
														placeholder="No. Of Carton Loaded" id="noofcarton1"
														name="noOfCarton" />
												</div>
											</div>
										</div>
										<!-- ./LR No -->

										<!-- Courier Modal -->
										<div id="COURIER_SMS" style="display: none;">
											<div class="form-group">
												<label for="exampleInputPassword1"
													class="col-sm-2 control-label">Courier Name<i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i></label>
												<div class="col-md-6">
													<select class="form-control" id="courierNm1"
														name="courierNm">
														<option value="Select">Select Courier</option>
														<c:forEach items="${courierList }" var="courier">
															<option value="${courier.courierNm }">${courier.courierNm }</option>
														</c:forEach>
													</select>
												</div>
											</div>

											<div class="form-group">
												<label for="exampleInputPassword1"
													class="col-sm-2 control-label">Docate No.<i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i></label>
												<div class="col-md-6">
													<input class="form-control" placeholder="Docate No."
														id="docateNo1" name="docateNo" />
												</div>
											</div>

											<div class="form-group">
												<label for="exampleInputPassword1"
													class="col-sm-2 control-label">Delivery Date<i
													class="fa fa-asterisk" style="color: red; font-size: 9px;"></i></label>
												<div class="col-md-6">
													<input class="form-control" placeholder="Delivery Date"
														id="delvryDate1" name="delvryDate" readonly="readonly" />
												</div>
											</div>
										</div>
										<!-- Courier Modal -->


									</div>

									<div class="box-footer">
										<button type="submit" class="btn btn-info">Send SMS</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Start Main Footer -->
		<jsp:include page="../includes/footer.jsp" />
		<!-- End Main Footer -->

	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<%-- <jsp:include page="../includes/requiredbodyjs.jsp" /> --%>
	<!-- Bootstrap 3.3.5 -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="dist/js/app.min.js"></script>
	<!-- SlimScroll -->
	<script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="plugins/fastclick/fastclick.min.js"></script>
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
    	"order": [[ 0, "desc" ]],
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
            ],
            columnDefs: [ {
                targets: 2,
                /* render: $.fn.dataTable.render.ellipsis( 50, true ), */
			       /* render: function ( data, type, row ) {
				    	return data.length > 50 ?
				        data.substr( 0, 50 ) + '<a href="javascript:void(0)" onclick="showData(\'' + data + '\')"> (Read More)</a>' :
				        data;
					} */
					render: function ( data, type, row ) {
	                   	 if (data.length > 50) {
	                   	 	var randomNum = randomString();
	                   	 	$('#ellipsis').append("<div id=\"ellipsis_" + randomNum + "\">" + data + "</div>");
	                   	 	return data.substr( 0, 50 ) + '<a href="javascript:void(0)" onclick="showData(\'' + randomNum + '\')"> (Read More)</a>'
	                   	 } else {
	                       	 return data; 
	                   	 }
					 }
              },
              {
                  targets: 6,
                  /* render: $.fn.dataTable.render.ellipsis( 90, true ), */
                     render: function ( data, type, row ) {
	                   	 if (data.length > 50) {
	                   	 	var randomNum = randomString();
	                   	 	$('#ellipsis').append("<div id=\"ellipsis_" + randomNum + "\">" + data + "</div>");
	                   	 	return data.substr( 0, 50 ) + '<a href="javascript:void(0)" onclick="showData(\'' + randomNum + '\')"> (Read More)</a>'
	                   	 } else {
	                       	 return data; 
	                   	 }
					 }
                },
                {
                    targets: 8,
                    render: $.fn.dataTable.render.ellipsis( 50, true ),
                    
                  }]
          }
          );  
    	
        $('#example2').DataTable({
        	
          "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false,
          columnDefs: [ {
              targets: 0,
              render: function ( data, type, row ) {
                  return data.substr( 0, 10 );
              }
          } ]
        });
      });
    </script>

	<!-- Ellipsis Data -->
	<div id="ellipsis" style="display: none;"></div>
	<!-- ./Ellipsis Data -->


	<!-- View Modal -->
	<div id="dataDisplayModel" class="modal modal-primary fade"
		role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<!-- general form elements -->
							<div class="box box-primary">
								<div class="box-header with-border">
									<h3 class="box-title">Details</h3>
								</div>
								<!-- /.box-header -->
								<!-- form start -->
								<div class="box-body" style="color: #333;">
									<div class="col-md-12">
										<div id="dataDisplayDiv"></div>
									</div>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- ./View Modal -->


	<!-- date-range-picker -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
	<script
		src="https://cdn.datatables.net/plug-ins/1.10.16/dataRender/ellipsis.js"></script>
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
        
        function showData(dataId) {
        	$('#dataDisplayDiv').html('');
        	var data = $('#ellipsis_'+dataId).html();
        	$('#dataDisplayDiv').html(data);
        	$('#dataDisplayModel').modal("toggle");
        }
        
        function randomString() {
        	return Math.floor((1 + Math.random()) * 0x10000)
        	    .toString(16)
        	    .substring(1);
        }
        
</script>


</body>
</html>

