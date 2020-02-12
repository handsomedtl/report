<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>流程系统</title>
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>

<script type="text/javascript">
	var validateFront = function() {
		if (true === $('#form').parsley().isValid()) {
			$('.bs-callout-info').removeClass('hidden');
			$('.bs-callout-warning').addClass('hidden');
		} else {
			$('.bs-callout-info').addClass('hidden');
			$('.bs-callout-warning').removeClass('hidden');
		}
	};
	function postForm(func,funcName){
		$("#form").attr("action","${ctx}/workflow/updateStartForm.jhtml");
		$("#update").attr("disabled",'disabled');
		$("#submit").attr("disabled",'disabled');
		var flag = $('#form').parsley().validate();
		validateFront(); 
		if(flag){
			$.post("${ctx}/workflow/"+func+".jhtml",$("#form").serialize(),function(result){
				if(result.status==200){
					BDialog.alert(funcName+"成功",function(){ 
						closeTab("tab_updapplydata");
						//parent.Addtabs.close("tab_updapplydata"); 
					})
				}else if(result.status==500){
					for(var name in result.data){
						var specificField = $('#'+name).parsley();
						specificField.addError("custom-error-message", {
						message : result.data[name],
						assert : true,
						updateClass : true
						});
					}
					$("#update").removeAttr("disabled");
					$("#submit").removeAttr("disabled");
				}				
			}).fail(function(data) {
				BDialog.error(funcName+"失败",data);
				$("#update").removeAttr("disabled");
				$("#submit").removeAttr("disabled");
			  });
		}else {
			$("#update").removeAttr("disabled");
			$("#submit").removeAttr("disabled");
		}
	 }

	 
	function updateForm() {
		
		postForm("saveForm","修改");
		
		
	}
	function submitForm() {
		postForm("submit","提交");
		
		
	}
</script>

</head>
<body style="background: #fff;">
	<div class="container">
		<div class="main_container right_col">
        	<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_title">
                        <h2>
                            流程名称 :${processName}
                        </h2>
                        <!--  
                        <ul class="nav navbar-right panel_toolbox">
                            <li><a class="collapse-link"><i
                                    class="fa fa-chevron-up"></i></a></li>
                            <li class="dropdown"><a href="#" class="dropdown-toggle"
                                data-toggle="dropdown" role="button" aria-expanded="false"><i
                                    class="fa fa-wrench"></i></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#">Settings 1</a></li>
                                    <li><a href="#">Settings 2</a></li>
                                </ul></li>
                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                            </li>
                        </ul>
                        -->
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">

                        <form id="form" class="form-horizontal form-label-left" action="${ctx}/workflow/submit.jhtml" method="post" novalidate>
                            <!-- 表单内容 -->
                            <div class="item form-group">${commonForm}</div>
                            <div class="item form-group">${from}</div>
                            <input type="hidden" name="definitionKey" value="${definitionKey}">
                            <input type="hidden" name="processId" value="${processId}">
                            <div class="form-group">
                                <div class="col-md-12 col-md-offset-5 col-sm-12 col-sm-offset-5 col-xs-12 col-xs-offset-5">
                                    <input id="update" type="button" value="保存" class="btn btn-info" onclick="updateForm()">
                                    <input id="submit" type="button" value="提交" class="btn btn-success" onclick="submitForm()">
                                    <!-- <button type="submit" class="btn btn-primary">驳回</button>
                                    <button id="send" type="submit" class="btn btn-success">审核通过</button> -->
                                </div>
                            </div>
                        </form>

                    </div>
				</div>
			</div>
			<!-- /page content -->
		</div>
	</div>
<script>
	$(document).ready(
			function() {
				$.listen('parsley:field:validate', function() {
					validateFront();
				});
				

				var validateFront = function() {
					if (true === $('#form').parsley().isValid()) {
						$('.bs-callout-info').removeClass('hidden');
						$('.bs-callout-warning').addClass('hidden');
					} else {
						$('.bs-callout-info').addClass('hidden');
						$('.bs-callout-warning').removeClass('hidden');
					}
				};
			});
	
	
	</script>
</body>
</html>
