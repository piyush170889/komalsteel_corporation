<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Komal Industries | Ordered Items</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
table {
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid black;
	padding: 0.1em;
	text-align: center;
}
</style>

</head>

<body>

	<!-- Success Or Error Messages -->
	<c:if test="${not empty errorMssg }">
		<h4 style="color: red;">${errorMssg }</h4>
	</c:if>
	<c:if test="${not empty successMssg }">
		<h4 style="color: green;">${successMssg }</h4>
	</c:if>
	<!-- Success Or Error Messages -->
	<!-- 
            <div class="row">
                <div class="col-md-6">
                    <h4>Order Details </h4>
                </div> -->

	<div style="width: 100%; margin: 0;">
		<div style="width: 48%;float:left;">
			<label>Order Id:  </label> ${orderId} 
			</div>
		<div style="width: 48%;float:left;">
			<label>Date: </label> ${userorderDetails.orderDate }
		</div>
	</div>

	<%-- <div class="row">
                <div class="col-md-6">
                    <label style="align-content: left">Order Notes  </label>
                     <c:choose>
                        <c:when test="${not empty userorderDetails.cartNotes }">
                            : ${userorderDetails.cartNotes}
                        </c:when>
                        <c:otherwise>
                            : Not Specified
                        </c:otherwise>
                    </c:choose>
                </div>
            </div> --%>

	<%-- <div class="row">
                <div class="col-md-6">
                    <h4>Billing Details :</h4>
                </div>

            </div>
            <div class="row">
                <div class="col-md-6">
                    <label style="align-content: left">Contact : </label>${userorderDetails.alternateContactNo }
                </div>
                </div>
                <div class="row">
                <div class="col-md-6">
                    <label>Delivery Address : </label>
                   </div>
                    
            </div>
<div class="row">
                    <div class="col-md-6">
                    ${userorderDetails.st1 }, ${userorderDetails.city }, ${userorderDetails.state }
                </div>
                </div>
            <div class="row">
                <div class="col-md-6">
                    <h4>Shipping Details :</h4>
                </div>

            </div>
            <div class="row">
                <div class="col-md-6">
                    <label style="align-content: left">LR NO </label>
                    <c:choose>
                        <c:when test="${not empty userorderDetails.lrNo }">
                            : ${userorderDetails.lrNo }
                        </c:when>
                        <c:otherwise>
                            : Not Specified
                        </c:otherwise>
                    </c:choose>
                

                    <label style="margin-left:290px">LR Date </label>
                    <c:choose>
                        <c:when test="${not empty userorderDetails.lrDate}">
                            : ${userorderDetails.lrDate }
                        </c:when>
                        <c:otherwise>
                            : Not Specified
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label style="align-content: left">Item Loaded </label>
                    <c:choose>
                        <c:when test="${not empty userorderDetails.itemsLoaded}">
                            : ${userorderDetails.itemsLoaded }
                        </c:when>
                        <c:otherwise>
                            : Not Specified
                        </c:otherwise>
                    </c:choose>
               

                    <label style="margin-left:250px">Delivery Date </label>
                    <c:choose>
                        <c:when test="${not empty expectedDlvryDate }">
                            : ${userorderDetails.expectedDlvrDate }
                        </c:when>
                        <c:otherwise>
                            : Not Specified
                        </c:otherwise>
                    </c:choose>
                </div>
            </div> --%>

	<div style="width: 100%; margin: 0;">
		<div style="width: 48%;float:left;">
			<label>Name: </label> ${userorderDetails.firstName }
			${userorderDetails.lastName }
		</div>
		<div style="width: 48%;float:left;">
			<label>Contact No.: </label> ${userorderDetails.alternateContactNo }
		</div>
	</div>


	<div style="width: 100%; margin: 0;">
		<div style="width: 48%;float:left;">
			<label >Mark</label>
			<c:choose>
				<c:when test="${not empty userorderDetails.mark }">
                            : ${userorderDetails.mark }
                        </c:when>
				<c:otherwise>
                            : Not Specified
                        </c:otherwise>
			</c:choose>
</div>
		<div style="width: 48%;float:left;">
			<label >Destination</label>
			<c:choose>
				<c:when test="${not empty userorderDetails.destination }">
                            : ${userorderDetails.destination }
                        </c:when>
				<c:otherwise>
                            : Not Specified
                        </c:otherwise>
			</c:choose>
		</div>
	</div>

	<div style="width: 100%; margin: 0;">
		<div style="width: 48%;float:left;">
			<label style="align-content: left">Transportation</label>
			<c:choose>
				<c:when test="${not empty userorderDetails.tranNm }">
                            : ${userorderDetails.tranNm }
                        </c:when>
				<c:otherwise>
                            : Not Specified
                        </c:otherwise>
			</c:choose>
		</div>

	</div>

	<div style="width: 100%; margin: 0;">
		<div style="width: 48%;">
			<h4 style="text-align: left">Ordered Items</h4>
		</div>

	</div>


	<table>
		<thead>
			<tr>
				<th style="width: 10%">Sr No</th>
				<th style="width: 50%">Item Name</th>
				<th style="width: 20%">Type</th>
				<th style="width: 20%">Quantity</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderedItems }" var="orderItems"
				varStatus="loopCount">
				<tr>
					<td style="width: 10%">${loopCount.count }</td>
					<td style="width: 50%">${orderItems.itemNm }</td>
					<td style="width: 20%">${orderItems.uom }</td>
					<td style="width: 20%">${orderItems.itemQty }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${empty errorMssg and empty successMssg }">
		<script type="text/javascript">
			window.print();
		</script>
	</c:if>

</body>

</html>