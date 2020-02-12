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
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate"></asiainfo:resource>
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
	<div class="container body">

		<div class="main_container">
			<!-- page content -->
			<div class="right_col" role="main" style="margin-left: 0px">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>流程名称</h2>
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

								<form class="form-horizontal form-label-left"
									data-parsley-validate id="subform" novalidate>
									<!-- 表单内容 -->
									<div class="item form-group">${formcontent}</div>

									<div class="ln_solid"></div>
									<div class="errormessage" style="width: 100%;"></div>

									<div class="form-group">
										<div class="col-md-12 col-md-offset-5">
											<button type="submit" class="btn btn-primary">驳回</button>
											<button id="send" type="submit" class="btn btn-success">审核通过</button>
										</div>
									</div>
								</form>

							</div>
						</div>
					</div>
				</div>

			</div>
			<!-- /page content -->
		</div>
	</div>
	<script>
		$(document).ready(
				function() {
					var specificField = $('#departid_pop').parsley();
					//specificField.addError("custom-error-message", {
					//message : '错误',
					//assert : true,
					//updateClass : true
					//	});
					$.listen('parsley:field:validate', function() {
						validateFront();
					});
					$('#subform .btn').on(
							'click',
							function() {
								var valid = $('#subform').parsley().validate();
								if (valid) {
									$('#subform').attr('action',
											'${ctx}/test/testdynamicform.jhtml');
									$('#subform').submit();		
								}
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