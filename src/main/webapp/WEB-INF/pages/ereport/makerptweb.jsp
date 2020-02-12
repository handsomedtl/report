<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>报表设计器</title>
    <asiainfo:resource type="Bootstrap,easyui,jquery,dynamicform,validate"></asiainfo:resource>
<%--     <script src="${ctx}/resources/js/addtabs/loadfinish.js"></script> --%>
    <script src="${ctx}/resources/js/report/customreport.js"></script>
    <script src="${ctx}/resources/js/report/reportdef.js"></script>
    resources/js/report/reportdef.js
</head>
<body>
	<div class="easyui-panel" data-options="fit:true" style="padding:0px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true,title:'数据集'" style="width:200px;padding:10px">
				Left Content
			</div>
			<div region='center' style="padding:0px">
			<div class="easyui-layout" data-options="fit:true">
				 <div region='north' split='true' title='查询条件' style="height:200px;padding:10px">
				    查询条件
			     </div>
			     <div region='center' split='true' title='数据集' style="padding:10px">
				   数据表
			     </div>
			</div>
			</div>
			<div data-options="region:'east',split:true,title:'属性'" style="width:200px;padding:10px">
				属性
			</div>			
		</div>
	</div>
</body>
</html>