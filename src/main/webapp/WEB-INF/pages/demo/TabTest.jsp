<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/resources/gentelella/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="${ctx}/resources/css/addtabs/bootstrap-addtabs.css" type="text/css" media="screen" />
<script src="${ctx}/resources/gentelella/js/jquery.min.js"></script>
<script src="${ctx}/resources/gentelella/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>
 <!--[if lt IE 9]>
          <script src="theme/js/html5shiv.min.js"></script>
          <script src="theme/js/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            /*$(function(){
                $('#tabs').addtabs({monitor:'.topbar'});
            })*/
        </script>
</head>
<body>
	<header class="topbar admin-header">
            <div class="brand">
                <small></small>
            </div>
            <div class="topbar-collapse">
                <!--Button group-->
                <div class="btn-group" role="group" aria-label="...">
                	<button type="button" class="btn btn-default" data-addtab="model" url="/asiainfo-workflow/workflow/model/list.jhtml">模型工作区</button>
                    <button type="button" class="btn btn-default" data-addtab="task" url="/asiainfo-workflow/workflow/taskList.jhtml">任务列表</button>
                    <button type="button" class="btn btn-default" data-addtab="design" url="/asiainfo-workflow/workflow/candidateTaskList.jhtml">签收列表</button>
                    <button type="button" class="btn btn-default" data-addtab="mytask" url="/asiainfo-workflow/workflow/processTradeExecution.jhtml">我的任务</button>
                <!--     <button type="button" class="btn btn-default" data-addtab="setting" url="/admin/setting" title="指定标题"><i class="glyphicon glyphicon-cog"></i>指定标题</button>
                    <button type="button" class="btn btn-default" data-addtab="ajax" url="/admin/profiles" ajax='true'><i class="glyphicon glyphicon-user"></i>使用AJAX</button> -->
                </div>
            </div>
        </header>


        <!-- <div class="col-md-12">
            <div id="tabs">
                Nav tabs
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>                    
                </ul>
                Tab panes
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="home">
                    	1111
                    </div>                    
                </div>

            </div>

        </div> -->
</body>
</html>