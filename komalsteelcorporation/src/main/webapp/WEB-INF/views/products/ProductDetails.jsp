<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/ProductDetails.tld" prefix="product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Komal Industries | Product Details</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
   
   	<!-- Required head CSS -->
	<jsp:include page="../includes/requiredheadcss.jsp" />
	<!-- ./Required head CSS -->   	
   	
   	 <!-- daterange picker -->
    <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
 	<!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- Edit Product Script -->
<script type="text/javascript">
function sendProductDetails(productId, productName, productCategory, productSubCategory, productDescription, itemsInMasterCarton, masterCartonPrice, 
		productPackaging, productPackagingInfo, masterCartonQtyRange, masterCartonQtyIncVal, productNo, hsnId, perUnitPrice, pageNum
		/* ,productAvailableQty, productThresholdVal, productMRP */) {
	document.getElementById("productId1").value=productId;
	document.getElementById("productName1").value=productName;
	document.getElementById("productCategory1").value=productCategory;
	getSubCatDetails1(productCategory,productSubCategory);
	//document.getElementById("productSubCategory1").value=productSubCategory;
	document.getElementById("productDescription1").value=productDescription;
	document.getElementById("itemsInMasterCarton1").value=itemsInMasterCarton;
	document.getElementById("masterCartonPrice1").value=masterCartonPrice;
	document.getElementById("productPackagingInfo1").value=productPackagingInfo;
	//document.getElementById("availableQty").value=productAvailableQty;
	//document.getElementById("thresholdVal1").value=productThresholdVal;
	//document.getElementById("mrp1").value=productMRP;
	document.getElementById("masterCartonQtyRange1").value=masterCartonQtyRange.split("-")[1];
	document.getElementById("masterCartonQtyIncVal1").value=masterCartonQtyIncVal;
	document.getElementById("productNo1").value=productNo;
	document.getElementById("pagenum").value=pageNum;
	$("#hsnId1").val(hsnId);
	$("#perUnitPrice1").val(perUnitPrice);
}

function getSubCatDetails(catId){
	$("#prodSubCat").html("");
	$("#prodSubCat").prop("disabled", true);
	$.ajax({
        type : 'GET',
        url : 'getProductSubcategory',
        dataType : 'json',
        data: "catId="+catId,
        success : function(data){
        	var listItems = "<option value='Select'>Select Product Sub Category</option>"; 
        	for (var i = 0; i < data.length; i++){
       	      listItems+= "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
       	    }
        	$("#prodSubCat").prop("disabled", false);
        	$("#prodSubCat").html(listItems);
        }
    });
}

function getSubCatDetails1(catId, subCatId){
	$("#productSubCategory1").html("");
	$("#productSubCategory1").prop("disabled", true);
	$.ajax({
        type : 'GET',
        url : 'getProductSubcategory',
        dataType : 'json',
        data: "catId="+catId,
        success : function(data){
        	var listItems = "<option value='Select'>Select Product Sub Category</option>"; 
        	for (var i = 0; i < data.length; i++){
       	      listItems+= "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
       	    }
        	$("#productSubCategory1").prop("disabled", false);
        	$("#productSubCategory1").html(listItems);
        	$("#productSubCategory1").val(subCatId);
        }
    });
}

function sendProductInventoryDetails(itemMasterDtlsId, invProdNm, invProdBookedQty, invProdAvl, invProdMrp) {
	document.getElementById("itemMasterDtlsId1").value=itemMasterDtlsId;
	document.getElementById("invProdNm1").value=invProdNm;
	document.getElementById("invProdBookedQty1").value=invProdBookedQty;
	document.getElementById("invProdAvl1").value=invProdAvl;
	document.getElementById("invProdMrp1").value=invProdMrp;
}
</script>
<!-- ./Edit Product Script -->
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
  <c:set var="productInjectedDAO" scope="page" value="${productDAO}"/>
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
            Product Details
            <small>Product master</small>
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
        <!-- Product Search -->
        	<!-- <div class="row">
            	<div class="col-xs-12">
                <div class="box-body">
			         Horizontal Form
		              <div class="box box-info">
		                <div class="box-header with-border">
		                  <h3 class="box-title">Search Products</h3>
		                </div>/.box-header
		                form start
		                <form class="form-horizontal" action="/productdetails" method="post">
		                  <div class="box-body">
		                    <div class="form-group">
		                      <label for="inputEmail3" class="col-sm-2 control-label">Search by</label>
		                      <div class="col-sm-2">
		                        select
			                      <select class="form-control" name="searchBy">
			                        <option>Product Name</option>
			                        <option>Product Category</option>
			                        <option>Product Sub-Category</option>
			                      </select>
		                      </div>
		                    </div>
		                    <div class="form-group">
		                      <label for="inputPassword3" class="col-sm-2 control-label">Search Value</label>
		                      <div class="col-sm-5">
		                        <input type="text" class="form-control" id="inputPassword3" placeholder="Search Value" name="searchValue">
		                      </div>
		                    </div>
		                  </div>/.box-body
		                  <div class="box-footer">
		                    <button type="submit" class="btn btn-info pull-left">Search</button>
		                  </div>/.box-footer
		                </form>
		              </div>/.box
		              </div>
				</div>
			</div> -->
                <!-- Product Search -->  
          <div class="row">
            <div class="col-xs-12">

			  <!-- Product Details Table -->
              <div class="box">
                <!-- /.Box-Header -->
                <div class="box-header">
                  <h3 class="box-title">Products List</h3>
                </div>
                <div class="row">
                	<div class="col-xs-12">
                		<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addProduct">Add New Product</button>
                		<!-- <button type="button" class="btn btn-danger btn-md">Show Out Of Stock Product</button> -->
                	</div>
                </div>
                <!-- /.Box-Header -->
                
                <div class="box-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>#</th>
                        <th>NO.</th>
                        <th>STATUS</th>
                        <th>IMAGE</th>
                        <th>NAME</th>
                        <th>HSN NO.</th>
                        <!-- <th>Category</th>
                        <th>Sub-Category</th> -->
                        <th>CATEGORY</th>
                        <th>SUBCATEGORY</th>
                        <th>QTY. IN CARTON</th>
                        <th>PER PIECE PRICE</th>
                        <th>SIZE</th>
                        <!-- <th>Avl Qty</th>
                        <th>Booked Qty</th>
                        <th>Threshold Val</th>
                        <th>MRP</th> -->
                        <th>MAX ALLOWED CARTONS IN ORDER</th>
                        <th>CARTON BATCH SIZE</th>
                        <th>ACTIONS</th>
                      </tr>
                    </thead>
                    <tbody>
                      <!-- Product Details -->
                      <product:productDetailsList productDAO="${productInjectedDAO }"/>
                      <!-- ./Product Details -->
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>#</th>
                        <th>NO.</th>
                        <th>STATUS</th>
                        <th>IMAGE</th>
                        <th>NAME</th>
                        <th>HSN NO.</th>
                        <!-- <th>Category</th>
                        <th>Sub-Category</th> -->
                        <th>CATEGORY</th>
                        <th>SUBCATEGORY</th>
                        <th>QTY. IN CARTON</th>
                        <th>PER PIECE PRICE</th>
                        <th>SIZE</th>
                        <!-- <th>Avl Qty</th>
                        <th>Booked Qty</th>
                        <th>Threshold Val</th>
                        <th>MRP</th> -->
                        <th>MAX ALLOWED CARTONS IN ORDER</th>
                        <th>CARTON BATCH SIZE</th>
                        <th>ACTIONS</th>
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
    
    <c:if test="${not empty num }">
    	<c:set var="pageNum" value="${num }" />
    </c:if>
    <c:if test="${empty num }">
    	<c:set var="pageNum" value="0" />
    </c:if>
    <h1>${pageNum }</h1>
    <script>
      $(function () {
    	$('#example1').DataTable( {
    		  'displayStart': ${pageNum }
    	});  
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
    
<!-- Update Inventory Details -->
<div id="editInventoryDetails" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update Product Inventory Details</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Product Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="productInventoryDetails" method="post" modelAttribute="editInventory">
                  <div class="box-body" style="color:#333;">	
                  	<div class="col-md-12">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Name</label>
	                      <form:input class="form-control" path="itemName" id="invProdNm1" placeholder="Product Name" required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Availability</label>
	                      <input class="form-control" name="invProdAvl" id="invProdAvl1" placeholder="Product Availability" type="text" required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Booked Quantity</label>
	                      <input class="form-control" name="invProdBookedQty" id="invProdBookedQty1" placeholder="Product Booked Quantity" type="text" required="required" readonly="readonly"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Refill Quantity</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdRefillQty" id="invProdRefillQty1" placeholder="Product Refill Quantity" required="required"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product Refill Date</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdRefillDt" id="invProdRefillDt1" placeholder="Product Refill Date" required="required" readonly="true"/>
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Product MRP</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
	                      <form:input class="form-control" path="invProdMrp" id="invProdMrp1" placeholder="Product MRP" required="required" />
	                    </div>	
                  	</div>
                  	
                  	<div class="col-md-6">
                  		<div class="form-group">
	                      <label for="exampleInputEmail1">Comments</label>
	                      <form:textarea class="form-control" path="invRefillComments" placeholder="Comments"/>
	                    </div>	
                  	</div>
                  	
                  </div>
                  
                  <!-- Box Footer -->
                  <div class="box-footer">
                  	<form:input type="hidden" path="itemMasterDtlsId" id="itemMasterDtlsId1" />
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                  <!-- Box Footer -->                  
                  
                </form:form>
              </div>
        </div>
     </div>
    </div>
    </div>
    
   </div>
</div>
<!-- ./Update Inventory Details -->

<!-- Add Product Modal -->
<div id="addProduct" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add a new product</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Product Details</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="wproductdetails" method="post" modelAttribute="productDetails" enctype="multipart/form-data">
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputEmail1">Product Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" path="itemNm" placeholder="Product Name" type="text" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                        <!-- select -->
	                      <form:select class="form-control" path="itemCategory" id="prodCat" onchange="getSubCatDetails(this.value);">
	                        <option value="-11">--Select--</option>
	                        <c:forEach var="category" items="${categoryList }">
								<option value="${category.id }">${category.name }</option>	                        
	                        </c:forEach>
	                      </form:select>
                    </div>
                    <%-- <div class="form-group">
                      <label for="exampleInputPassword1">Inventory Quantity</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Available Quantity" type="text" path="initialQuantity" required="required" />
                    </div> --%>
                    <div class="form-group">
                      <!-- <label for="exampleInputPassword1">Product Content Info</label> -->
                      <form:hidden class="form-control" placeholder="Product Content Info" path="itemContentInfo" value=""/>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Per Piece Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="CP" type="text" path="perUnitPrice" required="required" />
                    </div>
					<div class="form-group">
                      <label for="exampleInputPassword1">Items In Master Carton</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="MRP" type="text" path="itemsInMasterCarton" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Max allowed Master Carton in Order</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Master Carton Quantity Range" path="masterCartonQtyRange" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Number</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Product Number" path="itemNo" required="required" />
                    </div>
                    <%-- <div class="form-group">
                      <label for="exampleInputPassword1">Per Piece Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Per Carton Price" path="masterCartonPrice" required="required" />
                    </div> --%>
                    <div class="form-group">
                      <label for="exampleInputFile">HSN No.</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:select class="form-control" path="hsnDtlsId" >
                      	<form:option value="0">Select</form:option>
	                      	<c:forEach var="hsnDetails" items="${hsnDetailsList }">
	                      		<form:option value="${hsnDetails.hsnDtlsId }">${hsnDetails.hsnNo }</form:option>
	                      	</c:forEach>
                      </form:select>
                    </div>
                    </div>
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Product Description" path="itemDesc" type="text" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Sub-Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                        <!-- select -->
	                      <form:select class="form-control" path="itemSubCategory" disabled="true" id="prodSubCat">
	                      </form:select>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Size</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
                      <form:select class="form-control" path="itemPckgInfo">
                        <option value="select">--Select--</option>
						<c:forEach var="packagingInfo" items="${itemsPackingInfoList }">
							<option value="${packagingInfo.packagingInfo }">${packagingInfo.packagingInfo }</option>
						</c:forEach>                        
                      </form:select>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Master Carton Batch Qty</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Master Carton Quantity Increment Value" path="masterCartonQtyIncVal" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputFile">Product Image</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input id="exampleInputFile" path="itemImage" type="file" />
                      <p class="help-block">Upload an image of size 10x10.</p>
                    </div>
                   <%-- <div class="form-group">
                      <label for="exampleInputPassword1">Threshold Value</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Threshold Value" path="ThrhldVal" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Refill Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Refill Price" path="refilPrice"/>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Selling Price(MRP)</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="MRP" type="text" path="mrp" required="required" />
                    </div> --%>
                    <%--  <div class="form-group">
                      <label for="exampleInputPassword1">Master Carton Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="MRP" type="text" path="masterCartonPrice" required="required" />
                    </div> --%>
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  	<form:hidden path="cmpnyInfoId" value="56"/>
                  	<form:hidden path="itemManufacturer" value="Komal Industries"/>
                  	<form:hidden path="ThrhldVal" value="0.0" />
                  	<form:hidden path="refilPrice"  value="0.0" />
                  	<form:hidden path="mrp" />
                  	<form:hidden path="initialQuantity" value="0.0"/>
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
<!-- ./Add Product Modal -->


<!-- Edit Product Modal -->
<div id="editProduct" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Product</h4>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-md-12">
              <!-- general form elements -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Product Details</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form:form role="form" action="editproductdetails" method="post" modelAttribute="updateProductDetails" 
                enctype="multipart/form-data" >
                  <div class="box-body" style="color:#333;">
        			<div class="col-md-12">
        				<div class="form-group">
                      <label for="exampleInputPassword1">Product Id</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Product Id" type="text" id="productId1" path="productId" readonly="true" />
                    </div>
        			</div>
        			<div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputEmail1">Product Name</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" path="itemNm" placeholder="Product Name" id="productName1" type="text" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                        <!-- select -->
	                      <form:select class="form-control" path="itemCategory" id="productCategory1" onchange="getSubCatDetails1(this.value);">
	                         <option value="select">--Select--</option>
	                        <c:forEach var="category" items="${categoryList }">
								<option value="${category.id }">${category.name }</option>	                        
	                        </c:forEach>
	                      </form:select>
                    </div>
                   <%--  <div class="form-group">
                      <label for="exampleInputPassword1">Available Quantity</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Available Quantity" type="text" id="availableQty1" path="availableQty" required="required" disabled="true"/>
                    </div> --%>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Size</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                       <!-- select -->
                      <form:select class="form-control" path="itemPckgInfo" id="productPackagingInfo1">
                        <option value="select">--Select--</option>
						<c:forEach var="packagingInfo" items="${itemsPackingInfoList }">
							<option value="${packagingInfo.packagingInfo }">${packagingInfo.packagingInfo }</option>
						</c:forEach>                        
                      </form:select>
                    </div>
                      <form:hidden class="form-control" placeholder="Product Content Info" path="itemContentInfo" id="productContent1" value=""/>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Items In Master Carton</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Items In Master Carton" type="text" path="itemsInMasterCarton" required="required" id="itemsInMasterCarton1"/>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Number</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Product Number" path="itemNo" id="productNo1" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Master Carton Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Master Carton Price" path="masterCartonPrice" id="masterCartonPrice1" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Per Piece Price</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Per Piece Price" path="perUnitPrice" id="perUnitPrice1" required="required" />
                    </div>
                    </div>
                    <div class="col-md-6">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Description</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Product Description" id="productDescription1" path="itemDesc" type="text" required="required"/>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Product Sub-Category</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                        <!-- select -->
	                      <form:select class="form-control" path="itemSubCategory" id="productSubCategory1">
	                      </form:select>
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Max allowed Master Carton in Order</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Master Carton Quantity Range" path="masterCartonQtyRange" id="masterCartonQtyRange1" required="required" />
                    </div>
                     <div class="form-group">
                      <label for="exampleInputPassword1">Master Carton Batch Qty</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input class="form-control" placeholder="Master Carton Quantity Increment Value" path="masterCartonQtyIncVal" id="masterCartonQtyIncVal1" required="required" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputFile">Product Image</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:input id="exampleInputFile" path="itemImage" type="file" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputFile">HSN No.</label><i class="fa fa-asterisk" style="color:red;font-size:9px;"></i>
                      <form:select id="hsnId1" class="form-control" path="hsnDtlsId" >
                      	<form:option value="0">Select</form:option>
	                      	<c:forEach var="hsnDetails" items="${hsnDetailsList }">
	                      		<form:option value="${hsnDetails.hsnDtlsId }">${hsnDetails.hsnNo }</form:option>
	                      	</c:forEach>
                      </form:select>
                    </div>
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                  <form:hidden path="manufacturer" value="Komal Industries"/>
                   <form:hidden path="companyInfoId" value="56"/>
                   <input type="hidden" name="pageNumDetail" id="pagenum" />
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
<!-- ./Edit Product Modal -->

 <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
    <script src="plugins/daterangepicker/daterangepicker.js"></script>
    <script src="plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- InputMask -->
    <script src="plugins/input-mask/jquery.inputmask.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
    <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script> 
<script>
$('#invProdRefillDt1').datepicker();
</script>
  </body>
</html>
