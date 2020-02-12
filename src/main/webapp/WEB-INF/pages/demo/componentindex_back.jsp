<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>jstree</title>
</head>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Title</title>
<meta name="viewport" content="width=device-width" />
<link rel="stylesheet" href="${ctx}/resources/js/jstree/style.css" />
<link rel="stylesheet"
	href="${ctx}/resources/js/component/select/css/select2.min.css" />
<script src="${ctx}/resources/js/jquery-1.12.1.min.js"></script>
<script src="${ctx}/resources/js/jquery-ui.js"></script>
<script src="${ctx}/resources/gentelella/js/parsley/parsley.min.js"></script>
<script src="${ctx}/resources/js/jstree.js"></script>
<script src="${ctx}/resources/js/component/tree/jquery.tree.js"></script>
<script src="${ctx}/resources/js/component/email/jquery.email.js"></script>
<script src="${ctx}/resources/js/component/select/select2.full.js"></script>
<script src="${ctx}/resources/js/component/select/jquery.select.js"></script>
<link href="${ctx}/resources/js/component/select/css/select2.min.css"
	rel="stylesheet">
<script src="${ctx}/resources/js/component/select/zh-CN.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/js/component/tree/jquery.tree.css" />
<script src="${ctx}/resources/js/component/tree/jquery.tree.js"></script>
<style>
html, body {
	background: #ebebeb;
	font-size: 10px;
	font-family: Verdana;
	margin: 0;
	padding: 0;
}

#container {
	min-width: 320px;
	margin: 0px auto 0 auto;
	background: white;
	border-radius: 0px;
	padding: 0px;
	overflow: hidden;
	width: 100%;
	min-height: 600px;
}

#tree {
	float: left;
	min-width: 319px;
	border-right: 1px solid silver;
	overflow: auto;
	padding: 0px 0;
}

#data {
	margin-left: 320px;
}

#data textarea {
	margin: 0;
	padding: 0;
	height: 100%;
	width: 100%;
	border: 0;
	background: white;
	display: block;
	line-height: 18px;
	resize: none;
}

#data, #code {
	font: normal normal normal 12px/18px 'Consolas', monospace !important;
}

#tree .folder {
	background: url('${ctx}/resources/js/jstree/file_sprite.png') right
		bottom no-repeat;
}

#tree .file {
	background: url('${ctx}/resources/js/jstree/file_sprite.png') 0 0
		no-repeat;
}

#tree .file-pdf {
	background-position: -32px 0
}

#tree .file-as {
	background-position: -36px 0
}

#tree .file-c {
	background-position: -72px -0px
}

#tree .file-iso {
	background-position: -108px -0px
}

#tree .file-htm, #tree .file-html, #tree .file-xml, #tree .file-xsl {
	background-position: -126px -0px
}

#tree .file-cf {
	background-position: -162px -0px
}

#tree .file-cpp {
	background-position: -216px -0px
}

#tree .file-cs {
	background-position: -236px -0px
}

#tree .file-sql {
	background-position: -272px -0px
}

#tree .file-xls, #tree .file-xlsx {
	background-position: -362px -0px
}

#tree .file-h {
	background-position: -488px -0px
}

#tree .file-crt, #tree .file-pem, #tree .file-cer {
	background-position: -452px -18px
}

#tree .file-php {
	background-position: -108px -18px
}

#tree .file-jpg, #tree .file-jpeg, #tree .file-png, #tree .file-gif,
	#tree .file-bmp {
	background-position: -126px -18px
}

#tree .file-ppt, #tree .file-pptx {
	background-position: -144px -18px
}

#tree .file-rb {
	background-position: -180px -18px
}

#tree .file-text, #tree .file-txt, #tree .file-md, #tree .file-log,
	#tree .file-htaccess {
	background-position: -254px -18px
}

#tree .file-doc, #tree .file-docx {
	background-position: -362px -18px
}

#tree .file-zip, #tree .file-gz, #tree .file-tar, #tree .file-rar {
	background-position: -416px -18px
}

#tree .file-js {
	background-position: -434px -18px
}

#tree .file-css {
	background-position: -144px -0px
}

#tree .file-fla {
	background-position: -398px -0px
}
</style>
</head>
<body>
	<form id="demo-form">
		<div id="container" role="main">
			<div id="popuptree" style="margin: 10px 20px; float: left;"></div>
			<div id="popuptree1" style="margin: 10px 20px; float: left;"></div>
			<div id="popuptree2" style="margin: 10px 20px; float: left;"></div>
			<div style="margin: 10px 20px; float: left;">
				<input type="text" name="email" id="email" />
			</div>
			<br>

			<div id="indextree" style="margin: 10px 20px; float: left;"></div>
			<script type="text/javascript">
				$(document).ready(function() {
					$('#indextree').asiainfoTree({
						treeid : '10000003',
						fieldname : 'index',
						treetype : '1',
						title : '请选指标',
						filterfields : [],
						filtervals : [],
						required : true,
						afterselect : function() {
							alert(1);
						}
					});
				});
			</script>
			<!--  
			<div id="departidtree" style="margin: 10px 20px; float: left;"></div>
			<script type="text/javascript">
				$(document).ready(function() {
					$('#departidtree').asiainfoTree({
						treeid : '10000008',
						fieldname : 'departid',
						treetype : '1',
						title : '所属部门'
					});
				});
			</script>
			-->
			<div>
				<select id="city" name="city" class="form-control" value="0472"
					style="width: 200px"></select>
			</div>
			<script type="text/javascript">
				$(document).ready(function() {

					$('#city').asiainfoSelect({
						id : '#city',
						element_id : '11000001',
						listeners : []
					});

				});
			</script>
			<!--  
		<select
			name="selecttest" id="select2_single"
			style="width: 170px; height: 40px;">
		</select>
		-->
			<div id="data">
				<div class="content code" style="display: none;">
					<textarea id="code" readonly="readonly"></textarea>
				</div>
				<div class="content folder" style="display: none;"></div>
				<div class="content image"
					style="display: none; position: relative;">
					<img src="" alt=""
						style="display: block; position: absolute; left: 50%; top: 50%; padding: 0; max-height: 90%; max-width: 90%;" />
				</div>
				<div class="content default" style="text-align: center;">Select
					a file from the tree.</div>
				<div>
					<input type="button" name="ceshi" value="ceshi"
						onclick="javascript:test();" />
				</div>
			</div>
		</div>
	</form>
</body>
</html>