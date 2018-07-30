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
    <title>Komal Trading Corporation | User Details</title>
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
    
<!--   <script>
	function sendEditUserValues(userId1, firstName1, panNo1, lastName1, displayName1, status1, 
			vatNo1, contactNo1, userType1, stAddress1, state, city, pincode, associatedDistributor,
			otherAddressDtlsId, userDistributorListId,mark,dest,tranNm, gstNo, discount,emailId) {
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
  			document.getElementById("stAddress1_1").value = document.getElementById(stAddress1).innerHTML;
  			document.getElementById("state1").value = state;
  			document.getElementById("emailId1").value = emailId;
  			getCityList1(state,city);
  			document.getElementById("pincode1").value = pincode;
  			/* document.getElementById("associatedDistributor1").value = associatedDistributor; */
  			document.getElementById("otherAddressDtlsId1").value=otherAddressDtlsId;
  			document.getElementById("userDistributorListId1").value=userDistributorListId;
  			document.getElementById("mark1").value=mark;
  			document.getElementById("destination1").value=dest;
  			document.getElementById("transportName1").value=tranNm;
  			document.getElementById("gstNo1").value=gstNo;
  			document.getElementById("discount1").value=discount;
		} catch (e) {
			alert(e);
		}
  	}
  </script>
 -->  
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
            Contact Directory
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

        
	
		              

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">
				  <strong>
	<%-- 			  <c:choose>
					<c:when test="${not empty param.searchBy and param.searchBy eq 'InactiveUsers' }">
						Inactive
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				  </c:choose>
	 --%>			  </strong>
				  
				            <div>
          <button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addUser">Add New User</button>
          </div>
				  
                </div>
                
                <!-- /.Box-Header -->
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Sr. No</th>
                        <th>Contact Number</th>
                        <th>Contact Name</th>
                        <th>Shop Name</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${contactDirectorieList }"  var="contactDtls"  varStatus="loopCount">
                    
                                          <tr>
                                          <td>${loopCount.count }</td>
                                          <td>${contactDtls.contactNumber}</td>
                                          <td>${contactDtls.contactName}</td>
                                          <td>${contactDtls.shopName}</td>
                                          <td>
											<a data-toggle="modal" data-target="#editmodal" onclick="updateContactDetails('${contactDtls.contactDtlsId}','${contactDtls.contactNumber}','${contactDtls.contactName}','${contactDtls.shopName}')" ><i class="fa fa-pencil" title="Edit"></i></a>
											<c:choose>
														<c:when test="${ contactDtls.isActive eq 0}">
															<a
																href="activate-deactivate-contact?status=1&contactDtlsId=${contactDtls.contactDtlsId}"
																onclick="return confirm('Are you sure you want to activate this contact?')">
																<i class="fa fa-check" title="Activate"></i>
															</a>
														</c:when>
														<c:otherwise>
															<a
																href="activate-deactivate-contact?status=0&contactDtlsId=${contactDtls.contactDtlsId}"
																onclick="return confirm('Are you sure you want to de-activate this contact?')">
																<i class="fa fa-ban" title="Deactivate"></i>
															</a>
														</c:otherwise>
													</c:choose>
												</td>
                                          
                                             </tr>
                    
                    
                    </c:forEach>
                      <!-- user Details -->
                    
                      <!-- ./user Details -->
                    </tbody>
                  
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
        <h4 class="modal-title">Add New Contact</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Contact Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="${pageContext.request.contextPath }/add-contact" method="post" modelAttribute="addContctDetails">
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                    <div class="form-group">
                      <label >Contact Number</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Number" path="contactNumber" id="contactNu" required="required"  onchange="checkNumberExistOrNot()"/>
                    <div id="errorMsg" style="color: red"></div>
                    </div>
                      <div class="form-group">
                      <label >Contact Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Name" path="contactName" required="required" />
                    </div>
                    
                    <div class="form-group">
                      <label >Shop Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Shop Name" path="shopName" required="required" />
                    </div>
                    
                    </div>
                    
                    
                  </div>
                  <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Add</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Add user Modal -->
<script type="text/javascript">
function checkNumberExistOrNot(){
	var contactNumber = $('#contactNu').val();
	var data = {"contactNumber" : contactNumber};
		 $.ajax({
			 url: "check-contact-number",
			type : "GET",
			 data:data,
			 success: function(result){
		    },
		    error: function(result){
				$("#errorMsg").html("Contact Number already Exists.");
		    }
		 });
	
		 
}

function checkNumberExistOrNotEdit(){
	var contactNumber = $('#contactNumber1').val();
	var data = {"contactNumber" : contactNumber};
		 $.ajax({
			 url: "check-contact-number",
			type : "GET",
			 data:data,
			 success: function(result){
		    },
		    error: function(result){
				$("#errorMsgEdit").html("Contact Number already Exists.");
		    }
		 });
	
		
}


</script>

<!-- Edit user Modal -->
<div id="editmodal" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Contact</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Contact Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="${pageContext.request.contextPath }/edit-contact" method="post" modelAttribute="addContctDetails">
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                    <div class="form-group">
                    
                     <form:input  class="form-control"  type="hidden"  id="contactDtlsId1"  path="contactDtlsId" required="required" />
                      <label >Contact Number</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Number" id="contactNumber1"  path="contactNumber" required="required"  onchange="checkNumberExistOrNotEdit()" />
                    <div id="errorMsgEdit" style="color: red"></div>
                    </div>
                      <div class="form-group">
                      <label >Contact Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Contact Name" id="contactName1" path="contactName" required="required" />
                    </div>
                    <div class="form-group">
                      <label >Shop Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Shop Name" id="shopName1" path="shopName" required="required" />
                    </div>
                    
                    </div>
                  </div>
                    <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Edit</button>
                  </div>
                </form:form>
              </div><!-- /.box -->
          </div>
       </div>
       </div>
      </div>
    </div>

  </div>
</div>
<!-- ./Edit user Modal -->

<script type="text/javascript">
function updateContactDetails(contactId, contactNum, contactName,shopName){
	$('#contactDtlsId1').val(contactId);
	$('#contactNumber1').val(contactNum);
	$('#contactName1').val(contactName);
	$('#shopName1').val(shopName);
	
}

</script>


<!-- Show Inactive Users -->
<%-- <div id="showInactiveUser" class="modal modal-primary fade" role="dialog">
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
 --%><!-- ./Show Inactive Users -->

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

