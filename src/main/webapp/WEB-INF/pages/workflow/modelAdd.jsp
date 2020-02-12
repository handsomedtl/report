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

<title>流程新建</title>
<asiainfo:resource type="Bootstrap,validate,jquery,dynamicform"></asiainfo:resource>
<!--[if lt IE 9]>
        <script src="../assets/js/ie8-responsive-file-warning.js"></script>
        <![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>
<body style="background: #fff;">
	<div class="container">
		<div class="main_container right_col">
        	<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12 margin-tb10"> 
                    <form class="form-horizontal" id="form1" name="form1">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 col-xs-2 control-label">名称:</label>
                            <div class="col-sm-8 col-xs-8">
                                <input type="text" class="form-control" required id="name" name="name" data-parsley-error-message="请填写流程名。"
                                    data-parsley-errors-container=".errormessage" placeholder="流程名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="processType" class="col-sm-2 col-xs-2 control-label">流程类型:</label>
                            <div class="col-sm-8 col-xs-8">
                                <asiainfo:select emid="processType" name="processtype"
                                id="processType" cssclass="form-control" errorMessage="请选择流程类型。" errorContainer=".errormessage" required="required"></asiainfo:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="popuptree" class="col-sm-2 col-xs-2 control-label">流程范围:</label>
                            <div class="col-sm-8 col-xs-8">
                                <div id="popuptree"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-sm-2 col-xs-2 control-label">流程描述:</label>
                            <div class="col-sm-8 col-xs-8">
                                <textarea id="description" name="description" class="form-control" rows="5"></textarea>
                            </div>
                        </div>
                        <div class="errormessage" style="width: 100%;"></div>
                        <div class="form-group">
                            <div class="group-center">
                                <button type="button" class="btn btn-success">提交</button>
                            </div>
                        </div>
                    </form>
            </div>
        </div>
    </div>
</div>
	<script type="text/javascript">
		$(document).ready(function() {
		    $('#popuptree').asiainfoTree({
					treeid : '10000008',
					fieldname : 'depatids',
					treetype : '1',
					multiSelect : true,
					title : '请选择部门',
					required:true,
					tooltipmess:'请选择范围',
					defaultvalue : {
						text : '${userinfo.privilegedeptname}',
						value : '${userinfo.privilegedeptid}'
					}
				});
		});
		$(".btn").click(function(){
			var flag = $('#form1').parsley().validate();
			validateFront();
			$('.btn').attr('disabled','disabled')
			if(flag){
				var fromdata = $("#form1").serialize();
				$.ajax({
					type : "POST",
					url : '${ctx}/workflow/model/create.jhtml',
					data : fromdata,
					dataType : "json",
					success : function(data) {
						if(data.success){
							window.location.href='${ctx}/modeler.html?modelId='+data.modelId+'&processType='+data.processType;
						}else{
							$('.btn').removeAttr('disabled')
							BDailog.alert("数据创建异常");
						}
					},
					error : function(info) {
						//console.log("数据请求失败！")
						BDailog.alert("服务器异常");
					}
				});
			}
		})
		$.listen('parsley:field:validate', function() {
				validateFront();
			});
			var validateFront = function() {
				if (true === $('#form1').parsley().isValid()) {
					$('.bs-callout-info').removeClass('hidden');
					$('.bs-callout-warning').addClass('hidden');
				} else {
					$('.bs-callout-info').addClass('hidden');
					$('.bs-callout-warning').removeClass('hidden');
				}
			};
		</script>
</body>
</html>
