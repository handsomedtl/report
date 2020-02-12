<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../../public/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate,easyui"></asiainfo:resource>
<%-- <script src="${ctx}/resources/js/easyuitable/demo.js"></script> --%>
<title>easyuitable测试</title>
</head>
<body style="background: #fff;">
<div class="container">
    	<div class="main_container right_col">	
${grid}

 		</div>
</div>
</body>

</html>