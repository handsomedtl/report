<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">


<title>流程系统</title>
<asiainfo:resource
	type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>
</head>
<body style="background: #fff;">
	<div class="container">
		<div class="main_container right_col">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_title">
						<h2>流程名称 :${trade.processName}</h2>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<form id="form" class="form-horizontal form-label-left"
							method="post" novalidate>
							<div class="item form-group">${commonForm}${oldForm}</div>
						</form>
					</div>
					<div class="x_content">
						<form id="completeForm" class="form-horizontal form-label-left"
							action="${ctx}/workflow/complete/${taskId}.jhtml" method="post"
							novalidate>
							<!-- 表单内容 -->
							<c:forEach items="${nowList}" var="form">
								<div class="item form-group">
									${form}
									<div class="ln_solid"></div>
								</div>
							</c:forEach>
							<input id="leaderPass" type="hidden" name="deptLeaderPass"
								value="true">
							<div class="form-group">
								<div class="x_title">
									<h2>审批意见</h2>
									<div class="clearfix"></div>
								</div>
								<textarea class="form-control" rows="3" name="remark"></textarea>
								<div class="form-group margin-tb10">
									<div
										class="col-md-12 col-md-offset-5 col-sm-12 col-sm-offset-5 col-xs-12 col-xs-offset-5">
										<input id="overrule1" type="button" class="btn btn-primary"
											onclick="overrule(false)" value="驳回" /> <input id="pass1"
											type="button" class="btn btn-success" onclick="pass(true)"
											value="审核通过" />
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /page content -->
	</div>

	<script>
		
		function overrule(data) {
			$("#leaderPass").attr("value",data);
			$("#overrule1").attr("disabled",'disabled');
			$("#pass1").attr("disabled",'disabled');
			$.post("${ctx}/workflow/complete/${taskId}.jhtml",$("#completeForm").serialize(),function(result){
					if(result.status==200){
						BDialog.alert("已经驳回",function(){ 
							closeTab("tab_complete");
							//parent.Addtabs.close("tab_complete"); 
						})
					}
			}).fail(function() {
				BDialog.alert("驳回失败");
				$("#overrule1").removeAttr("disabled");
				$("#pass1").removeAttr("disabled");
			  });
		} 
		
		function pass(data) {
			$("#leaderPass").attr("value",data);
			$("#overrule1").attr("disabled",'disabled');
			$("#pass1").attr("disabled",'disabled');
			$.post("${ctx}/workflow/complete/${taskId}.jhtml",$("#completeForm").serialize(),function(result){
					if(result.status==200){
						BDialog.alert("审核成功",function(){ 
							closeTab("tab_complete");
							//parent.Addtabs.close("tab_complete"); 
						})
					}
			}).fail(function(resp) {
				BDialog.alert("审核失败",resp);
				$("#overrule1").removeAttr("disabled");
				$("#pass1").removeAttr("disabled");
			  });
		} 
	</script>
</body>
</html>