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
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
</head>
<body style="background: #fff;">
	
<div class="x_panel" style="font-weight: bold;">
		 <div class="col-md-7 col-sm-12 col-xs-12">  
		    申请内容:
		 </div>
		<div class="x_content">
			<div class="form-horizontal form-label-left">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="item form-group">
							<div class="form-group" >
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">申请人
								</lable>
								<div class="col-md-3 col-sm-12 col-xs-12">
									<input type="text" name="" readonly="readonly" value="${workFlow.staffName}"
										class="form-control col-md-12 col-xs-12" />
								</div>
								 <lable class="control-label col-md-3 col-sm-12 col-xs-12"></lable>
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">发起时间</lable>
								<div class="col-md-3 col-sm-12 col-xs-12">
									<input type="text" name="" readonly="readonly" value="<fmt:formatDate value='${workFlow.startDate}' type='date'/>"
										class="form-control col-md-12 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
						    	<lable class="control-label col-md-1 col-sm-12 col-xs-12">申请原因:
								</lable>
								<div class="col-md-9 col-xs-12" style="margin-top: 8px;">									
										${reason}								
								</div>
						    	</div>
							<div class="form-group">
							${workFlow.startNode}
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<div class="clearfix"></div>
	<div class="x_panel" style="font-weight: bold; ">
	审核详情：
	<c:if test="${empty workFlow.processApproves}">尚未审批</c:if>
		<c:forEach items="${workFlow.processApproves}" var="workflow">
		
		<div class="x_content" style="margin-left: 10px;">
			
			<div class="form-horizontal form-label-left">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="item form-group">
							<div class="form-group">
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">审批角色:</lable>
								<div class="col-md-2 col-sm-12 col-xs-12" style="margin-top: 8px;">																	
									${workflow.taskName}																
								</div>
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">审批人:</lable>
								<div class="col-md-2 col-sm-12 col-xs-12" style="margin-top: 8px;">																					
									${workflow.staffName}					
								</div>
								
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">审批结果:</lable>
								<div class="col-md-2 col-sm-12 col-xs-12" style="margin-top: 8px;">																
								<c:if test="${workflow.approveState=='1' }">通过
								</c:if>
								<c:if test="${workflow.approveState=='0' }">未通过
								</c:if>								
								</div>
								<lable class="control-label col-md-1 col-sm-12 col-xs-12">审批时间:</lable>						
								<div class="col-md-2 col-sm-12 col-xs-12" style="margin-top: 8px;">								
									<fmt:formatDate value='${workflow.approveDate}' type='date'/>									
								</div>
							</div>
							<c:if test="${not empty workflow.approveRemark }">
							<div class="form-group">
						    	<lable class="control-label col-md-1 col-sm-12 col-xs-12">审批备注:
								</lable>
								<div class="col-md-9 col-xs-12" style="margin-top: 8px;">									
										${workflow.approveRemark}								
								</div>
						    </div>
						    </c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
		
	</div>
	<!--  
	<script src="${ctx}/resources/gentelella/js/bootstrap.min.js"></script>	
	<script
		src="${ctx}/resources/gentelella/js/progressbar/bootstrap-progressbar.min.js"></script>
	<script
		src="${ctx}/resources/gentelella/js/nicescroll/jquery.nicescroll.min.js"></script>
	<script src="${ctx}/resources/gentelella/js/custom.js"></script>
	<script src="${ctx}/resources/gentelella/js/validator/validator.js"></script>
	-->
</body>
</html>