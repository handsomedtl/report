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
<script src="${ctx}/resources/js/component/select/jquery.select.js"></script>
<link href="${ctx}/resources/js/component/select/css/select2.min.css" rel="stylesheet">
<script src="${ctx}/resources/js/component/select/select2.full.js"></script>
<script src="${ctx}/resources/js/component/select/zh-CN.js"></script>
<script src="${ctx}/resources/js/component/textarea/jquery.textarea.js"></script>
<link href="${ctx}/resources/gentelella/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/gentelella/fonts/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/gentelella/css/animate.min.css" rel="stylesheet">
<script src="${ctx}/resources/gentelella/js/bootstrap.min.js"></script>
<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	http://localhost:8080/asiainfo-workflow/enume/demo.jhtml 测试
	<hr>
	<p>
		<label class="col-xs-2">用户名<span class='text-danger'>*</span></label>
		<select id='select1' name="eparchy_code" style="width:200px;"></select>
		<input type="submit" value='验证' onclick="checkf();"/>
	</p>
	<hr>
	<p>
		<!-- <select id='select2' name="area_code" class="form-control" style="width:200px"></select> -->
	</p>
	<hr>
	<p>
		<!-- <select id='select3' name="xian" class="form-control" style="width:200px;"></select> -->
	</p>
	
	<hr>
	<p>
		<!-- <select id='select4' name="xian" class="form-control" style="width:200px;"></select> -->
	</p>
	
	<hr>
	<textarea id='test1'></textarea>
	<p>
		<hr>
	</p>
	自定义标签select
	<asiainfo:select emid="APPROVE_STAFF_TYPE" name="ceshizi" id="ceshizi"></asiainfo:select>
	
	
	
	<script type="text/javascript">
	$('#select1').asiainfoSelect({
		id:'#select1',
		element_id:11000001,
		//listeners:[{selector:"#select2",type:"select"}],
		default_value:'G_001',
		readonly:false,
		//allowClear: true
		//disabled:true
		dataid:'%'
	});
	/*
	$('#select2').asiainfoSelect({
		id:'#select2',
		element_id:11000002,
		listeners:[{selector:"#select3",type:"select"}],
		filterfields:['#select1'],
		//default_value:'B001',
		filtervals:{eparchy_code:'0472'}
		
	});
	
	$('#select3').asiainfoSelect({
		id:'#select3',
		element_id:11000003,
		filterfields:['#select1','#select2'],
		//filtervals:{eparchy_code:'0472',area_code:'B001'}
		initData:false
	});
	$('#select4').asiainfoSelect({
		id:'#select4',
		element_id:11000021
	});
	*/
	function checkf(){
		alert($('#select1').find("option:selected").val());
	}
	
	/* $('#test1').asiainfoTextarea({
		id:'#select1',
		element_id:10000006,
		default_value:'0472'
	}); */
	
	</script>
</body>
</html>