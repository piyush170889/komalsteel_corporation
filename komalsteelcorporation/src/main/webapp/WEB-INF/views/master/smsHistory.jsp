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
   		<jsp:include page="../includes/requiredbodyjs.jsp" />
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
   	
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
    
  <!--  <script>
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
             Sms History 
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
				  <c:choose>
					<c:when test="${not empty param.searchBy and param.searchBy eq 'InactiveUsers' }">
						Inactive
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				  </c:choose>
				  </strong>
				  </h3>
                </div>
                
                <!-- /.Box-Header -->
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                       <!--  <th>Sr. No</th> -->
                        <th>Contact Number</th>
                        <th>Contact Name</th>
                        <th>Shop Name</th>
                        <th>Msg Content</th>                      
                        <th>Msg Send Time</th>              
                      </tr>
                    </thead>
                   <%--  <tbody>
                    
                    <c:forEach items="${smsDtlsList }"  var="smsDtls"  varStatus="loopCount">
                                          <tr>
                                          <td>${loopCount.count }</td>
                                          <td>${smsDtls.contactNumber}</td>
                                          <td>${smsDtls.contactName}</td>
                                           <td>${smsDtls.shopName}</td>
                                          <td>${smsDtls.smsContent}</td>
                                          <td>${smsDtls.createdTs}</td>
                                         
                                             </tr>
                                             </c:forEach>
                      <!-- user Details -->
                    
                      <!-- ./user Details -->
                    </tbody>
                   --%>
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
    <!-- ./REQUIRED JS SCRIPTS -->

  
    <!-- DataTables -->
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
    <!-- page script -->
    <script>
     /*  $(function () {
    	$('#example1').DataTable();  
        $('#example2').DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false
        });
      }); */
      
      
      $.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
  	{
      	console.log(oSettings);
  		return {
  			"iStart":         oSettings._iDisplayStart,
  			"iEnd":           oSettings.fnDisplayEnd(),
  			"iLength":        oSettings._iDisplayLength,
  			"iTotal":         oSettings.fnRecordsTotal(),
  			"iFilteredTotal": oSettings.fnRecordsDisplay(),
  			"iPage":          oSettings._iDisplayLength === -1 ?
  				0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
  			"iTotalPages":    oSettings._iDisplayLength === -1 ?
  				0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
  		};
  	};

  $(document).ready(function() {

  	$("#example1").dataTable( {
  		"bProcessing": true,
        "bServerSide": true,
        "bPaginate": true,
        "sPaginationType": "full_numbers",
        "bPaging": true,
        //"sort": "invoiceNo",
        //bStateSave variable you can use to save state on client cookies: set value "true" 
        "bStateSave": false,
        //Default: Page display length
        "iDisplayLength": 10,
          "fnDrawCallback": function () {
              //Get page numer on client. Please note: number start from 0 So
              //for the first page you will see 0 second page 1 third page 2...
              //Un-comment below alert to see page number
          	//alert("Current page number: "+this.fnPagingInfo().iPage);    
          },         
          "sAjaxSource": "sms-listing",
          "aoColumns": [
              { "mData": "contactNumber" },
              { "mData": "contactName" },
              { "mData": "shopName" },
              { "mData": "smsContent" },
              { "mData": "createdTs" },
           
          ]
      } );

  } );
    </script>
    



  </body>
</html>

