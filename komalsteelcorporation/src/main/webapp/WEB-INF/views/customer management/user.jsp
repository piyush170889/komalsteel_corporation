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
    <title>Komal Industries | User Details</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
   
   	<!-- Required head CSS -->
	<jsp:include page="../includes/requiredheadcss.jsp" />
	<!-- ./Required head CSS -->   	
   	
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  <script>
	function sendEditUserValues(userId1, firstName1, panNo1, lastName1, displayName1, status1, 
			vatNo1, contactNo1, userType1, stAddress1, state, city, pincode, associatedDistributor,
			otherAddressDtlsId, userDistributorListId,mark,dest,tranNm) {
		try{
			document.getElementById("userId1").value=userId1;
  			document.getElementById("firstName1").value = firstName1;
  			document.getElementById("panNo1").value = panNo1;
  			document.getElementById("lastName1").value = lastName1;
  			document.getElementById("displayName1").value = displayName1;
  			document.getElementById("status1").value = status1;
  			document.getElementById("vatNo1").value = vatNo1;
  			document.getElementById("contactNo1").value = contactNo1;
  			document.getElementById("userType1").value = userType1;
  			document.getElementById("stAddress1_1").value = stAddress1;
  			document.getElementById("state1").value = state;
  			getCityList1(state,city);
  			document.getElementById("pincode1").value = pincode;
  			/* document.getElementById("associatedDistributor1").value = associatedDistributor; */
  			document.getElementById("otherAddressDtlsId1").value=otherAddressDtlsId;
  			document.getElementById("userDistributorListId1").value=userDistributorListId;
  			document.getElementById("mark1").value=mark;
  			document.getElementById("destination1").value=dest;
  			document.getElementById("transportName1").value=tranNm;
		} catch (e) {
			alert(e);
		}
  	}
  </script>
  
  <!-- Custom JS -->
  <script src="js/locationdetails.js"></script>
  <!-- ./Custom JS -->
  
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="userInjectedDAO" scope="page" value="${userDAO}"/>
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
          
          <!-- Success Or Error Message -->
          <jsp:include page="../includes/SuccessOrErrorMessage.jsp" />
          <!-- ./Success Or Error Message -->
          
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
		                  <h3 class="box-title">Search User</h3>
		                </div>
		                <form class="form-horizontal" action="${pageContext.request.contextPath }/user" method="get">
		                  <div class="box-body">
		                    <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Search by</label>
		                      <div class="col-sm-2">
			                      <select class="form-control" name="searchBy">
			                        <option value="Select">Select</option>
			                        <option value="name">Name</option>
			                        <option value="companyName">Company Name</option>
			                        <option value="panno">PAN.No</option>
			                        <option value="vatno">VAT.No</option>
			                        <option value="cntcNo">Contact.No</option>
			                        <option value="emailId">EmailId</option>
			                      </select>
		                      </div>
		                    </div>
		                    <div class="form-group">
		                      <label for="inputPassword3" class="col-sm-2 control-label">Search Value</label>
		                      <div class="col-sm-5">
		                        <input type="text" class="form-control" id="inputPassword3" placeholder="Search Value" name="searchValue">
		                      </div>
		                    </div>
		                  <!-- <div class="form-group">
		                      <label for="inputPassword3" class="col-sm-2 control-label">Search By Date</label>
		                      <div class="col-sm-2">
		                      Registration From
		                       <input type="date" class="form-control" id="inputPassword3" placeholder="Search Value" name="orderfrom">
		                      </div>
		                        <div class="col-sm-2">
		                        Registration To
			                     <input type="date" class="form-control" id="inputPassword3" placeholder="Search Value" name="orderto">
		                      </div>
		                    </div> -->
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
                  <h3 class="box-title">User List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addUser">Add New User</button>
						<button type="button" class="btn btn-danger btn-md" data-toggle="modal" data-target="#showInactiveUser">Show Inactive Users</button>
                	  	<!-- <button type="button" class="btn btn-danger btn-md">Show InActive User</button> --> 
                	</div>
                </div>
                <!-- /.Box-Header -->
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>#</th>
                        <th>User Name</th>
                        <th>Display Name</th>
                        <th>Status</th>
                         <th>PAN.No</th> 
                        <th>VAT.No</th>
                        <th>Contact.No</th>
                        <th>EmailId</th>
                        <th>User Type</th>
                        <th>Reg.Date</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- user Details -->
                        <user:userDetails userDAO="${userInjectedDAO}" searchBy="${param.searchBy }" searchValue="${param.searchValue }" /> 
                      <!-- ./user Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                         <th>#</th>
                        <th>User Name</th>
                        <th>Display Name</th>
                        <th>Status</th>
                         <th>PAN.No</th> 
                        <th>VAT.No</th>
                        <th>Contact.No</th>
                        <th>EmailId</th>
                        <th>User Type</th>
                        <th>Reg.Date</th>
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
    
<!-- Add user Modal -->
<div id="addUser" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a New User</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">User Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="${pageContext.request.contextPath }/userAdd" method="post" modelAttribute="userAdd">
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">First Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="First Name" path="firstName" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">EmailId </label><!-- <i class="fa fa-asterisk" style="color:red;font-size:9px;"></i> -->
                      <form:input class="form-control" placeholder="Email Id" path="emailId"/>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Company Name</label>
                      <form:input class="form-control" placeholder="Display Name" path="displayName"/>
                    </div>
                    <%--  <div class="form-group">
                      <label for="exampleInputPassword1">State</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="State" path="state" required="required" />
                    </div> --%>
                    <div class="form-group">
                      <label for="exampleInputPassword1">PAN.No</label>
                      <form:input class="form-control" placeholder="Pan No" path="panNo" />
                    </div>
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Last Name</label>
                      <form:input class="form-control" placeholder=" Last Name" path="lastName" />
                    </div>
                    
                    <div class="form-group">
                      <label for="exampleInputEmail1">Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
	                      <form:select class="form-control" path="status">
	                        <option value="Select">--Select--</option>
	                        <option value="Active">Active</option>
	                        <option value="Inactive">Inactive</option>
	                      </form:select>
                    </div>
                    
                     <div class="form-group">
                      <label for="exampleInputPassword1">Contact.No</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Number" path="contactNo" required="required" />
                    </div>
                    <%-- 
                    <div class="form-group">
                      <label for="exampleInputPassword1">City</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="City" path="city" required="required" />
                    </div>
                    
                    <div class="form-group">
                      <label for="exampleInputPassword1">Postal Code</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Postal Code" path="zipcode" required="required" />
                    </div>
                     --%><div class="form-group">
                      <label for="exampleInputPassword1">VAT.No</label><!-- <i class="fa fa-asterisk" style="color:red;font-size:9px;"></i> -->
                      <form:input class="form-control" placeholder="VAT.No" path="vatNo" />
                    </div>
                     <div class="form-group">
                      <label for="exampleInputPassword1">User Type</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                         <!-- select -->
	                      <form:select class="form-control" path="userType">
	                        <option value="Select">--Select--</option>
	                        <option value="Distributor">Distributor</option>
	                        <option value="Dealer">Dealer</option>
	                      </form:select>                  
	                        </div>
                    </div>
                  </div>
                  
                   <!-- Shipping Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Shipping Address</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">State</label>
							<!-- select -->
							<form:select class="form-control" path="state" onchange="getCityList(this.value)">
							<option value="select">--Select--</option>
							<c:forEach var="stateObj" items="${stateList }">
								<option value="${stateObj.locationName }">${stateObj.locationName }</option>
							</c:forEach>
							</form:select>
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">Street Address</label>
						  <form:textarea class="form-control" placeholder=" Last Name" path="staddress1" />
						</div>
                    </div>
                    
                    <div class="col-md-6">
						<div class="form-group">
						 	<label for="exampleInputPassword1">City</label>
							<!-- select -->
							<form:select class="form-control" path="city" id="cityId" />                  
						</div>				
						<div class="form-group">
						  <label for="exampleInputPassword1">Pincode</label>
						  <form:input class="form-control" placeholder="Pincode" path="pincode" />
						</div>
                    </div>
                  </div>  
                  
                  <!-- Billing Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Billing Address</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">Mark</label>
							<!-- select -->
							<form:input class="form-control" placeholder="Mark" path="mark" />
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">Destination</label>
						  <form:input class="form-control" placeholder="Destination" path="destination" />
						</div>
                    </div>
                    
                    <div class="col-md-6">
						<div class="form-group">
						 	<label for="exampleInputPassword1">Transport Name</label>
						  <form:input class="form-control" placeholder="Transport Name" path="transportName" />                  
						</div>				
                    </div>
                  </div>  
                  <!-- ./Billing Address -->
                  
                  <!-- Associated Distributors -->
                  <%-- <div class="box-header with-border">
                  	<h3 class="box-title">Associated Distributors</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
						<div class="form-group">
						  	<label for="exampleInputEmail1">Distributor</label>
							<!-- select -->
							<form:select class="form-control" path="associatedDistributor">
								<option value="select">--Select--</option>
								<c:forEach var="distributorObj" items="${DistributorList }">
									<option value="${distributorObj.distributorTrackId }">${distributorObj.firstName } ${distributorObj.lastName } -- ${distributorObj.displayName }</option>
								</c:forEach>
							</form:select>
						</div>                  
                  </div> --%>
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
<!-- ./Add user Modal -->


<!-- Edit user Modal -->
<div id="editUser" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit User</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">User Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="${pageContext.request.contextPath }/editUser" method="post" modelAttribute="userEdit" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-12">
        				<div class="form-group">
                      <form:hidden path="userId" id="userId1"  readonly="readonly" />
                      <form:hidden path="otherAddressDtlsId" id="otherAddressDtlsId1"  readonly="readonly" />
                      <form:hidden path="userDistributorListId" id="userDistributorListId1"  readonly="readonly" />
                    </div>
        			</div>
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">First Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="First Name" path="firstName" id="firstName1"  required="required" />
                    </div>
                     <div class="form-group">
                      <label for="exampleInputPassword1">PAN.No</label>
                      <form:input class="form-control" placeholder="Pan No" path="panNo" id="panNo1" />
                    </div>
					<div class="form-group">
                      <label for="exampleInputPassword1">Company Name</label>
                      <form:input class="form-control" placeholder="Display Name" id="displayName1"  path="displayName"/>
                    </div>  
                    <div class="form-group">
                      <label for="exampleInputPassword1">Contact.No</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Number" path="contactNo" id="contactNo1" required="required"/>
                    </div>                
                    </div>
                    
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Last Name</label>
                      <form:input class="form-control" placeholder=" Last Name" id="lastName1" path="lastName" />
                    </div>
                     <div class="form-group">
                      <label for="exampleInputPassword1">VAT.No</label>
                      <form:input class="form-control" placeholder="VAT.No" path="vatNo" id="vatNo1" />
                    </div>
                    
                    <div class="form-group">
                      <label for="exampleInputEmail1">Status</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
	                      <form:select class="form-control" path="status" id="status1">
	                        <option value="select">--Select--</option>
	                        <option value="Active">Active</option>
	                        <option value="Inactive">Inactive</option>
	                      </form:select>
                    </div>
                   
                      <div class="form-group">
                      <label for="exampleInputPassword1">User Type</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                         <!-- select -->
	                      <form:select class="form-control" id="userType1" path="userType" >
	                        <option value="">--Select--</option>
	                        <option value="Distributor">Distributor</option>
	                        <option value="Dealer">Dealer</option>
	                      </form:select>                  
	                        </div>
                    </div>
                  </div>
                  
                  <!-- Shipping Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Shipping Address</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">State</label>
							<!-- select -->
							<form:select class="form-control" path="state" id="state1" onchange="getCityList1(this.value)">
							<option value="Select">--Select--</option>
							<c:forEach var="stateObj" items="${stateList }">
								<option value="${stateObj.locationName }">${stateObj.locationName }</option>
							</c:forEach>
							</form:select>
						</div>
                    </div>
                    
                    <div class="col-md-6">	
                    	<div class="form-group">
						 	<label for="exampleInputPassword1">City</label>
							<!-- select -->
							<form:select class="form-control" id="cityId1" path="city" />                  
						</div>	
					</div>
					<div class="col-md-6">
                    	<div class="form-group">
						  <label for="exampleInputPassword1">Street Address 1</label>
						  <form:textarea class="form-control" placeholder=" Last Name" id="stAddress1_1" path="staddress1" />
						</div>				
                    </div>
                    
                    <div class="col-md-6">
                    	<div class="form-group">
						  <label for="exampleInputPassword1">Pincode</label>
						  <form:input class="form-control" placeholder="VAT.No" path="pincode" id="pincode1" />
						</div>
					</div>
                  </div>    
                  
                  <!-- Billing Address -->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Billing Address</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
                  	<div class="col-md-6">
                  		<div class="form-group">
						  	<label for="exampleInputEmail1">Mark</label>
							<!-- select -->
							<form:input class="form-control" placeholder="Mark" path="mark" id="mark1"/>
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">Destination</label>
						  <form:input class="form-control" placeholder="Destination" path="destination" id="destination1"/>
						</div>
                    </div>
                    
                    <div class="col-md-6">
						<div class="form-group">
						 	<label for="exampleInputPassword1">Transport Name</label>
						  <form:input class="form-control" placeholder="Transport Name" path="transportName" id="transportName1"/>                  
						</div>				
                    </div>
                  </div>  
                  <!-- ./Billing Address -->
                  
                  <!-- Associated Distributors -->
                  <%-- <div class="box-header with-border">
                  	<h3 class="box-title">Associated Distributors</h3>
                  </div><!-- /.box-header -->
                  <div class="box-body" style="color:#333;">
						<div class="form-group">
						  	<label for="exampleInputEmail1">Distributor</label>
							<!-- select -->
							<form:select class="form-control" path="associatedDistributor" id="associatedDistributor1">
								<option value="Select">--Select--</option>
								<c:forEach var="distributorObj" items="${DistributorList }">
									<option value="${distributorObj.distributorTrackId }">${distributorObj.firstName } ${distributorObj.lastName } -- ${distributorObj.displayName }</option>
								</c:forEach>
							</form:select>
						</div>                  
                  </div> --%>
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
<!-- ./Edit user Modal -->

<!-- Show Inactive Users -->
<div id="showInactiveUser" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Inactive Users</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Date Range Selection</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="user" method="get">
                  <div class="box-body" style="color:#333;">
	                    <div class="form-group">
	                      <label for="inputPassword3" class="col-sm-12 control-label">Please Select A Date Range : </label>
		                    <div class="col-md-12">  
		                      <div class="form-group">
			                    <div class="input-group">
			                      <div class="input-group-addon">
			                        <i class="fa fa-calendar"></i>
			                      </div>
			                      <input class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask="" type="text" id="reservation" name="searchValue">
			                      <input type="hidden" name="searchBy" value="InactiveUsers"/>
			                    </div><!-- /.input group -->
			                  </div>
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
<!-- ./Show Inactive Users -->

 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
    <script src="plugins/daterangepicker/daterangepicker.js"></script>
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

