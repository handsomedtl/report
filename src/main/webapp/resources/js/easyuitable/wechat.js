$(function() {
	$('#gzlist')
			.datagrid(
					{
						idField : 'id',
						url : 'subscribedatagrid.jhtml?a=a&field=id,templateName,msgType,addTime,',
						fit : true,
						loadMsg : '数据加载中...',
						pageSize : 10,
						pagination : true,
						pageList : [ 10, 20, 30 ],
						sortOrder : 'asc',
						rownumbers : true,
						singleSelect : true,
						fitColumns : true,
						showFooter : true,
						frozenColumns : [ [] ],
						columns : [ [
								{
									field : 'id',
									title : '编号',
									hidden : true,
									sortable : true
								},
								{
									field : 'templateName',
									title : ' 模板名称',
									width : 100,
									sortable : true
								},
								{
									field : 'msgType',
									title : '类型',
									width : 100,
									sortable : true,
									formatter : function(value, rec, index) {
										var valArray = value.split(",");
										if (valArray.length > 1) {
											var checkboxValue = "";
											for (var k = 0; k < valArray.length; k++) {
												if (valArray[k] == 'text') {
													checkboxValue = checkboxValue
															+ '文本' + ','
												}
												if (valArray[k] == 'news') {
													checkboxValue = checkboxValue
															+ '图文' + ','
												}
											}
											return checkboxValue.substring(0,
													checkboxValue.length - 1);
										} else {
											if (value == 'text') {
												return '文本'
											}
											if (value == 'news') {
												return '图文'
											} else {
												return value
											}
										}
									}
								},
								{
									field : 'addTime',
									title : '时间',
									width : 100,
									sortable : true
								},
								{
									field : 'opt',
									title : '操作',
									formatter : function(value, rec, index) {
										if (!rec.id) {
											return '';
										}
										var href = '';
										href += "[<a href='#' onclick=delObj('delsubscribe.jhtml?a=a&id="
												+ rec.id + "','gzlist')>";
										href += "删除</a>]";
										return href;
									}
								} ] ],
						onLoadSuccess : function(data) {
							$("#gzlist").datagrid("clearSelections");
						},
						onClickRow : function(rowIndex, rowData) {
							rowid = rowData.id;
							gridname = 'gzlist';
						}
					});
	$('#gzlist').datagrid('getPager').pagination({
		beforePageText : '',
		afterPageText : '/{pages}',
		displayMsg : '{from}-{to}共{total}条',
		showPageList : true,
		showRefresh : true
	});
	$('#gzlist').datagrid('getPager').pagination({
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
});
function reloadTable() {
	try {
		$('#' + gridname).datagrid('reload');
		$('#' + gridname).treegrid('reload');
	} catch (ex) {
	}
}
function reloadgzlist() {
	$('#gzlist').datagrid('reload');
}
function getgzlistSelected(field) {
	return getSelected(field);
}
function getSelected(field) {
	var row = $('#' + gridname).datagrid('getSelected');
	if (row != null) {
		value = row[field];
	} else {
		value = '';
	}
	return value;
}
function getgzlistSelections(field) {
	var ids = [];
	var rows = $('#gzlist').datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i][field]);
	}
	ids.join(',');
	return ids
};
function getSelectRows() {
	return $('#gzlist').datagrid('getChecked');
}
function gzlistsearch() {
	var queryParams = $('#gzlist').datagrid('options').queryParams;
	$('#gzlisttb').find('*').each(function() {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$('#gzlist')
			.datagrid(
					{
						url : 'subscribedatagrid.jhtml?a=a&field=id,templateName,msgType,addTime,',
						pageNumber : 1
					});
}
function dosearch(params) {
	var jsonparams = $.parseJSON(params);
	$('#gzlist')
			.datagrid(
					{
						url : 'subscribedatagrid.jhtml?a=a&field=id,templateName,msgType,addTime,',
						queryParams : jsonparams
					});
}
function gzlistsearchbox(value, name) {
	var queryParams = $('#gzlist').datagrid('options').queryParams;
	queryParams[name] = value;
	queryParams.searchfield = name;
	$('#gzlist').datagrid('reload');
}
$('#gzlistsearchbox').searchbox({
	searcher : function(value, name) {
		gzlistsearchbox(value, name);
	},
	menu : '#gzlistmm',
	prompt : '请输入查询关键字'
});
function EnterPress(e) {
	var e = e || window.event;
	if (e.keyCode == 13) {
		gzlistsearch();
	}
}
function searchReset(name) {
	$("#" + name + "tb").find(":input").val("");
	gzlistsearch();
}