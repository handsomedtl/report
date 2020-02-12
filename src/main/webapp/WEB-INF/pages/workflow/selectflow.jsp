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
<asiainfo:resource
	type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
<title>流程系统</title>
</head>
<body style="background: #fff;">
	<div class="container body">
		<div class="main_container">
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<!--<div class="x_panel">-->
						<!--<div class="x_title">
								<h2>
									流程选择
								</h2>								
								<div class="clearfix"></div>
							</div>-->
						<div class="process-box">

							<div class="process-tit">请选择流程：</div>

							<div id="indextree" class="process-tree-input"
								style="padding-left: 10px;"></div>
						</div>
					</div>

				</div>
				<!-- /page content -->
			</div>
		</div>
		<script>
			$(document)
					.ready(
							function() {
								$('#indextree')
										.asiainfoTree(
												{
													treeid : '11000004',
													fieldname : 'index',
													treetype : '1',
													title : '选择流程',
													serverurl : window.contaxtrot
															+ '/tree/treeData.jhtml',
													required : true,
													selectLeaf : true,
													afterselect : function(data) {//id: "Borrowprocess", text: "呼和浩特预借流程", 
														window.location.href = '${ctx}/workflow/start.jhtml?flowid='
																+ data.id
																+ '&flowName='
																+ data.text;
													}
												});
							});
		</script>
</body>
</html>