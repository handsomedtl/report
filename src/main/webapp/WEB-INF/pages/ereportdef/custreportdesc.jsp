<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>自定义报表描述</title>
<link href="${ctx}/resources/js/jquery-ui-1.12.0/jquery-ui.min.css" rel="stylesheet" />
<asiainfo:resource type="Bootstrap,jquery,dynamicform,validate"></asiainfo:resource>
<link href="${ctx}/resources/js/jquery-easyui/themes/default/easyui.css" rel="stylesheet"/>
<link href="${ctx}/resources/js/jquery-easyui/themes/icon.css" rel="stylesheet"/>
<script src="${ctx}/resources/js/jquery-easyui/jquery.min.js"></script>

<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.resizable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.parser.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.panel.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.draggable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.droppable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.layout.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.pagination.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.datagrid.js"></script>
<script src="${ctx}/resources/js/addtabs/loadfinish.js"></script>
<script src="${ctx}/resources/js/colresizable/colResizable-1.6.min.js"></script>
<script type="text/javascript">
function openCustReport(rptId,rptName){
  if(window.systemType == 'portal'){
			var title = rptName;
    		var url = window.contaxtrot+'/report/'+rptId+'.jhtml';
    		parent.parent.addNavFrame('contentframe',url,title);	
		}else{
			window.open(window.contaxtrot+'/report/'+rptId+'.jhtml');	
		}
}
$(function(){querycustrpt();})
function querycustrpt(){
	$('#custrpt').datagrid({
    url:'custrptdesc.jhtml',
    method:'post',
    rownumbers:true,
    singleSelect:true,
    queryParams: {
		reportName: $('#rptname').val(),
		condName: $('#condname').val(),
		displayFieldName:$('#fieldname').val()
	},
    columns:[[
        {field:'reportName',title:'报表名称',width:150,align:'center',formatter: function(value,row,index){
					return  '<a href="javascript:openCustReport('+"'"+row.reportId+"'"+","+"'"+row.reportName+"'"+');">'+row.reportName+'</a>';
			}
	    },
        {field:'reportId',title:'报表编号',width:100,align:'center'},
        {field:'condName',title:'查询条件',width:500,align:'center'},
        {field:'displayFieldName',title:'展示字段',width:500,align:'center'}
    ]]});
	
}
</script>
</head>
<body>
<div class="container">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<form class="search">
					<div class="col-lg-3 col-md-3 col-sm-3 col-xs-9">
                                <div class="form-group  win-search">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-9">定义报表名称:</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <input class="form-control col-md-8 col-sm-8 col-xs-6" type="text" id="rptname" />
                                    </div>
                                </div>
                            </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-9">
                                <div class="form-group  win-search">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-9">查询条件名称：</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <input class="form-control col-md-8 col-sm-8 col-xs-8"  type="text" id="condname" />
                                    </div>
                                </div>
                            </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-9">
                                <div class="form-group  win-search">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-9">展现字段名称：</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <input class="form-control col-md-8 col-sm-8 col-xs-8"  type="text" id="fieldname" />
                                    </div>
                                </div>
                            </div>
					<button class="btn btn-success select-btn-r"  onclick="javascript:querycustrpt();return false;">查询</button>
				</form>
			</div>
			</div>
	<table id='custrpt' class="easyui-datagrid"  style="height: 700px" 
             title='自定义报表描述'>
	</table>
	</div>
</body>
</html>
