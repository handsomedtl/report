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
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>

        <script type="text/javascript">
        var validateFront = function() {
			if (true === $('#subform').parsley().isValid()) {
				$('.bs-callout-info').removeClass('hidden');
				$('.bs-callout-warning').addClass('hidden');
			} else {
				$('.bs-callout-info').addClass('hidden');
				$('.bs-callout-warning').removeClass('hidden');
			}
		};
		
		function postForm(func,funcName){
			 $("#saveStartForm").attr("disabled",'disabled')
			 $("#saveForm").attr("disabled",'disabled');
			$("#submitForm").attr("disabled",'disabled');
			var flag = $('#subform').parsley().validate();
			validateFront();
			if(flag){
				$.post("${ctx}/workflow/"+func+".jhtml",$("#subform").serialize(),function(result){
					
					if(result.status==200){
						BDialog.alert(funcName+"成功",function(){ 
							closeTab("tab_add");
							//parent.Addtabs.close("tab_add");
						});
					
					}else if(result.status==503){
						BDialog.error(funcName+"异常",result.msg,function(){
							$("#saveForm").removeAttr("disabled");
							$("#submitForm").removeAttr("disabled");
						});
						
					}
					else if(result.status==500){
						for(var name in result.data){
							var specificField = $('#'+name).parsley();
							specificField.addError("custom-error-message", {
							message : result.data[name],
							assert : true,
							updateClass : true
							});
						}
						$("#saveForm").removeAttr("disabled");
						$("#submitForm").removeAttr("disabled");
					}
				}).fail(function(result) {
					BDialog.alert(result);
					$("#saveForm").removeAttr("disabled");
					$("#submitForm").removeAttr("disabled");
				  });
			}else {
				$("#saveForm").removeAttr("disabled");
				$("#submitForm").removeAttr("disabled");
			}
		 }
         function submitStartForm(){
        	 postForm("submit","启动");
			/* $("#subform").attr("action","${ctx}/workflow/submit.jhtml");
			$("#saveForm").attr("disabled",'disabled');
			$("#submitForm").attr("disabled",'disabled');
			var flag = $('#subform').parsley().validate();
			validateFront(); 
			if(flag){
				$.post("${ctx}/workflow/submit.jhtml",$("#subform").serialize(),function(result){
					if(result.status==200){
						BDialog.alert("启动成功",function(){ 
							parent.Addtabs.close("tab_add");
						});
					}else if(result.status==503){
						BDialog.error(result.msg);
						$("#saveForm").removeAttr("disabled");
						$("#submitForm").removeAttr("disabled");
					}
					else if(result.status==500){
						for(var name in result.data){
							var specificField = $('#'+name).parsley();
							specificField.addError("custom-error-message", {
							message : result.data[name],
							assert : true,
							updateClass : true
							});
						}
						$("#saveForm").removeAttr("disabled");
						$("#submitForm").removeAttr("disabled");
					}	
				}).fail(function() {
					BDialog.error("启动失败");
					$("#saveForm").removeAttr("disabled");
					$("#submitForm").removeAttr("disabled");
				  });
			}else {
				$("#saveForm").removeAttr("disabled");
				$("#submitForm").removeAttr("disabled");
			} */
		}  
         
		 function saveStartForm(){
			 postForm("saveForm","保存");
			 /* $("#saveStartForm").attr("disabled",'disabled')
			 $("#saveForm").attr("disabled",'disabled');
			$("#submitForm").attr("disabled",'disabled');
			var flag = $('#subform').parsley().validate();
			validateFront();
			if(flag){
				$.post("${ctx}/workflow/saveForm.jhtml",$("#subform").serialize(),function(result){
					
					if(result.status==200){
						BDialog.alert("保存成功",function(){ 
							parent.Addtabs.close("tab_add");
						});
					
					}else if(result.status==503){
						BDialog.alert(result.msg);
						$("#saveForm").removeAttr("disabled");
						$("#submitForm").removeAttr("disabled");
					}
					else if(result.status==500){
						for(var name in result.data){
							var specificField = $('#'+name).parsley();
							specificField.addError("custom-error-message", {
							message : result.data[name],
							assert : true,
							updateClass : true
							});
						}
						$("#saveForm").removeAttr("disabled");
						$("#submitForm").removeAttr("disabled");
					}
				}).fail(function(result) {
					BDialog.alert(result);
					$("#saveForm").removeAttr("disabled");
					$("#submitForm").removeAttr("disabled");
				  });
			}else {
				$("#saveForm").removeAttr("disabled");
				$("#submitForm").removeAttr("disabled");
			} */
		 }
		 
		 
		 </script>
</head>
<body style="background: #fff;">
	<div class="container">
    	<div class="main_container right_col">
			<!-- page content -->
			<!--<div class="right_col" role="main">-->
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_title">
                        <h2>
                            流程名称 :${flowName}
                        </h2>
                        
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
<!-- 								<select id='select2' name="area_code" class="form-control" style="width:200px"></select> -->
                        
                        <form id="subform" class="form-horizontal form-label-left" action="${ctx}/workflow/saveForm.jhtml" method="post" novalidate>
                            <!-- 表单内容 -->
                            <div id="commomForm" class="item form-group">${commonForm} </div>
                            <div id="startForm" class="item form-group">${from} </div>                            
                            <div class="ln_solid"></div>
                            <input id="definitionKey" type="hidden" name="definitionKey" value="${flowid}">
                            <div class="errormessage" style="width: 100%;"></div>
                            <div class="form-group">
                                <div class="col-md-12 col-md-offset-5">
                                    <!-- <input  id="subform" type="submit" class="btn btn-primary" value="提交">
                                    <input id="subform" type="submit" class="btn btn-primary" value="启动流程" onclick="submitStartForm()"> -->
                                     <button id="saveForm" type="button" class="btn btn-info" onclick="saveStartForm()" >保存</button>
                                     <button id="submitForm" type="button" class="btn btn-success" onclick="submitStartForm()">提交</button>
                                
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
			<!--</div>-->
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
					if (true === $('#subform').parsley().isValid()) {
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