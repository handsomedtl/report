<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../../public/head.jsp"%>
<asiainfo:resource
	type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
	
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>

<script type="text/javascript">
	function add() {
		window.location.href = '${ctx}/workflow/selectflow.jhtml';
	}
</script>


<title>历史列表</title>
</head>
<body style="background: #fff;" style="padding:10px;">
	<div class="container">
		<div class="main_container right_col">

			<div class="row">
				 <div class="col-md-12 col-sm-12 col-xs-12">

					<form name="" id="form1" class="form-horizontal form-label-left"
						method="post">
<div class="search clearfix">
                        	<div class="form-up">
                            <a href="javascript:void()">
                                <i class="fa fa-collapse fa-angle-double-up" collapse="search-form"></i>
                                <!--<i class="fa fa-angle-double-up"></i>--> <!--点击后向上-->
                            </a>
                            </div>
                            <div id="search-form"><!--  id=search-form 这部分内容点击后隐藏-->
                                <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">开始时间：</label>
                                        <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                            <input name="startBeginTime" class="form-control" type="text" id="date1" placeholder="起始时间" >
                                        </div>
                                        <div class="col-md-1 col-sm-1">
                                        </div>
                                        <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                            <input name="startEndTime" class="form-control" type="text" id="date2" placeholder="终止时间">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">结束时间：</label>
                                        <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                            <input name="finishBeginTime" class="form-control" type="text" id="date3" placeholder="起始时间">
                                        </div>
                                        <div class="col-md-1 col-sm-1">
                                        </div>
                                        <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                            <input name="finishEndTime" class="form-control" type="text" id="date4" placeholder="终止时间">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">流程类型：</label>
                                        <div class="col-md-9 col-sm-9 col-xs-12">
                                        <asiainfo:select emid="processType" name="processType" id="processType" cssclass="form-control"></asiainfo:select>
                                            <!-- <select name="processType" class="form-control">
                                                <option value="">--请选择--</option>
                                                <option value="Borrow">预借</option>
                                                <option value="Add">追加</option>
                                                <option value="Conversion">毛利转换</option>
                                                <option value="Adjustment">成本调整</option>
                                            </select> -->
                                        </div>
                                    </div>
                                </div> 
                                <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12">流程状态：</label>
                                        <div class="col-md-9 col-sm-9 col-xs-12">
                                            <select name="processState" class="form-control">
                                                <option value="">--请选择--</option>
                                                
                                                <option value="3">审核通过</option>
                                                <option value="4">审核未通过</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                        <!--        <div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
                                    <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                                </div>-->
                                    <button class="btn btn-success select-3btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                                    <button type="reset"  class="btn btn-info f-r">清 空</button>
                               </div>
                        </div>

					</form>
					</div>
				


			</div>

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_content">
						<asiainfo:table id="mytable" paging="true"
							actionUrl="gettaskListCompleted.jhtml"
							cssclass="table table-striped responsive-utilities jambo_table"
							formid="form1" keyField="id" checkbox="true">
							<asiainfo:td title="流程ID" field="processInstanceId"></asiainfo:td>
							<asiainfo:td title="流程类型" field="processTypeName"></asiainfo:td>
							<asiainfo:td title="发起时间" field="startDate"
								render="(new Date(data)).format(\"yyyy-MM-dd\")"></asiainfo:td>
							<asiainfo:td title="申请内容" field="bizSummary"></asiainfo:td>
							<asiainfo:td title="状态" field="processState"
								render="processState(data);"></asiainfo:td>
							<%-- <asiainfo:td title="当前节点" field="currentNode"></asiainfo:td>
			<asiainfo:td title="当前负责人" field="currentStaffName"></asiainfo:td> --%>
							<asiainfo:td title="操作" field="processInstanceId"
								render="gettype(data, type, full);"></asiainfo:td>

						</asiainfo:table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var gettype = function(data, type, full) {
		if (data == '0') {
			return "<a  data-addtab=\"update\" href=\"javascript:updateapplydata('"
					+ full.processId
					+ "');\" url=\"${ctx}/workflow/showStartFrom.jhtml?processId="
					+ full.processId + "\">修改</a>";
		} else if (data != '0') {
			/* return "<a href='${ctx}/workflow/queryActivityMap.jhtml?processInstanceId="+ data +"&processId="+full.processId+"'>查看当前流程</a>";  */
			/* return "<a id=\"trace\" class=\"trace\" onclick=\"graphTrace({pid:'"+full.processId+"',pdid:'"+data+"'})\">查看当前流程</a>"; */
			return "<a   href=\"javascript:workfolwInfo('"
					+ data
					+ "','"
					+ full.processId
					+ "');\" url=\"${ctx}/workflow/showStartFrom.jhtml?processId="
					+ full.processId + "\">查看当前流程</a>";
		}
	}
	var workfolwInfo = function(processInstanceId, processId) {
		var url = '${ctx}/workflow/queryActivityMap.jhtml?processInstanceId='+ processInstanceId + '&processId=' + processId;
		addTabByGeneLink('add', '流程详情', url, 'mytable');
		/*parent.Addtabs.add({
			id : 'add',
			title : '流程详情',
			url : '${ctx}/workflow/queryActivityMap.jhtml?processInstanceId='
					+ processInstanceId + '&processId=' + processId,
			ajax : false,
			callback : function() {
				$('#mytable').DataTable().refreshtable();
			}
		});*/
	}

	var selectWorkflow = function() {
		var url = '${ctx}/workflow/selectflow.jhtml';
		addTabByGeneLink('add', '流程选择', url, 'mytable');
		/*parent.Addtabs.add({
			id : 'add',
			title : '流程选择',
			url : '${ctx}/workflow/selectflow.jhtml',
			ajax : false,
			callback : function() {
				$('#mytable').DataTable().refreshtable();
			}
		});*/
	}
	var updateapplydata = function(processid) {
		var url = '${ctx}/workflow/showStartFrom.jhtml?processId=' + processid;
		addTabByGeneLink('updapplydata', '修改数据', url, 'mytable');
		/*parent.Addtabs.add({
			id : 'updapplydata',
			title : '修改数据',
			url : '${ctx}/workflow/showStartFrom.jhtml?processId=' + processid,
			ajax : false,
			callback : function() {
				$('#mytable').DataTable().refreshtable();
				$('#mytable').DataTable().refreshtable();
			}
		});*/
	}
	var processState = function(data) {
		if (data == '1') {
			return "保存";
		} else if (data == '2') {
			return "审核中";
		} else if (data == '3') {
			return "通过";
		} else if (data == '4') {
			return "未通过";
		}
		return data;
	}

	$(document).ready(

			function() {

				$('#select1').asiainfoSelect({
					id : '#select1',
					element_id : 11000021
				});

				$('#date1').asiainfoDate(
						{
							id : '#date1',
							format : "Y-m-d",
							timepicker : false,
							onShow : function(ct) {
								this.setOptions({
									maxDate : $('#date2').val() ? jQuery(
											'#date2').val() : false
								})
							},
						});

				$('#date2').asiainfoDate(
						{
							id : '#date2',
							format : "Y-m-d",
							timepicker : false,
							onShow : function(ct) {
								this.setOptions({
									minDate : $('#date1').val() ? jQuery(
											'#date1').val() : false
								})
							},
						});

				$('#date3').asiainfoDate(
						{
							id : '#date3',
							format : "Y-m-d",
							timepicker : false,
							onShow : function(ct) {
								this.setOptions({
									minDate : $('#date2').val() ? jQuery(
											'#date2').val() : false
								});
							},
						})

				$('#date4').asiainfoDate(
						{
							id : '#date4',
							format : "Y-m-d",
							timepicker : false,
							onShow : function(ct) {
								this.setOptions({
									minDate : $('#date3').val() ? jQuery(
											'#date3').val() : false
								})
							}
						});
			})

	$(document).ready(function() {
		$('#indextree').asiainfoTree({
			treeid : '11000004',
			fieldname : 'index',
			treetype : '1',
			title : '选择流程',
			required : true,
			afterselect : function(data) {
				$("#processType").attr("value", data);
			}
		});

		$('#reset').click(function() {
			$("#processType").attr("value", "");
		});
	});
	
	$(function(){
		 $(".fa-collapse").each(function(){
			 var t =$(this).attr("collapse");
			 $(this).on('click',function(){
				 $(this).toggleClass('fa-angle-double-up').toggleClass('fa-angle-double-down').toggleClass('form-down');
				 $("#"+t).slideToggle("slow");
				 
			 })
			 
		 })
		})	
</script>
</html>