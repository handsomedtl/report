<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0, maximum-scale=1.0">
<meta content="telephone=no" name="format-detection" />
<title>SELECT demo</title>
<%@include file="../../public/head.jsp"%>
<script src="${ctx}/resources/js/jquery-1.12.1.min.js"></script>
<script src="${ctx}/resources/js/jquery-ui.js"></script>
<script src="${ctx}/resources/js/uploadify/jquery.uploadify.min.js"></script>
<link href="${ctx}/resources/js/uploadify/css/uploadify.css" rel="stylesheet">
<script src="${ctx}/resources/js/component/file/jquery.file.js"></script>

<link href="${ctx}/resources/gentelella/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/gentelella/fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/resources/gentelella/css/main.css" rel="stylesheet">
<link href="${ctx}/resources/gentelella/css/animate.min.css" rel="stylesheet">
<script src="${ctx}/resources/gentelella/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/js/component/bootbox/bootbox.js"></script>

<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	http://localhost:8080/asiainfo-workflow/file/demo.jhtml 测试
	<hr/>
	<!-- <input type="file" name="file_upload" id="file_upload" />
	1111 -->
	
	<div id="test1"></div>
	
	
	<input type="button" id="bt1" value="dianji"></button>
	
	<script type="text/javascript">
	$('#test1').asiainfoFile({
		id:'#test1',
		element_id:10000006,
		//readonly:true,
		//fileTypeExts:'*.jpg;*.docx',
		default_value:'file_haha.doc:测试.doc|file_123.xls:测试xls.xls'
	});
	
	
	</script>
</body>
</html>