<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<table width="100%" >
		<thead>
			<tr>
				<th>ID</th>
				<th>DID</th>
				<th>名称</th>
				<th>KEY</th>
				<th>版本号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="process">
				<tr>
					<td>${process.id }</td>
					<td>${process.deploymentId }</td>
					<td>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td><a>启动</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>