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

<link href="${ctx}/resources/gentelella/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/gentelella/fonts/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/gentelella/css/animate.min.css" rel="stylesheet">
<script src="${ctx}/resources/gentelella/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/js/component/bootbox/bootbox.js"></script>


<style>
html, body {
	background: none;
}
</style>
</head>
<body>
	http://localhost:8080/asiainfo-workflow/demo/bootbox.jhtml 测试
	<br/>
	官方api   http://bootboxjs.com/documentation.html#bb-confirm-dialog-default
	<hr/>
	<input type="button" value="默认警告框" onclick="click1()" />
	<hr/>
	<input type="button" value="警告框-带回调函数" onclick="click2()" />
	<hr/>
	<input type="button" value="警告框-自定义参数" onclick="click3()" />
	<hr/>
	<input type="button" value="confirm-默认" onclick="click4()" />
	<hr/>
	<input type="button" value="confirm-自定义参数" onclick="click5()" />
	<hr/>
	<input type="button" value="prompt-默认" onclick="click6()" />
	<hr/>
	<input type="button" value="prompt-自定义" onclick="click7()" />
	<hr/>
	<input type="button" value="Dialog -使用" onclick="click8()" />
	<hr/>
	
	
	
	<script type="text/javascript">
	//message:$(“#obj”).clone();
	bootbox.setDefaults("locale","zh_CN");
	//默认警告框
	function click1(){
		bootbox.alert("这里是我想输出内容");
	}
	
	//警告框-带回调函数
	function click2(){
		bootbox.alert("点击ok回调", function(){ 
			alert('111')
		})
	}
	
	//警告框-自定义参数
	function click3(){
		bootbox.alert({ 
		    size: 'small',
		    message: "这里是我想输出内容",
		    //回调函数
		    callback: function(){
		    	alert('111')
		    }
		})
	}
	
	//confirm-默认
	function click4(){
		bootbox.confirm("你想输出的内容", function(result){ 
			alert(result) //点击按钮后回调，ok返回true，取消返回flase
		})
	}
	
	//confirm-自定义参数
	function click5(){
		bootbox.confirm({ 
		    size: 'small',
		    message: "你想输出的内容", 
		    callback: function(result){
		    	alert(result) //点击按钮后回调，ok返回true，取消返回flase
		    }
		})
	}
	//prompt-默认
	function click6(){
		bootbox.prompt("Your message here…", function(result){ 
			//可以回调的内容
			alert(result) //点击按钮后回调，ok返回true，取消返回flase
		})
	}
	//prompt-自定义
	function click7(){
		bootbox.prompt({ 
		    size: 'small',
		    title:'尝试',
		    callback: function(result){ /* your callback code */ }
		})
	}
	function click8(){
		bootbox.dialog({
		    title : "修改密码",
		    message : "<div class='well ' style='margin-top:25px;'><form class='form-horizontal' role='form'><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtOldPwd'>旧密码</label><div class='col-sm-9'><input type='text' id='txtOldPwd' placeholder='请输入旧密码' class='col-xs-10 col-sm-5' /></div></div><div class='space-4'></div><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtNewPwd1'>新密码</label><div class='col-sm-9'><input type='text' id='txtNewPwd1' placeholder='请输入新密码' class='col-xs-10 col-sm-5' /></div></div><div class='space-4'></div><div class='form-group'><label class='col-sm-3 control-label no-padding-right' for='txtNewPwd2'>确认新密码</label><div class='col-sm-9'><input type='text' id='txtNewPwd2' placeholder='再次输入新密码' class='col-xs-10 col-sm-5' /></div></div></form></div>",
		    buttons : {
		        "success" : {
		            "label" : "<i class='icon-ok'></i> 保存",
		            "className" : "btn-sm btn-success",
		            "callback" : function() {
		                var txt1 = $("#txtOldPwd").val();
		                var txt2 = $("#txtNewPwd1").val();
		                var txt3 = $("#txtNewPwd2").val();
		 
		                if(txt1 == "" || txt2 == "" || txt3 == ""){
		                    bootbox.alert("密码不能为空");
		                    return false;
		                }
		                if(txt2 != txt3 ){
		                    bootbox.alert("两次输入新密码不一致，请重新输入!");
		                    return false;
		                }
		                var info = {"opt":"changepassword","oldpwd":txt1,"newpwd1":txt2,"newpwd2":txt3};
		                    bootbox.alert("密码更新成功");
		            }
		        },
		        "cancel" : {
		            "label" : "<i class='icon-info'></i> 取消",
		            "className" : "btn-sm btn-danger",
		            "callback" : function() { }
		        }
		    }
		});
	}
	
	
	</script>
</body>
</html>