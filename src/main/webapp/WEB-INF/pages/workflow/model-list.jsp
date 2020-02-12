<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<asiainfo:resource type="Bootstrap,datatables,jquery,dynamicform"></asiainfo:resource>
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>
<title>流程列表</title>
<script type="text/javascript">
	function showSvgTip() {
		alert('点击"编辑"链接,在打开的页面中打开控制台执行\njQuery(".ORYX_Editor *").filter("svg")\n即可看到svg标签的内容.');
	}

	//用来操作程模型
	var operationModel = function(data, url) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			dataType : "json",
			success : function(data) {
				BDialog.alert(data.message);
				if (data.success) {
					$('#mytable').DataTable().refreshtable();
				}
			},
			error : function(info) {
				console.log("数据请求失败！")
				BDialog.alert("服务器异常");
			}
		});
	}

	var getaction = function(data, type, full) {
		if (full.relaseState == 0) {
			return "<a  href='#' style=\"margin-right:3px;\" data-addtab='model"
					+ data
					+ "' title='流程模型设计' url='${ctx}/modeler.html?modelId="
					+ data
					+ "&processType="
					+ full.processType
					+ "&relaseState="
					+ full.relaseState
					+ "' onclick='addtab(this)'>编辑</a>"
					+ "<a href='#' style=\"magin-left:3px;\" onclick='operationModel(\""
					+ data
					+ "\",\"${ctx}/workflow/model/deploy/"
					+ data
					+ ".jhtml\")'>发布    </a>"
					+ "<a href='#' style=\"margin-left:3px;\" onclick='operationModel(\""
					+ data
					+ "\",\"${ctx}/workflow/model/delete/"
					+ data
					+ ".jhtml\")'>删除    </a>"
		} else {
			return "<a href='#' style=\"margin-right:3px;\" data-addtab='model"
					+ data
					+ "' title='流程模型设计' url='${ctx}/modeler.html?modelId="
					+ data
					+ "&processType="
					+ full.processType
					+ "&relaseState="
					+ full.relaseState
					+ "' onclick='addtab(this)'>查看</a>"
					+ "<a href='#' style=\"magin-left:3px;\" onclick='operationModel(\""
					+ data + "\",\"${ctx}/workflow/model/changeModelState/"
					+ data + ".jhtml\")'>取消发布   </a>"
		}

	}
	var getRelaseScope = function(data, type, full) {
		var temp = data.substr(0, 10);
		if (data.length > 10) {
			temp = temp + '...';
		}
		return temp;
	}
</script>
</head>
<body style="background: #fff;">
	<div class="container">
		<div class="main_container right_col">
			<c:if test="${not empty message}">
				<div class="ui-widget">
					<div class="ui-state-highlight ui-corner-all"
						style="margin-top: 20px; padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-info"
								style="float: left; margin-right: .3em;"></span> <strong>提示：</strong>${message}</p>
					</div>
				</div>
			</c:if>
			<form name="" id="form1" class="form-horizontal form-label-left">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="search clearfix">
							<div class="form-up">
								<a href="javascript:void()"> <i
									class="fa fa-collapse fa-angle-double-up"
									collapse="search-form"></i> <!--<i class="fa fa-angle-double-up"></i>-->
									<!--点击后向上-->
								</a>
							</div>
							<div id="search-form">
								<!--  id=search-form 这部分内容点击后隐藏-->
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 ">
									<div class="form-group">
										<label class="control-label col-md-4 col-sm-4 col-xs-12">流程名称：</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input name="processName" class="form-control" type="text"
												placeholder="">
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 ">
									<div class="form-group">
										<label class="control-label col-md-4 col-sm-4 col-xs-12">流程类型：</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<asiainfo:select emid="processType" name="processType"
												id="processType" cssclass="form-control col-md-12 col-xs-12"></asiainfo:select>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 ">
									<div class="form-group">
										<label class="control-label col-md-4 col-sm-4 col-xs-12">创建人：</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input name="staffName" class="form-control" type="text"
												placeholder="">
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 ">
									<div class="form-group">
										<label class="control-label col-md-4 col-sm-4 col-xs-12">流程状态：</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<select name="relaseState"
												class="form-control col-md-12 col-xs-12">
												<option value="">--请选择--</option>
												<option value="0">未发布</option>
												<option value="1">已发布</option>
												<option value="2">作废</option>
											</select>
										</div>
									</div>
								</div>
								<button class="btn btn-success select-btn-r" type="button"
									onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
								<button type="reset" class="btn btn-info f-r">清 空</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_content font14">
						<div class="add-btn-plr">
							<button type="button" class="btn btn-info btn-xs" title="新增流程"
								data-addtab="createModel"
								url="${ctx}/workflow/model/modelAdd.jhtml">新 增</button>
							<div class="clearfix"></div>
						</div>
						<asiainfo:table id="mytable" paging="true"
							actionUrl="processlist.jhtml"
							cssclass="table table-striped responsive-utilities jambo_table td-text"
							formid="form1" keyField="id" checkbox="false" hasChild="true"
							childFunc="getChild">
							<%-- <asiainfo:td title="流程ID" field="relaseId"></asiainfo:td> --%>
							<asiainfo:td title="流程Key" field="processKey" width="100"></asiainfo:td>
							<asiainfo:td title="流程名称" field="processName" width="100"></asiainfo:td>
							<asiainfo:td title="流程类型" field="processTypeText" width="80"></asiainfo:td>
							<asiainfo:td title="流程状态" field="relaseState"
								render="getRelaseStateDec(data);" width="80"></asiainfo:td>
							<asiainfo:td title="流程范围" field="scope" width="120" cclass="text"></asiainfo:td>
							<%-- <asiainfo:td title="模板ID" field="modelId"></asiainfo:td> --%>
							<asiainfo:td title="创建人" field="staffName" width="80"></asiainfo:td>
							<asiainfo:td title="创建时间" field="createDate"
								render="(new Date(data)).format(\"yyyy-MM-dd\")" width="100"></asiainfo:td>
							<asiainfo:td title="操作" field="modelId"
								render="getaction(data, type, full);" width="100"></asiainfo:td>
						</asiainfo:table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//子表数据
		function getChild(data) {
			var info;
			$.ajax({
				url : "scopeView.jhtml",
				type : "post",
				async : false,
				data : data,
				dataType : "html",
				success : function(data) {
					info = data;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					info = errorThrown;
				}
			});
			return info;
		}

		var getRelaseStateDec = function(data) {
			if (data == 0) {
				return "未发布";
			} else if (data == 1) {
				return "已发布";
			} else if (data == 2) {
				return "作废";
			} else {
				return "";
			}

		}

		$(function() {
			$(".fa-collapse")
					.each(
							function() {
								var t = $(this).attr("collapse");
								$(this)
										.on(
												'click',
												function() {
													$(this)
															.toggleClass(
																	'fa-angle-double-up')
															.toggleClass(
																	'fa-angle-double-down')
															.toggleClass(
																	'form-down');
													$("#" + t).slideToggle(
															"slow");

												})

							})
		})
	</script>
</body>
</html>