<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../../public/head.jsp"%>
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
<script src="${ctx}/resources/js/addtabs/addnewtabs.js"></script>
<script type="text/javascript">
	function add() {
		//$.get("workflow/start.jhtml",{processDefinitionId:'leave-formkey:2:15009'});
		window.location.href = '${ctx}/workflow/selectflow.jhtml';
	}
</script>
<title>Insert title here</title>
</head>
<body style="background: #fff;">
<div class="container">
	<div class="main_container right_col">
        <form name="" id="form1" class="form-horizontal form-label-left">
          <div class="row">
          	<div class="col-md-12 col-sm-12 col-xs-12">
                <div id="search" class="search clearfix">
                	<div class="form-up">
                        <a href="javascript:void()">
                            <i class="fa fa-collapse fa-angle-double-up" collapse="search-form"></i>
                            <!--<i class="fa fa-angle-double-up"></i>--> <!--点击后向上-->
                        </a>
                    </div>
                    <div id="search-form"><!--  id=search-form 这部分内容点击后隐藏-->
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
                          <label class="control-label col-md-3 col-sm-3 col-xs-12">流程ID：</label>
                          <div class="col-md-9 col-sm-9 col-xs-12">
                            <input name="processId" class="form-control" type="text" placeholder="请输入流程ID">
                          </div>
                        </div>
                      </div>
                      <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                        <div class="form-group">
                          <label class="control-label col-md-3 col-sm-3 col-xs-12">开始时间：</label>
                          <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                            <input name="startBeginTime" class="form-control" type="text" id="date1" placeholder="起始时间" >
                          </div>
                          <div class="col-md-1 col-sm-1"> </div>
                          <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                            <input name="startEndTime" class="form-control" type="text" id="date2" placeholder="终止时间">
                          </div>
                        </div>
                      </div>
                      <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                        <div class="form-group">
                          <label class="control-label col-md-3 col-sm-3 col-xs-12">发起人：</label>
                          <div class="col-md-9 col-sm-9 col-xs-12">
                            <input name="initiator" class="form-control" type="text" placeholder="请输入发起人工号或者全名">
                          </div>
                        </div>
                      </div>
                      <button class="btn btn-success select-3btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                      <button type="reset"  class="btn btn-info f-r">清 空</button>
                    </div>
                </div>
            </div>
          </div>
        </form>
        <div class="row"> 
           	<div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_content">
                    <asiainfo:table id="mytable" paging="true"
                            actionUrl="gettaskList.jhtml"
                            cssclass="table table-striped responsive-utilities jambo_table" formid="form1"
                            keyField="id" checkbox="true" >
                    <asiainfo:td title="流程ID" field="processInstanceId"></asiainfo:td>
                    <asiainfo:td title="流程类型" field="processTypeName"></asiainfo:td>
                    <asiainfo:td title="发起人" field="staffName"></asiainfo:td>
                    <asiainfo:td title="发起时间" field="startDate"
                                render="(new Date(data)).format(\"yyyy-MM-dd\")"></asiainfo:td>
                    <asiainfo:td title="申请内容" field="bizSummary"></asiainfo:td>
                    <asiainfo:td title="操作" field="currentTaskId"
                                render="gettype(data, type, full);"></asiainfo:td>
                    </asiainfo:table>
                    </div>
                </div>
            </div>
	</div>
</div>
<script type="text/javascript">
		var gettype = function(data, type, full) {
			if (full.currentStaff == null) {
				/* return "<a href='${ctx}/workflow/pickup.jhtml?taskId=" + data
						+ "'>签收</a>"; */
				return "<a href=\"javascript:pickupApplydata('"+data+"');\">签收</a>";
			} else if (full.currentStaff != null) {
				/* return "<a href='${ctx}/workflow/getForm.jhtml?taskId=" + data
						+ "'>办理</a>"; */
				return "<a  data-addtab=\"update\" href=\"javascript:completeApplydata('"+data+"');\" >办理</a>";
			}
		}
		var pickupApplydata = function(taskId) {
			$.get("${ctx}/workflow/pickup.jhtml?taskId="+taskId,function(result){
				if(result.status==200){
					bootbox.alert("签收成功");
					$('#mytable').DataTable().refreshtable();
				}
			}).fail(function() {
				bootbox.alert("签收失败");
				$('#mytable').DataTable().refreshtable();
			  });
		}
		var completeApplydata = function(taskId){
			var url = '${ctx}/workflow/getForm.jhtml?taskId=' +taskId;
			addTabByGeneLink('complete', '流程办理', url, 'mytable');
			/*parent.Addtabs.add({
				id : 'complete',
				title : '流程办理',
				url : '${ctx}/workflow/getForm.jhtml?taskId=' +taskId ,
				ajax : false,
				callback : function(){
					$('#mytable').DataTable().refreshtable();
				}
		});*/
		}
		
		$(document).ready(
				function() {
					$('#select1').asiainfoSelect({
						id : '#select1',
						element_id : 11000021
					});
				})
				
		$('#date1').asiainfoDate(
				{
					id : '#date1',
					format : "Y-m-d",
					timepicker : false,
					onShow : function(ct) {
						this.setOptions({
							maxDate : $('#date2').val() ? jQuery('#date2')
									.val() : false
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
							minDate : $('#date1').val() ? jQuery('#date1')
									.val() : false
						})
					},
				});
		
		
		$(document).ready(function() {
			$('#indextree').asiainfoTree({
				treeid : '11000004',
				fieldname : 'index',
				treetype : '1',
				title : '选择流程',
		   		 required:true,
		   		afterselect : function(data) {
		   			$("#processType").attr("value",data);
		   		}
			});
			
			$('#reset').click(function (){
				$("#processType").attr("value","");
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
</body>
</html>
