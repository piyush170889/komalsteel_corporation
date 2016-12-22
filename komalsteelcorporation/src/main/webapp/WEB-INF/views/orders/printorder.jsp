<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Id : {printOrder.orderId}</title>
</head>
<body>
<c:set var="orderDetail" value="${printOrder.orderDetails}"></c:set>
	<table>
		<tr>
			<td>Name</td>
			<td>: ${printOrder.fNm} ${printOrder.lNm} </td>
			<td>Contact</td>
			<td>: ${printOrder.cntcNum}</td>
			<td>Display Name</td>
			<td>: ${printOrder.displayName}</td>
		</tr>
		<tr>
			<td>Order Id</td>
			<td>: ${orderDetail.cartDtlId}</td>
			<td>Order Date </td>
			<td>: ${orderDetail.orderDate} </td>
			<td>Order Status</td>
			<td>: ${orderDetail.cartStatus}</td>
		</tr>
		<tr>	
			<td>Order Notes </td>
			<td colspan="2">: ${orderDetail.cartNotes} </td>
			<td>Alternate Contact</td>
			<td>: ${orderDetail.alternateContactNo}</td>
		</tr>
		<tr>
			<td>Lr No</td>
			<td>: ${orderDetail.lrNo}</td>
			<td>Lr Date </td>
			<td>: ${orderDetail.lrDate} </td>
			<td>Items loaded </td>
			<td>: ${orderDetail.itemsLoaded}</td>
		</tr>
		<tr>
			<td>Delivery Address</td>
			<td colspan="5">: ${orderDetail.st1}</td>
		</tr>
		<tr>
			<td>State</td>
			<td>: ${orderDetail.state}</td>
			<td>City</td>
			<td>: ${orderDetail.city} </td>
			<td>TIN NO. </td>
			<td>: ${orderDetail.tinNo}</td>
		</tr>
		<tr>
			<td>Mark</td>
			<td>: ${orderDetail.mark}</td>
			<td>Destination</td>
			<td>: ${orderDetail.destination} </td>
			<td>Transporter Name</td>
			<td>: ${orderDetail.tranNm}</td>
		</tr>
	</table>
</body>
</html>