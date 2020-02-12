<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0, maximum-scale=1.0">
<meta content="telephone=no" name="format-detection" />
<title>SELECT demo</title>
<%@include file="../../public/head.jsp"%>
<script src="${ctx}/resources/js/jquery-1.12.1.min.js"></script>
<script src="${ctx}/resources/js/jquery-ui.js"></script>

<script charset="utf-8" src="${ctx}/resources/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctx}/resources/js/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/resources/js/component/editor/jquery.editor.js"></script>
<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	http://localhost:8080/asiainfo-workflow/ueditor/demo.jhtml 测试
	<hr/>
	kindeditor
	<textarea id="editor_id" name="content" style="width:700px;height:300px;">
	</textarea>
	<input type="button" onclick="getvalue()" value="获得值"/>
	<script type="text/javascript">
	/*
	 KindEditor.ready(function(K) {
        // window.editor = K.create('#editor_id');
         K.create('#editor_id', {
             cssPath : '/asiainfo-workflow/resources/js/kindeditor/plugins/code/prettify.css',
             uploadJson : '/asiainfo-workflow/file/upload.jhtml',
             fileManagerJson : '/asiainfo-workflow/file/manager.jhtml',
             allowFileManager : true
         });
 	});
	*/
	 $('#editor_id').asiainfoEditor({
		id:'#editor_id',
		element_id:10000006,
		readonly:true,
		default_value:'1111'
	});
	//获得值
	function getvalue(){
		var value=$('#editor_id').asiainfoEditor('getValue');
		alert(value)
	}
	</script>
</body>
</html>