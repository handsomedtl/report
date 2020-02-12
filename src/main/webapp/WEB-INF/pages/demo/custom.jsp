<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Custonm Data Source UI Test</title>
<script src="${ctx }/resources/js/jquery-1.12.1.min.js"></script>
<script src="${ctx }/resources/js/jquery-ui.js"></script>
<link href="${ctx}/resources/js/component/select/css/select2.min.css" rel="stylesheet">
<script src="${ctx}/resources/js/component/select/select2.full.js"></script>
<script src="${ctx}/resources/js/component/select/zh-CN.js"></script>
<link href="${ctx}/resources/js/jstree/style.css" rel="stylesheet"></link>
<script src="${ctx}/resources/js/jstree.js"></script>
<script src="${ctx}/resources/js/component/number/accounting.js"></script>
<script src="${ctx }/resources/js/component/custom/jquery.custom.js"></script>
<script src="${ctx}/resources/js/component/select/jquery.select.js"></script>
<script src="${ctx}/resources/js/component/number/jquery.number.js"></script>
<script src="${ctx}/resources/js/component/phone/jquery.phone.js"></script>
<script src="${ctx}/resources/js/component/innertree/jquery.innertree.js"></script>
<body>
	<!-- <div>
		<p>custom测试</p>
		<input type="text" id="custom" name="10000001" value="111">
		
		<input type="text" id="select" name="eparchy_code" value="0472">
		
		<input type="button" onclick="changeValue()" value="初始化custom插件">
		
		<input type="text" id="indexid" name="indexid" value="911900">
		
		<p>
			<select id='select2' name="area_code" class="form-control" style="width:200px"></select>
		</p>
	</div>
	<div>
		<p>number测试</p>
		
		<input type="text" id="number" name="10000004" value="">
		
		<input type="button" onclick="checkNumber()" value="初始化number插件">
	</div>
	
	<div>
		<p>phone测试</p>
		
		<input type="text" id="phone" name="10000009" value="">
		
		<input type="button" onclick="checkPhone()" value="初始化phone插件">
	</div>
	 -->
	<p>内嵌树测试</p>
	<input type="button" onclick="initTree()" value="初始化innerTree插件">
	<input type="text" id="selectedDepart" style="width: 800px;">
	<br>
	<div id="innertree"></div>
<script type="text/javascript">
/*function changeValue(){
	$('#select2').asiainfoSelect({
		id:'#select2',
		element_id:11000002,
		initData:false,
		//listeners:['#select2','#select3'],
		filterfields:["#select"]
	});
	$("#custom").asiainfoCustom({listeners:[{selector:"#select2",type:"select"}],filterfields:["#indexid"]});
}
function checkNumber(){
	$("#number").asiainfoNumber({formatparam : {truncway:"truncate",format:"###,###.00"}});
}

function checkPhone(){
	$("#phone").asiainfoPhone();
}*/
function initTree(){
	$("#innertree").asiainfoInnerTree({defaultWidth:"800px",defaultHeight:"auto"});
}
</script>
</body>
</html>