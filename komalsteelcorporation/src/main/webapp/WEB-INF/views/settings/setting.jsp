<%@page import="co.in.replete.komalindustries.utils.UDValues"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/dashboard.tld" prefix="d" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
  <head>
	<meta http-equiv="refresh" content="300"/> 
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Settings</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
	<!-- Required head CSS -->
	<jsp:include page="../includes/requiredheadcss.jsp" />
	
	<!-- ./Required head CSS -->

 <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <!--
  BODY TAG OPTIONS:
  =================
  Apply one or more of the following classes to get the
  desired effect
  |---------------------------------------------------------|
  | SKINS         | skin-blue                               |
  |               | skin-black                              |
  |               | skin-purple                             |
  |               | skin-yellow                             |
  |               | skin-red                                |
  |               | skin-green                              |
  |---------------------------------------------------------|
  |LAYOUT OPTIONS | fixed                                   |
  |               | layout-boxed                            |
  |               | layout-top-nav                          |
  |               | sidebar-collapse                        |
  |               | sidebar-mini                            |
  |---------------------------------------------------------|
  -->
  <body class="hold-transition skin-blue sidebar-mini">
  	<c:set var="dashBoardDAO" scope="page" value="${dashboardDAO}"/>
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
            Setting
            <small></small>
          </h1>
           <c:if test="${not empty errorMssg }" >
	        <h4 style="color:red;">${errorMssg }</h4>
	      </c:if>
	      <c:if test="${not empty successMssg }">
			<h4 style="color:green;">${successMssg }</h4>
		  </c:if>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Settings</li>
          </ol>
          
         
        </section>

        <!-- Main content -->
        <section class="content">
	        <div class="row">
			<h4 style="margin: 0.4em 0.9em;color:#3c8dbc; padding: 0.2em;">
				Change Setting Password <a data-toggle="modal" data-target="#editpasswordmodal"
															style="margin: 0 5px;"> <i class="fa fa-pencil"
																title="Edit"></i></a>
			</h4>
			
				<%-- <div class=" col-xs-12">
								<div class="col-sm-6">
								 <div class="row">
									<div class="col-sm-4"><label>Password </label></div>
									<div class="col-sm-1"><label>: </label></div>
									<div class="col-sm-7 form-group">
									<input type="password" name="password" id="password1" value="${ matchPassword}" disabled="disabled"/>
								<a data-toggle="modal" data-target="#editpasswordmodal"
															style="margin: 0 5px;"> <i class="fa fa-pencil"
																title="Edit"></i></a>
									</div>
								</div> 
								
								</div> 
							 </div> --%>
				</div>
				
				<div class="row">
			<h4 style="margin: 0.4em 0.9em;background-color: #00c0ef !important;padding: 0.2em;color: #fff;">
				Admin Email Id's
			</h4>
				<div class=" col-xs-12">
								<div class="col-sm-6">
								 <div class="row">
									<div class="col-sm-2"><label>Email Id :</label></div>
									<div class="col-sm-9 form-group">
									<input type="text" style="width: 410px; " name="emailIds" id="emailid" value="${ adminEmailList}" data-role="tagsinput" disabled="disabled"/>
									</div>
									<div class="col-sm-1">	<a data-toggle="modal" data-target="#editemailmodal"
															style="margin: 0 5px;"> <i class="fa fa-pencil" onclick="updateEmailId()"
																title="Edit"></i></a></div>
									</div>
								</div> 
								
								</div> 
							 </div>
				</div>
				
				
			  
			</section><!-- /.content -->
      </div><!-- /.content-wrapper -->

      <!-- Start Main Footer -->
      <jsp:include page="../includes/footer.jsp" />
      <!-- End Main Footer -->

    </div>
    <!-- ./wrapper -->
    
    
    
    <div id="editpasswordmodal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Update Password</h4>
				</div>
				
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<!-- general form elements "-->
							<form action="update-password" method="post" id="changePasswordform" >
								<div class="box box-primary">
									<div class="box-header with-border">
										<!-- <h3 class="box-title">Add New Details</h3> -->
									</div>
									<!-- /.box-header -->
									<!-- form start -->
							
									<div class="box-body" style="color: #333;">
										
										<div class="row">
											<div class="col-md-12">
										<div class="row">
											<div class="col-md-4">
												<label>Old Password :<span class="col-red">*</span></label>
											</div>
											<div class="col-md-8">
												<div class="form-group">
													<input type="password" class="form-control"  placeholder="Old Password" name="oldPassword" required="required" id="oldPassword1" onkeyup="checkConfirnAndNewPassword()" />
												<div id="oldPassDiv" style="color:red"></div>
												</div>
												
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<label>New Password :<span class="col-red">*</span></label>
											</div>
											<div class="col-md-8">
												<div class="form-group">
													<input type="password" class="form-control"  placeholder="New Password" name="newPassword" required="required" id="newPassword1" onkeyup="checkConfirnAndNewPassword()"/>
												</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<label>Confirm Password :<span class="col-red">*</span></label>
											</div>
											<div class="col-md-8">
												<div class="form-group">
													<input type="password" class="form-control"  placeholder="Confirm Password" name="confirmPassword" required="required" id="confirmPassword1" onchange="checkConfirnAndNewPassword()" />
												<div id="cnfrmPass1" style="color:green"></div>
												<div id="cnfrmPass" style="color:red"></div>
												</div>
											</div><div id="cnfrmPass" style="color:red"></div>
										</div>
										
										<div class="box-footer">
										        <button type="submit" id="emailSubmit1" disabled="disabled" class="btn btn-primary" data-toggle="tooltip" data-placement="bottom">Update</button>
										<!-- 	<button type="submit" disabled="disabled" id="emailSubmit" class="btn btn-primary">Update</button> -->
										</div>
										</div>
										</div>
									</div>	
								</div>
							</form>
						</div>
						<!-- /.box -->
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<div id="editemailmodal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Update Admin Email Id</h4>
				</div>
				
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<!-- general form elements "-->
							<form action="update-email" method="post" >
								<div class="box box-primary">
									<div class="box-header with-border">
										<!-- <h3 class="box-title">Add New Details</h3> -->
									</div>
									<!-- /.box-header -->
									<!-- form start -->
							
									<div class="box-body" style="color: #333;">
										
										<div class="row">
											<div class="col-md-12">
										<div class="row">
											<div class="col-md-4">
												<label>Email Id :<span class="col-red">*</span></label>
											</div>
											<div class="col-md-8">
												<div class="form-group">
													<input type="text" class="form-control"  value="${ adminEmailList}" data-role="tagsinput"  name="emailId" required="required" id="emailIdss1"  />
												</div>
												
											</div>
										</div>
										
										
										
										
										<div class="box-footer">
											<button type="submit" class="btn btn-primary">Update</button>
										</div>
										</div>
										</div>
									</div>	
								</div>
							</form>
						</div>
						<!-- /.box -->
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<script type="text/javascript">
	
	
 

function checkConfirnAndNewPassword(){
	
	var newPassword1 = $('#newPassword1').val();
	var confirmPassword1 = $('#confirmPassword1').val();
	var oP = $('#oldPassword1').val();
	
	 if (newPassword1=='' || newPassword1==null || confirmPassword1=='' || confirmPassword1==null || oP=='' || oP==null ) {
		 $('#emailSubmit1').attr('disabled', 'disabled');
     } else {
    	 $('#emailSubmit1').attr('disabled', false);
     }   
	if(confirmPassword1=='' || confirmPassword1==null){
		
	}else{
		if(newPassword1 != confirmPassword1){
			 $("#cnfrmPass").html("Password Not Matched");
		}else{
			 $("#cnfrmPass1").html("Password Matched");
		}	
	}
	
	
}

function updateEmailId(){
	var email = $('#emailid').val();
	$('#emailIds1').val(email);
}

function checkOldPassword(){
	var oldPassword1 = $('#oldPassword1').val();
	var userDtlsId = $('#udi').val();
	var data = {"oldPassword1":oldPassword1, "userDtlsId":userDtlsId};
	
	$.ajax({
	       type : "POST",
	       url : "check-oldPassword",
	       data : data,
	       success : function(response) {
	    	   if(response != "Matched"){
	    		   $("#oldPassDiv").html("Old Password is Incorrect.");
			           /* $("#oldPassDiv").html("Password Matched"); */
	    	   }
	       },
	       error : function(xhr, status, error) {
	       }
	   })
	
}

function UpdateEmailId(){
	var emailId = $('#emailIdss1').val();
	alert(emailId);
	var data = {"emailId":emailId};
	
	$.ajax({
	       type : "POST",
	       url : "update-email",
	       data : data,
	       success : function(response) {
	    	   if(response != "Matched"){
	    		   $("#oldPassDiv").html("Old Password is Incorrect.");
			           /* $("#oldPassDiv").html("Password Matched"); */
	    	   }
	       },
	       error : function(xhr, status, error) {
	       }
	   })
	
}



</script>

    <!-- REQUIRED JS SCRIPTS -->
	<jsp:include page="../includes/requiredbodyjs.jsp" />
    <!-- ./REQUIRED JS SCRIPTS -->
    
  </body>
</html>
