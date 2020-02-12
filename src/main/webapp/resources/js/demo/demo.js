$(document)
		.ready(
				function() {
					window.mytable = $('#mytable')
							.DataTable(
									{
										"aLengthMenu" : [ [ 5, 10, 20 ],
												[ 5, 10, 20 ] ],
										"searching" : false,
										"fnDrawCallback" : function(oSettings) {
											installCheckbox();
										},
										"bProcessing" : true,
										"bPaginate" : false,
										"bScrollInfinite" : true,
										"bSort" : true,
										"scrollX" : "100%",
										"bLengthChange" : true,
										"bInfo" : true,
										"sAjaxSource" : "listjson.jhtml",
										"serverData" : function(source, data,
												callback, settings) {
											var postparams = '';
											settings.jqXHR = $
													.ajax({
														"dataType" : 'json',
														"type" : "POST",
														"url" : source,
														"data" : postparams,
														"success" : function(
																json) {
															 var returndata={
																	 recordsTotal:null,
																	 recordsFiltered:null,
																	 data:null
															 }
															 returndata.recordsTotal = json.length;
															 returndata.recordsFiltered = json.length;
															 returndata.data = json;
													        // return returndata;
													          //  return JSON.stringify( json );
															
															$(
																	settings.oInstance)
																	.trigger(
																			'xhr',
																			[
																					settings,
																					returndata ]);
															callback(returndata);
															
														},
													});
										},
										"bServerSide" : true,
										"oLanguage" : {
											"sLengthMenu" : " _MENU_ 条记录",
											"sZeroRecords" : "没有检索到数据",
											"sInfo" : "第 _START_ 至 _END_ 条数据 共 _TOTAL_ 条",
											"sInfoEmtpy" : "没有数据",
											"sProcessing" : "正在加载数据...",
											"sSearch" : "搜索",
											"oPaginate" : {
												"sFirst" : "首页",
												"sPrevious" : "上一页",
												"sNext" : "下一页",
												"sLast" : "末页"
											}
										},
										"aoColumns" : [
												{
													"sTitle" : "<input type=\"checkbox\" class=\"tableflat\" onclick=\"$.fn.dataTableExt.oApi.checkAll(this);\"></input>",
													"mDataProp" : null,
													"sWidth" : "20px",
													"bSortable" : false
												},
												{
													"sTitle" : "ID",
													"sName" : "id",
													"mDataProp" : "id",
													"mData" : "id",
													"sWidth" : "30",
													"bSortable" : false,
													"bVisible" : true
												},
												{
													"sTitle" : "KEY",
													"sName" : "key",
													"mDataProp" : "key",
													"mData" : "key",
													"sWidth" : "45",
													"bSortable" : false,
													"bVisible" : true,
													/*
													"mRender" : function(data,
															type, full) {
														return gettype(data);
														;
													}*/
												},
												{
													"sTitle" : "Name",
													"sName" : "name",
													"mDataProp" : "name",
													"mData" : "name",
													"sWidth" : "60",
													"bSortable" : false,
													"bVisible" : true
												},
												{
													"sTitle" : "Version",
													"sName" : "version",
													"mDataProp" : "version",
													"mData" : "version",
													"sWidth" : "105",
													"bSortable" : false,
													"bVisible" : true
												},
												{
													"sTitle" : "创建时间",
													"sName" : "createTime",
													"mDataProp" : "createTime",
													"mData" : "createTime",
													"sWidth" : "60",
													"bSortable" : false,
													"bVisible" : true
												},
												{
													"sTitle" : "最后更新时间",
													"sName" : "lastUpdateTime",
													"mDataProp" : "lastUpdateTime",
													"mData" : "lastUpdateTime",
													"sWidth" : "90",
													"bSortable" : false,
													"bVisible" : true
												}, {
													"sTitle" : "元数据",
													"sName" : "metaInfo",
													"mDataProp" : "metaInfo",
													"mData" : "metaInfo",
													"sWidth" : "45",
													"bSortable" : false,
													"bVisible" : true
												}, {
													"sTitle" : "操作",
													"sName" : "metaInfo",
													"mDataProp" : "metaInfo",
													"mData" : "metaInfo",
													"sWidth" : "30",
													"bSortable" : false,
													"bVisible" : true
												} ],
										aoColumnDefs : [ {
											targets : 0,
											render : function(data, type, row,
													meta) {
												return '<input type="checkbox" name="checklist" value="'
														+ row[1]
														+ '" class="tableflat" ></input>';
											}
										} ]
									});
					$.extend(mytable, {
						getSelrow : function() {
							var seleindexs = Array();
							$('#mytable tbody .icheckbox_flat-green').each(
									function(obj, index) {
										if (obj == undefined) {
											return;
										}
										if ($(this).hasClass('checked')) {
											seleindexs.push(obj);
										}
									});
							return seleindexs
						}
					});
				});
$.fn.dataTableExt.oApi.checkAll = function(e) {
	alert(1);
	var arrChk = $("input[class='tableflat']");
	if (e.checked) {
		$(arrChk).each(function() {
			$(this).prop("checked", true)
		});
	} else {
		$(arrChk).each(function() {
			$(this).prop("checked", false)
		});
	}
};
$.fn.dataTableExt.oApi.getSelections = function() {
	;
	var str = "";
	for (var i = 0; i < $("#mytable input[name='checklist']").size(); i++) {
		if ($($("#mytable input[name='checklist']")[i]).prop("checked")) {
			str += i + ","
		}
	}
	str.split(",");
	return str;
};
var installCheckbox = function() {
	$('#mytable_wrapper input.tableflat').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});
};
installCheckbox();