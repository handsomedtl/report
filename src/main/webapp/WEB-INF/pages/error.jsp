<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../public/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${ctx}/resources/js/jquery-1.12.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0, maximum-scale=1.0">
<meta content="telephone=no" name="format-detection" />
<title>报错提示</title>
<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	<div class="error-yuan"></div>
	<div class="error-text m-b4">
		<div class="error-tit">系统出现异常</div>
		<div>
			<a class="showerror" href="#">查看详情</a>
		</div>
		<div style="display: none" id="detailmess">${errormess}</div>
	</div>
</body>
<script>
$(document).ready(function() { 
  $(".showerror").click(function(){
    $("#detailmess").show();
  });
})
	</script>
</html>