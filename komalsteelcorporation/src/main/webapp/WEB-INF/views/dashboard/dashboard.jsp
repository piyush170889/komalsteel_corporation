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
    <title>Dashboard</title>
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
            Dashboard
            <small>An Overview of the entire system</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
	        <div class="row">
			<h4 style="margin: 0.4em 0.9em;background-color: #00c0ef !important;padding: 0.2em;color: #fff;">
				Todays Summary
			</h4>
				<div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="user" >
					  <div class="info-box">
						<span class="info-box-icon bg-aqua"><i class="fa fa-inbox"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">New User Registration</span>
						  <span class="info-box-number"><d:NewUsersCount userType="All"  dashboardDAO="${dashBoardDAO }"/></span>
						</div><!-- /.info-box-content -->
					  </div><!-- /.info-box -->
				  </a>
				</div><!-- /.col -->
				
				<div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="order" >
					  <div class="info-box">
						<span class="info-box-icon bg-aqua"><i class="fa fa-inbox"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">New Orders</span>
						  <span class="info-box-number"><d:NewOrdersCount dashboardDAO="${dashBoardDAO }"/></span>
						</div><!-- /.info-box-content -->
					  </div><!-- /.info-box -->
				  </a>
				</div><!-- /.col -->
				
				
				<%-- 
				<div class="col-md-4 col-sm-6 col-xs-12">
					<a href="user" >
					  <div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-users"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">New Dealer</span>
						  <span class="info-box-text">Registrations</span>
						  <span class="info-box-number"><d:NewUsersCount userType="<%= UDValues.USER_TYPE_DEALER.toString() %>"  dashboardDAO="${dashBoardDAO }"/></span>
						</div><!-- /.info-box-content -->
					  </div><!-- /.info-box -->
					</a>
				</div> --%>
				<!-- /.col -->
				
				
				<%-- <div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="user" >
				  	<div class="info-box">
						<span class="info-box-icon bg-yellow"><i class="fa fa-users"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">New Distributors</span>
						  <span class="info-box-text">Registrations</span>
						  <span class="info-box-number"><d:NewUsersCount userType="<%= UDValues.USER_TYPE_DISTRIBUTOR.toString() %>"  dashboardDAO="${dashBoardDAO }"/></span>
						</div><!-- /.info-box-content -->
				  	</div><!-- /.info-box -->
				  </a>
				</div> --%><!-- /.col -->
				<%-- <div class="col-md-3 col-sm-6 col-xs-12">
				  <div class="info-box">
					<span class="info-box-icon bg-red"><i class="fa fa-barcode"></i></span>
					<div class="info-box-content">
					  <span class="info-box-text">Out of Stock</span>
					  <span class="info-box-text">Products</span>
					  <span class="info-box-number"><d:OutOfStockProductsCount dashboardDAO="${dashBoardDAO }"/></span>
					</div><!-- /.info-box-content -->
				  </div><!-- /.info-box -->
				</div><!-- /.col --> --%>
			  </div><!-- /.row -->
			  
			  <%-- <div class="row">
			  <h4 style="margin: 0.4em 0.9em;background-color: #00c0ef !important;padding: 0.2em;color: #fff;">
				Todays Orders Summary
			  </h4>
			  	
				<div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="order">
					  <div class="info-box">
						<span class="info-box-icon bg-aqua"><i class="fa fa-inbox"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">Orders Dispatched</span>
						  <span class="info-box-number"><d:OrdersCount dashboardDAO="${dashBoardDAO }" orderStatus="<%= UDValues.CART_STATUS_DISPATCHED.toString() %>"/></span>
						</div><!-- /.info-box-content -->
					  </div><!-- /.info-box -->
				  </a>
				</div><!-- /.col -->
				<div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="order">
				  	<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-inbox"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">Orders Booked</span>
						  <span class="info-box-number"><d:OrdersCount dashboardDAO="${dashBoardDAO }" orderStatus="<%= UDValues.CART_STATUS_BOOKED.toString() %>"/></span>
						</div><!-- /.info-box-content -->
					  </div><!-- /.info-box -->
					</a>
				</div><!-- /.col -->
				<div class="col-md-4 col-sm-6 col-xs-12">
				  <a href="order">
				  	<div class="info-box">
						<span class="info-box-icon bg-yellow"><i class="fa fa-inbox"></i></span>
						<div class="info-box-content">
						  <span class="info-box-text">Orders Completed</span>
						  <span class="info-box-number"><d:OrdersCount dashboardDAO="${dashBoardDAO }" orderStatus="<%= UDValues.CART_STATUS_COMPLETED.toString() %>"/></span>
						</div><!-- /.info-box-content -->
				  	</div><!-- /.info-box -->
				  </a>
				</div><!-- /.col -->
				<div class="col-md-3 col-sm-6 col-xs-12">
				  <div class="info-box">
					<span class="info-box-icon bg-red"><i class="fa fa-inbox"></i></span>
					<div class="info-box-content">
					  <span class="info-box-text">Orders Pending</span>
					  <span class="info-box-text">Approval</span>
					  <span class="info-box-number"><d:OrdersCount dashboardDAO="${dashBoardDAO }" orderStatus="<%= UDValues.CART_STATUS_PENDING.toString() %>"/></span>
					</div><!-- /.info-box-content -->
				  </div><!-- /.info-box -->
				</div><!-- /.col -->
			  </div> --%>
			  <!-- /.row -->
			  
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
    
  </body>
</html>
