<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>报表设计器</title>
<link href="${ctx}/resources/js/jquery-ui-1.12.0/jquery-ui.min.css" rel="stylesheet" />
<asiainfo:resource type="Bootstrap,jquery,dynamicform,validate"></asiainfo:resource>
<link href="${ctx}/resources/js/jquery-easyui/themes/bootstrap/easyui.css" rel="stylesheet"/>
<link href="${ctx}/resources/js/jquery-easyui/themes/icon.css" rel="stylesheet"/>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.resizable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.parser.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.panel.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.draggable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.droppable.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.layout.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.tree.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.linkbutton.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.pagination.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.datagrid.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/plugins/jquery.treegrid.js"></script>
<%-- <script src="${ctx}/resources/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script> --%>
<script src="${ctx}/resources/js/report/reportdef.js"></script>
<script src="${ctx}/resources/js/addtabs/loadfinish.js"></script>
<script src="${ctx}/resources/js/report/customreport.js"></script>
<script
	src="${ctx}/resources/gentelella/js/bootstrap3-dialog/js/bootstrap-dialog.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/gentelella/js/bootstrap3-dialog/css/bootstrap-dialog.css">
<script src="${ctx}/resources/js/colresizable/colResizable-1.6.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/colresizable/css/colresize.css" />

<script src="${ctx}/resources/js/json3.js" type="text/javascript"></script>
<link href="${ctx}/resources/gentelella/css/icheck/flat/green.css"
	rel="stylesheet" type="text/css" />
<!-- switchery -->
<link rel="stylesheet" href="${ctx}/resources/js/switchery/switchery.min.css" />

        <script src="${ctx}/resources/js/switchery/switchery.min.js"></script>
<link
	href="${ctx}/resources/gentelella/css/colorpicker/bootstrap-colorpicker.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="${ctx}/resources/gentelella/js/colorpicker/bootstrap-colorpicker.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/gentelella/js/colorpicker/docs.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/report/toolbar.js" charset="utf-8"></script>
<script src="${ctx}/resources/js/jquery.serializejson.min.js"></script>
<script src="${ctx}/resources/js/report/formula-editor.js" charset="utf-8"></script>
 <script src="${ctx}/resources/js/tooltipster/js/tooltipster.bundle.js" charset="utf-8"></script>
<link rel="stylesheet" href="${ctx}/resources/js/tooltipster/css/tooltipster.bundle.min.css" media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" href="${ctx}/resources/js/tooltipster/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-light.min.css" media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reportdef/reportdef.css" />
</head>
<body class="report-designer">
	<div class="easyui-panel" data-options="fit:true" style="padding: 0px;">
		<div id="outer-layout" class="easyui-layout" data-options="fit:true">

			<div data-options="region:'west',split:true" style="width: 200px; padding: 0px">
				<div class="side-bar easyui-layout" data-options="fit:true">
				<div id='reportpanel'  
				data-options="hideCollapsedContent:false,region:'north',title:'我的报表',split:true,tools:[{iconCls:'fa fa-plus fa-ico',handler:function(){newRptCategory();}}]"
				style="height:50%;padding: 6px">
					<ul class='easyui-tree' id='reporttreeid'>
					</ul>
				</div>
				<div id=datasetid
				data-options="hideCollapsedContent:false,region:'center',title:'数据集',split:true,tools:[{iconCls:'fa fa-search fa-ico',handler:function(){datasetContent();}}]"
				style="height:50%; padding: 10px">
					<ul class='easyui-tree' id='datasettreeid'>
					</ul>
				</div>
			</div>
			</div>
			<div region='center' style="padding: 0px;overflow-x: hidden;">
					<!-- ================ 工具栏开始 =============== -->
			<div id=reportattr>
				<div class="content">
					<div class=" report-toolbar clearfix" id="toolbar">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="page-title">
									<div class="title_left">
										<h2 title="${report.reportMeta.name}- ${report.reportMeta.id}">
											<i class="" ></i><span style="width:8px; height:8px; border:1px solid #b1b1b1; background:#ccc; margin:0 15px; border-radius:8px;"></span><span id="reportName">报表名称</span>
										</h2>
									</div>
								</div>
							</div>
						</div>
						<%-- 报表操作  开始 --%>
						<div class="tool-section col-md-12" id="report" >
							<div class="title">
								<span class="line"></span>
								<span class="text" >操作</span>
								
							</div>
							<div class="tool-large" >					
								<a class="btn btn-app" href="javascript:customreport.createnew();"> <i class="fa fa-plus"></i>新建

								</a>
								<a class="btn btn-app" href="javascript:customreport.dosave();"> <i class="fa fa-save"></i>保存
								
								</a> 
								<a class="btn btn-app" href="javascript:customreport.preview();"> <i class="fa fa-eye"></i> 打开
								</a> 
								<a class="btn btn-app" href="javascript:customreport.deleteall();"> <i class="fa fa-trash"></i> 删除
								</a>
							</div>
						</div>
						<%-- 报表操作  结束 --%>
						<%-- 表格属性设置  开始 --%>
						<div class="tool-section" id="gridconfig"
							style="padding-left: 10px;">
							<div class="title">
								<span class="line"></span>
								<span class="text">表格设置</span>
							</div>
							<form class="" action="#" method="post">
								<div class="tool-small">
									<div class="form-group">
									  <label>
													<input  name="ispaging" id="ispaging" type="checkbox" class="js-switch"  /> 分页
												</label>
												<br>
										<label>
													<input  name="isexport" id="isexport" type="checkbox" checked class="js-switch"  /> 导出
												</label>
									</div>
									
								
								</div>
							</form>
						</div>
												<%-- 查询表单设置  开始 --%>
						<div class="tool-section clearfix" id="formconfig"
							style="padding-left: 10px;">
							<div class="title">
								<span class="line"></span>
								<span class="text">过滤条件</span>
							</div>
							<form class="" action="#" method="post">
							<input type="hidden" name="templateTextId" id="templateTextId" value="${templateTextId}">
								<div class="tool-small">
									<div class="form-group switch-checkbox">										
											<label> <input type="checkbox" name="isrequired"
												id="isrequired" class="js-switch"> 必填
											</label>
											<br/>
											操作符:
										<select name="opert" id="oper" style="margin-left:3px;">
										<option value="=">=
										</option>
										</select> 
									</div>
<!-- 									<div class="form-group switch-checkbox"> -->
										
											
<!-- 									</div> -->
								</div>
							</form>
						</div>
						
						<%-- 单元格格式设置  开始 --%>
						<div class="tool-section col-md-3" id="data-format" style="width:510px;">
							<div class="title">
								<span class="line"></span>
								<span class="text">数据格式</span>
							</div>
							<form class="form-inline" action="#" method="post">

								<div class="tool-small">
									<div class="form-group">
										<div class="btn-group tool-group " data-toggle="buttons">
											<label class="btn btn-default"> <input type="radio"
												name="align" value="left"> <i
												class="fa fa-align-left"></i>
											</label> <label class="btn btn-default"> <input type="radio"
												name="align" value="center"> <i
												class="fa fa-align-center"></i>
											</label> <label class="btn btn-default"> <input type="radio"
												name="align" value="right"> <i
												class="fa fa-align-right"></i>
											</label>
										</div>

										<div class="btn-group tool-group tool-dropdown ">
											<input type="hidden" name="format" class="tool-dropdown"
												value="">
											<button data-toggle="dropdown"
												class="btn btn-default dropdown-toggle tool-btn tool-btn-dropdown"
												type="button" aria-expanded="false">
												<div class="btn-text"
													style="display: inline-block; width: 85px;">数据格式</div>
												<span class="caret"></span>
											</button>
											<ul role="menu" class="dropdown-menu tool-dropdown" id="formater">
												<li><a field="format" value="">12345</a>
												<li><a field="format" value="###,###.##">1,234</a>
												<li><a field="format" value="###,###.00">1,234.00</a></li>
												<li><a field="format"
													value="datagridFormat_NegativeRed(value,'###,###.00')"><span
														style="color: red">-1,234.00</span></a></li>

												<li class="divider"></li>
												<li><a field="format" value="###,###.000">1,234.000</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="form-group">
										<div class="btn-group tool-group" data-toggle="buttons">
											<label class="btn btn-default"> <input
												type="checkbox" name="style[]" value="bold"> <i
												class="fa fa-bold"></i>
											</label> <label class="btn btn-default"> <input
												type="checkbox" name="style[]" value="italic"> <i
												class="fa fa-italic"></i>
											</label> <label class="btn btn-default "> <input
												type="checkbox" name="style[]" value="underline"> <i
												class="fa fa-underline"></i>
											</label>
										</div>

										<div class="btn-group  tool-group demo2 colorpicker-element">
											<input type="hidden" class="colorpicker" name="background"
												value="#FFFFFF">
											<button class="btn btn-default" type="button" name="button">背景颜色</button>
											<button class="btn btn-default add-on" type="button"
												name="button" style="background-color: #eee;">
												<i
													style="background-color: rgb(247, 0, 0); border: 1px sold #ddd;"></i>
											</button>
										</div>
									</div>





								</div>
							</form>
						</div>


					</div>
				</div>
			</div>
			<!-- ================ 工具栏开始 结束 =============== -->
			<div id="grid-def-area" style="height:80%" >
				<div id="grid-layout" class="easyui-layout" data-options="fit:true">
					 <div id='selarea' region='north' split='true' title='查询条件'
						style="height: 200px; padding: 10px" data-options="split:true,hideCollapsedContent:false"></div>
					<div id='gridarea' region='center' split='true' title='数据表格'
						style="overflow:scroll;" data-options=" hideCollapsedContent:false,openAnimation:'slide'">

						<div style="width: 4200px;">
							<div class="gridtable">
								<table id="gridTab">
									<thead>
										<tr style="height: 30px">
											<th class="dropId"></th>
											<th class="dropId">A</th>
											<th class="dropId">B</th>
											<th class="dropId">C</th>
											<th class="dropId">D</th>
											<th class="dropId">E</th>
											<th class="dropId">F</th>
											<th class="dropId">G</th>
											<th class="dropId">H</th>
											<th class="dropId">I</th>
											<th class="dropId">J</th>
											<th class="dropId">K</th>
											<th class="dropId">L</th>
											<th class="dropId">M</th>
											<th class="dropId">N</th>
											<th class="dropId">O</th>
											<th class="dropId">P</th>
											<th class="dropId">Q</th>
											<th class="dropId">R</th>
											<th class="dropId">S</th>
											<th class="dropId">T</th>
											<th class="dropId">U</th>
											<th class="dropId">V</th>
											<th class="dropId">W</th>
											<th class="dropId">X</th>
											<th class="dropId">Y</th>
											<th class="dropId">Z</th>
											<th class="dropId">AA</th>
											<th class="dropId">AB</th>
											<th class="dropId">AC</th>
											<th class="dropId">AD</th>
											<th class="dropId">AE</th>
											<th class="dropId">AF</th>
											<th class="dropId">AG</th>
											<th class="dropId">AH</th>
											<th class="dropId">AI</th>
											<th class="dropId">AJ</th>
											<th class="dropId">AK</th>
											<th class="dropId">AL</th>
											<th class="dropId">AM</th>
											<th class="dropId">AN</th>
										</tr>
									</thead>
									<tbody>
										<tr class="grid-header" >
											<td class="hint">表头</td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>

										</tr>
										<tr class="grid-body">
											<td class="hint">表体</td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
										</tr>

										<tr class="grid-footer">
											<td class="hint">表尾</td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>
											<td class="drop"></td>

										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				
				</div>
				
				<!--  layout center  end -->
			</div>
		</div>
	</div>
</body>
</html>
