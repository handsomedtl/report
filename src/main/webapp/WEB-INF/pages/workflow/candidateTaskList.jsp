<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../../public/head.jsp"%>
<asiainfo:resource type="Bootstrap,datatables,jquery"></asiainfo:resource>
<%-- <script src="${ctx}/resources/gentelella/js/jquery.min.js"></script> --%>
<script type="text/javascript">
	function add(){
 		//$.get("workflow/start.jhtml",{processDefinitionId:'leave-formkey:2:15009'});
 		window.location.href='${ctx}/workflow/start.jhtml?processDefinitionId='+'leave-formkey:2:15009';
 	} 
</script>
<title>Insert title here</title>
</head>
<body style="background: #fff;">

<div class="x_content">
		<div class="DTTT_container">
		    <a href="javascript:add();"
				class="DTTT_button DTTT_button_update">新增</a>
			<a href="javascript:importfile();"
				class="DTTT_button DTTT_button_update">修改</a>
			<a href="javascript:importfile();"
				class="DTTT_button DTTT_button_update">导入</a> <a href="#"
				class="DTTT_button DTTT_button_update">删除</a> <a
				href="javascript:exportmember();"
				class="DTTT_button DTTT_button_copy">导出</a>
		</div>
		<asiainfo:table id="mytable" paging="true" actionUrl="getcandidateTaskList.jhtml"
			cssclass="table table-striped responsive-utilities jambo_table"
			keyField="id" checkbox="true" >
			<asiainfo:td title="流程ID" field="processId"></asiainfo:td>
			<asiainfo:td title="流程名称" field="processName"></asiainfo:td>
			<asiainfo:td title="发起人" field="promoter"></asiainfo:td>
			<asiainfo:td title="发起时间" field="startTime" render="(new Date(data)).format(\"yyyy-MM-dd\")"></asiainfo:td>
			<asiainfo:td title="当前节点" field="taskName"></asiainfo:td>
			<asiainfo:td title="当前负责人" field="assignee"></asiainfo:td>
			<asiainfo:td title="操作" field="taskId" render="gettype(data, type, full);"></asiainfo:td>
			
		</asiainfo:table>
	</div>
	
</body>
<script type="text/javascript">
	var gettype = function(data, type, full) {
		if (full.name == null) {
			return "<a href='${ctx}/workflow/pickup.jhtml?taskId="+data+"'>签收</a>";
		} else if (full.name != null) {
			return "<a href='${ctx}/workflow/getForm.jhtml?taskId="+data+"'>办理</a>";
		}
		

	}
</script>
</html>