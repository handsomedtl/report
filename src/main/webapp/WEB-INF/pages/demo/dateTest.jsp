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
<link href="${ctx}/resources/js/datetimepicker-master/jquery.datetimepicker.css" rel="stylesheet">
<script src="${ctx}/resources/js/datetimepicker-master/build/jquery.datetimepicker.full.js"></script>
<script src="${ctx}/resources/js/component/date/jquery.date.js"></script>
<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	http://localhost:8080/asiainfo-workflow/date/demo.jhtml 测试
	11111
	<p>
		<input id="datetimepicker" type="text" >
	</p>
	日期格式
	<p>
		<input id="date2" type="text" >
	</p>
	只有日期
	<p>
		<input id="date3" type="text" >
	</p>
	只有时间
	<p>
		<input id="date4" type="text" >
	</p>
	取值
	<p>
		<input id="date5" type="text" >
	</p>
	
	<script type="text/javascript">
	$('#datetimepicker').datetimepicker({
		mask:{replace:function(){
		}},
		minDate:0, // now 
		minTime:0, // now
		maxDate:0, // now 
		maxTime:0,
		value:'2016/04/13 12:00',
	});	
	
	$('#date2').asiainfoDate({
		id:'#date2',
		element_id:10000005,
	});
	$('#date3').asiainfoDate({
		id:'#date3',
		element_id:10000006
	});
	$('#date4').asiainfoDate({
		id:'#date4',
		element_id:10000007
	});
	
	$('#date5').asiainfoDate({
		id:'#date5',
		element_id:10000005,
		value:'2016/04/13 12:00',
		readonly:true
	});
	
	</script>
</body>
</html>