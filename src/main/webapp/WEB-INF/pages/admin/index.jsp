<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="">
<head>
<%@include file="../../public/head.jsp"%>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>报表系统</title>
<asiainfo:resource type="Bootstrap,datatables,jquery,dynamicform"></asiainfo:resource>
<link rel="stylesheet"
	href="${ctx}/resources/css/addtabs/bootstrap-addtabs.css"
	type="text/css" media="screen" />
<script src="${ctx}/resources/js/addtabs/bootstrap-addtabs.js"></script>
<script src="${ctx}/resources/js/html5/respond.src.js"></script>
</head>

<body class="nav-md" style="overflow-y: hidden;">

	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="index.html" class="site_title"><img
							src="${ctx}/resources/images/admin_img/head_logo.png"
							style="widht: 180px; height: 65px;" /> </a>
					</div>
					<div class="clearfix"></div>

					<!-- menu prile quick info -->
					<div class="profile">
						<div class="profile_pic">
							<img src="${ctx}/resources/gentelella/images/img.jpg" alt="..."
								class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>欢迎,</span>
							<h2>${userinfo.staffname}</h2>
						</div>
					</div>
					<!-- /menu prile quick info -->

					<br />
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<asiainfo:menu userid="${username}"></asiainfo:menu>
					</div>
					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="Settings">
							<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="FullScreen">
							<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Lock"> <span
							class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Logout">
							<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
						</a>
					</div>
					<!-- /menu footer buttons -->
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">

				<div class="nav_menu">
					<nav class="" role="navigation">
					<div class="nav toggle">
						<a id="menu_toggle"><i class="fa fa-bars"></i></a>
					</div>
					<ul class="nav navbar-nav navbar-right">
						<li class=""><a href="javascript:;"
							class="user-profile dropdown-toggle" data-toggle="dropdown"
							aria-expanded="false"> <img
								src="${ctx}/resources/gentelella/images/img.jpg" alt="">Admin
								<span class=" fa fa-angle-down"></span>
						</a>
							<ul
								class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
								<li><a href="javascript:;">个人中心</a></li>
								<li><a href="javascript:;"> <span
										class="badge bg-red pull-right">50%</span> <span>设置</span>
								</a></li>
								<li><a href="login.html"><i
										class="fa fa-sign-out pull-right"></i> 注销</a></li>
							</ul></li>

						<li role="presentation" class="dropdown"><a
							href="javascript:;" class="dropdown-toggle info-number"
							data-toggle="dropdown" aria-expanded="false"> <i
								class="fa fa-envelope-o"></i> <span class="badge bg-green">6</span>
						</a>
							<ul id="menu1"
								class="dropdown-menu list-unstyled msg_list animated fadeInDown"
								role="menu">
								<li><a> <span class="image"> <img
											src="${ctx}/resources/gentelella/images/img.jpg"
											alt="Profile Image" />
									</span> <span> <span>Admin</span> <span class="time">3
												mins ago</span>
									</span> <span class="message"> Film festivals used to be
											do-or-die moments for movie makers. They were where... </span>
								</a></li>
								<li><a> <span class="image"> <img
											src="${ctx}/resources/gentelella/images/img.jpg"
											alt="Profile Image" />
									</span> <span> <span>John Smith</span> <span class="time">3
												mins ago</span>
									</span> <span class="message"> Film festivals used to be
											do-or-die moments for movie makers. They were where... </span>
								</a></li>
								<li><a> <span class="image"> <img
											src="${ctx}/resources/gentelella/images/img.jpg"
											alt="Profile Image" />
									</span> <span> <span>John Smith</span> <span class="time">3
												mins ago</span>
									</span> <span class="message"> Film festivals used to be
											do-or-die moments for movie makers. They were where... </span>
								</a></li>
								<li><a> <span class="image"> <img
											src="${ctx}/resources/gentelella/images/img.jpg"
											alt="Profile Image" />
									</span> <span> <span>John Smith</span> <span class="time">3
												mins ago</span>
									</span> <span class="message"> Film festivals used to be
											do-or-die moments for movie makers. They were where... </span>
								</a></li>
								<li>
									<div class="text-center">
										<a> <strong><a href="inbox.html">See All
													Alerts</strong> <i class="fa fa-angle-right"></i>
										</a>
									</div>
								</li>
							</ul></li>

					</ul>
					</nav>
				</div>

			</div>
			<!-- /top navigation -->
			<!-- page content -->
			<div class="right_col" role="main"
				style="background: #F7F7F7; padding: 10px 10px 10px 10px;">
				<div id="tabs"
					style="min-width: 100%; min-height: 100%; margin-top: 10px; margin-bottom: 5px; background: #fff;">
					<!-- Nav tabs -->

					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="#home"
							aria-controls="home" role="tab" data-toggle="tab">首页</a></li>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">
							<iframe class="iframeClass" frameborder="no" id="welcomepage"
								style="min-width: 100%; min-height: 530px;"
								src="${ctx}/datatabledemo/datatable.jhtml"> </iframe>
						</div>
					</div>


				</div>
				<!-- <iframe id="showHtmlId" name="showHtmlName" scrolling="auto" src="/asiainfo-workflow/datatabledemo/datatable.jhtml"
					frameborder="0"
					style="min-width: 100%; min-height: 530px; margin-bottom: 5px; background: #fff;">
				</iframe> -->

				<!-- footer content -->

				<footer>
				<div class="">
					<p class="pull-right">
						© 2014 版权所有 亚信集团股份有限公司 京ICP备11005544号-15 京公网安备110108007119号 </span>
					</p>
				</div>
				<div class="clearfix"></div>
				</footer>
				<!-- /footer content -->
			</div>
			<!-- /page content -->

		</div>

	</div>

	<div id="custom_notifications" class="custom-notifications dsp_none">
		<ul class="list-unstyled notifications clearfix"
			data-tabbed_notifications="notif-group">
		</ul>
		<div class="clearfix"></div>
		<div id="notif-group" class="tabbed_notifications"></div>
	</div>

	<div id="createModelTemplate" title="创建模型" class="template"
		style="display: none">
		<form id="modelForm" action="${ctx}/workflow/model/create.jhtml"
			target="_blank" method="post">
			<table>
				<tr>
					<td>名称：</td>
					<td><input id="name" name="name" type="text" /></td>
				</tr>
				<tr>
					<td>KEY：</td>
					<td><input id="key" name="key" type="text" /></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><textarea id="description" name="description"
							style="width: 300px; height: 50px;"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<script src="${ctx}/resources/js/component/index/index.js"></script>
</body>

</html>