<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Komal Industries | Ordered Items</title>
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
<c:if test="${not empty errorMssg }" >
<h4 style="color:red;">${errorMssg }</h4>
</c:if>
<c:if test="${not empty successMssg }">
<h4 style="color:green;">${successMssg }</h4>
</c:if>
<!-- Success Or Error Messages -->

<table>
	<thead>
		<tr>
			<th style="width:10%">Sr No</th>
			<th style="width:50%">Item Name</th>
			<th style="width:20%">Type</th>
			<th style="width:20%">Quantity</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${orderedItems }" var="orderItems" varStatus="loopCount" >
			<tr>	
				<td style="width:10%">${loopCount.count }</td>
				<td style="width:50%">${orderItems.itemNm }</td>
				<td style="width:20%">${orderItems.uom }</td>
				<td style="width:20%">${orderItems.itemQty }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<c:if test="${empty errorMssg and empty successMssg }" >
	<script type="text/javascript">
		window.print();
	</script>
</c:if>

</body>
</html>