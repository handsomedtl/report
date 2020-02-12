<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>定义新报表</title>

<link href="${ctx}/resources/js/jquery-ui-1.12.0/jquery-ui.min.css" rel="stylesheet" />
<asiainfo:resource type="Bootstrap,jquery,dynamicform,validate"></asiainfo:resource>
<link href="${ctx}/resources/js/jquery-easyui/themes/default/easyui.css" rel="stylesheet"/>
<link href="${ctx}/resources/js/jquery-easyui/themes/icon.css" rel="stylesheet"/>
<script src="${ctx}/resources/js/report/ereportdefconfig.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/jquery.easyui.min.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.parser.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.panel.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.layout.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.pagination.js"></script>
<script src="${ctx}/resources/js/addtabs/loadfinish.js"></script>
<script src="${ctx}/resources/js/colresizable/colResizable-1.6.min.js"></script>
</head>
<body class="report-designer">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="hideCollapsedContent:false,region:'west',split:false" title="查询区域" style="width:320px;">
        <div style="margin-bottom:20px">
            <hr>
            &nbsp&nbsp&nbsp&nbsp&nbsp<input id="rptname" class="easyui-textbox" label="报表名称" labelPosition="top" data-options="prompt:'请输入报表名称...'" style="width:200px;height:30px;float:left">
            <a href="#" class="easyui-linkbutton" onclick="javascript:querycustrpt();return false;" style="width:20%;height:30px">查 询</a>
            <hr>
            <table id='custrpt' class="easyui-datagrid"  style="height: 500px">
	        </table>
        </div>
        </div>
        <div data-options="region:'center',title:'配置区域'">
            <div class="easyui-tabs" data-options="fit:true,plain:true"> 
                <div title="报表组成概览" style="padding:10px">
                    <div id="tb" style="height:auto">
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">恢复</a>
				     </div>
	                <table id='custrptcomp' class="easyui-datagrid"  style="height: 200px">
			        </table>
		            <a href="#" class="easyui-linkbutton" onclick="javascript:querycustrpt();return false;" style="width:80px;height:30px">新增报表</a>
		            <a href="#" class="easyui-linkbutton" onclick="javascript:querycustrpt();return false;" style="width:80px;height:30px">保 存</a>
	            </div>
                <div title="数据源配置" style="padding:30px">
                <hr>
	                <label>数据集分类:</label><select class="easyui-combobox" id="category" label="数据集分类:" labelPosition="top" style="width:20%;height:50px;" panelHeight="60px" editable=false>
		                <option value="1">1-明细类</option>
		                <option value="2">2-汇总类</option>
	                </select>&nbsp&nbsp&nbsp&nbsp&nbsp
	                <label>数据集定义分类:</label><select class="easyui-combobox" id="resourcetype" label="数据集定义分类:" labelPosition="top" style="width:20%;height:50px;" panelHeight="90px" editable=false>
		                <option value="system">system-管理员定义的报表数据集</option>
		                <option value="custom">custom-自定义数据集</option>
		                <option value="REF">REF-自定义引用数据集</option>
	                </select>&nbsp&nbsp&nbsp&nbsp&nbsp
	                <label>SQL类型:</label><select class="easyui-combobox" id="type" label="数据集分类:" labelPosition="top" style="width:20%;height:50px;" panelHeight="60px" editable=false>
		                <option value="1">1-SQL</option>
		                <option value="2">2-存储过程</option>
	                </select>&nbsp&nbsp&nbsp&nbsp&nbsp
	                
	                <hr>
	                <div style="margin-bottom:20px">
	                <label>选择数据库:</label>
	                <select class="easyui-combobox" id="database" style="width:20%;height:50px;" panelHeight="80px" editable=false>
		                <option value="1">1-营销中心库</option>
		                <option value="2">2-营销分库</option>
		                <option value="3">3-营销报表库</option>
	                </select>
            		<label>名称:</label>	
            		<input class="easyui-textbox" label="名称:" id="datasetname" labelPosition="top" data-options="prompt:'数据集名称...'" style="width:20%;height:52px"/>
        			</div>
        			<hr>
        			<div style="margin-bottom:20px">
            		<label align="center">SQL:</label>
            		    <textarea id="SQL" style="resize:none;width:700px;height:300px;font-size:13px;"></textarea>  
        			<a href="#" class="easyui-linkbutton" onclick="javascript:analysSql();return false;" style="width:10%;height:30px">解 析</a>
        			</div>
        			
        			<div style="margin-bottom:20px;float:left">
            		<label align="center">可选字段列表:</label>
            		<div id="tb2" style="height:auto">
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append2()">添加</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit2()">删除</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept2()">保存</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject2()">恢复</a>
				     </div>
            		    <table id='datasetcol' style="height: 400px;width:400px">
		                </table>
            		</div>
        			<div style="height:300px;float:left">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</div>
        			<div style="margin-bottom:20px;">
	            		<label align="center">查询条件列表:</label>
	            		<div id="tb1" style="height:auto">
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append1()">添加</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit1()">删除</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept1()">保存</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject1()">恢复</a>
				     </div>	
	                    <table id='datasetcond' class="easyui-datagrid"  style="height: 400px;width:400px">
		                </table>
        			</div>
        			<a href="#" class="easyui-linkbutton" onclick="javascript:querycustrpt();return false;" style="width:10%;height:30px">保 存</a>
        			<hr>
                </div>
                <div title="数据展示配置" style="padding:10px">
                    <div style="margin-bottom:20px">
                    <label>表格组件名称:</label>	
            		<input class="easyui-textbox" label="表格组件名称:" id="gridname" labelPosition="top" data-options="prompt:'表格组件名称...'" style="width:20%;height:52px"/>
        			<label>数据表类型:</label>
	                <select class="easyui-combobox" id="gridtype" style="width:20%;height:50px;" panelHeight="80px" editable=false>
		                <option value="1">1-普通报表</option>
		                <option value="2">2-树形报表</option>
		                <option value="3">3-摘要详情</option>
	                </select>
	                <label>功能列表:</label>
	                <select class="easyui-combobox" id="functionlist" style="width:20%;height:50px;" multiple=true separator="|" panelHeight="80px" required=true editable=false> 
		                <option value="EXPORT" selected>导出</option>
		                <option value="FILTER">过滤</option>
	                </select>
	                <hr>
	                <label>是否分页:</label>
	                <select class="easyui-combobox" id="ispaging" style="width:20%;height:50px;" panelHeight="80px" editable=false>
		                <option value="true">是</option>
		                <option value="false">否</option>
	                </select>
	                <label>是否合计:</label>
	                <select class="easyui-combobox" id="issum" style="width:20%;height:50px;" panelHeight="80px" editable=false>
		                <option value="2" selected>最后合计</option>
		                <option value="1">每页合计</option>
		                <option value="0">不合计</option>
	                </select>
        			<hr>
            		<label align="center">表头配置:</label>
            		    <textarea id="gridHead" style="resize:none;width:700px;height:300px;font-size:13px;"></textarea>  
        			<a href="#" class="easyui-linkbutton" onclick="javascript:isJsonType();return false;" style="width:10%;height:30px">解 析</a>
        			</div>
        			<a href="#" class="easyui-linkbutton" onclick="javascript:querycustrpt();return false;" style="width:10%;height:30px">保 存</a>
        			<hr>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
