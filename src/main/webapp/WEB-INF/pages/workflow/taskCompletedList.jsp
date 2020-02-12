<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../../public/head.jsp"%>
<title>Insert title here</title>
</head>
<body>
	<form id="queryOrderForm" name="queryOrderForm"
		action="/"
		method="post">
		<TABLE class="toptable grid" >
			<TBODY>
				<tr>
					<td>流程ID</td>
					<td>流程名称</td>
					<td>发起人</td>
					<td>发起时间</td>
					<td>结束时间</td>
				</tr>
				<c:forEach items="${list}" var="processTrade">
					<tr>
						<td >${processTrade.processInstanceId}</td>
						<td >${processTrade.processType}</td>
						<td >${processTrade.staffName }</td>
						<td >${processTrade.startDate}</td>	
						<td >${processTrade.finishDate}</td>
					</tr>
				</c:forEach>
			</TBODY>
		</TABLE>

		
	</form>
</body>
</html>