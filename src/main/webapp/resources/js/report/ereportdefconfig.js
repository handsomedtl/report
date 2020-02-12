var datasetId;
function querycustrpt(){
	$('#custrpt').datagrid({
    url:'reportdesc.jhtml',
    method:'post',
    loadMsg:'加载中...',
    
    rownumbers:true,
    singleSelect:true,
    queryParams: {
		reportName: $('#rptname').val()
	},
    columns:[[
        {field:'REPORTID',title:'报表编号',width:100,align:'center'},
        {field:'REPORTNAME',title:'报表名称',width:150,align:'center'},
        {field:'DATASETID',title:'数据集编号',width:100,align:'center'},
        {field:'GRIDID',title:'数据网格编号',width:100,align:'center'},
        {field:'TEMPLID',title:'查询条件模板',width:100,align:'center'}
    ]]});
	
}

$(document).ready(function() {
	querycustrpt();
	$('#custrpt').datagrid({
		onClickRow:function(index,row){
			datasetId=row.DATASETID;
			editIndex = undefined;
			editIndex1 = undefined;
			editIndex2 = undefined;
			data1="";
			if(row.REPORTID!='' && row.REPORTID!=undefined)dispalayReport(row.REPORTID);
			else $("#custrptcomp").datagrid('loadData', { total: 0, rows: [] });
			if(row.DATASETID!='' &&  row.DATASETID!=undefined){
				displayDataset(row.DATASETID);
				displayDatasetCol(row.DATASETID);
				displayDatasetCond(row.DATASETID);
			}
			else{
				$("#datasetcol").datagrid('loadData', { total: 0, rows: [] });
				$("#datasetcond").datagrid('loadData', { total: 0, rows: [] });
			}
			if(row.GRIDID!='' &&  row.GRIDID!=undefined)displayGrid(row.GRIDID);
			else{
				$('#gridHead').val(null);
				$('#gridname').textbox('setValue', null);
				$('#gridtype').combobox('setValue', null);
				$('#functionlist').combobox('setValues', null);
				$('#ispaging').combobox('setValue', null);
				$('#issum').combobox('setValue', null);
			}
			if(row.TEMPLID!='' &&  row.TEMPLID!=undefined)displayTemplid(row.TEMPLID);
		}
	})
	$('#rptname').textbox({
	icons: [{
				iconCls:'icon-clear',
				handler: function(e){
					$(e.data.target).textbox('clear');
					querycustrpt();
		    }
	}]
})
})

function dispalayReport(reportid){
	$('#custrptcomp').datagrid({
		iconCls: 'icon-edit',
	    url:'reportcompdesc.jhtml',
	    method:'post',
	    loadMsg:'加载中...',
	    singleSelect:true,
	    toolbar: '#tb',
	    onClickCell: onClickCell,
        onEndEdit: onEndEdit,
	    queryParams: {
			reportid: reportid
		},
	    columns:[[
	        {field:'REPORTID',title:'报表编号',width:100,align:'center',editor:'textbox'},
	        {field:'TYPE',title:'组成类型',width:150,align:'center',formatter:function(value,row){
                return row.TYPE;
            },
	            editor:{
	                type:'combobox',
	                options:{
	                	valueField:'id',
                        textField:'type',
                        method:"post",
                        panelHeight:"60px",
                        editable:false,
                        url:"rptcomptype.jhtml",
                        required:true
	                }
	            }
	        },
	        {field:'COMPID',title:'组成类型编号',width:100,align:'center',editor:'textbox'},
	        {field:'DATASETID',title:'数据集编号',width:100,align:'center',editor:'textbox'}
	    ]]});
}
function displayDataset(datasetid){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : "displaydataset.jhtml",// 请求的action路径
		data : {
			datasetid : datasetid
		},
		error : function() {// 请求失败处理函数
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '获取数据集请求失败'
			});
		},
		success : function(data) { // 请求成功后处理函数。
			$('#SQL').text(data.sql);
			$('#resourcetype').combobox('setValue', data.restype);
			$('#type').combobox('setValue', data.type);
			$('#datasetname').textbox('setValue',data.name);
			$('#database').combobox('setValue', data.dataroute);
		}
	});
//	$('#datasetname').textbox('setValue',rptName);
//  if(window.systemType == 'portal'){
//			var title = rptName;
//    		var url = window.contaxtrot+'/report/'+rptId+'.jhtml';
//    		parent.parent.addNavFrame('contentframe',url,title);	
//		}else{
//			window.open(window.contaxtrot+'/report/'+rptId+'.jhtml');
//			
//		}
}
function displayGrid(gridid){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : "displaygrid.jhtml",// 请求的action路径
		data : {
			gridid : gridid
		},
		error : function() {// 请求失败处理函数
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '获取GRID请求失败'
			});
		},
		success : function(data) { // 请求成功后处理函数。
			$('#gridHead').val(null);
			$('#gridHead').val(JSON.stringify(jQuery.parseJSON(data.header),null,6));
			$('#gridname').textbox('setValue', data.name);
			$('#gridtype').combobox('setValue', data.type);
			if(data.functionList!=null && data.functionList!="")
			var functionlist=data.functionList.split('|');
			$('#functionlist').combobox('setValues', data.functionList);
			$('#ispaging').combobox('setValue', ""+data.paging);
			$('#issum').combobox('setValue', data.rowSummary);
		}
	});
}

function displayTemplid(templid){
	
}
function displayDatasetCond(datasetid){
	$('#datasetcond').datagrid({
		iconCls: 'icon-edit',
	    url:'displaydatasetcond.jhtml',
	    toolbar: '#tb1',
	    method:'post',
	    loadMsg:'加载中...',
	    singleSelect:true,
	    rownumbers:true,
	    onClickCell: onClickCell1,
	    onEndEdit: onEndEdit,
	    queryParams: {
	    	datasetid: datasetid
		},
	    columns:[[
	        {field:'datasetId',title:'数据集编号',width:100,align:'center',editor:'textbox'},
	        {field:'id',title:'查询条件编码',width:200,align:'center',formatter:function(value,row){
                return row.id;
            },
	            editor:{
	                type:'combobox',
	                options:{
	                	valueField:'elementcode',
                        textField:'elementcode',
                        method:"post",
                        panelHeight:"200",
                        url:"datasetcondgen.jhtml",
                        formatter: formatItem,
                        queryParams : {
                        	templateid : "cusreport_ref_form"
                		},
                        required:true
	                }
	            }},
	        {field:'name',title:'名称',width:100,align:'center',editor:'textbox'},
	        {field:'bizType',title:'bizType',width:100,align:'center',editor:'textbox'},
	        {field:'required',title:'是否必选',width:100,align:'center',editor:'textbox'},
	        {field:'srcType',title:'条件之来源',width:100,align:'center',editor:'textbox'},
	        {field:'validator',title:'校验规则',width:100,align:'center',editor:'textbox'},
	        {field:'datatype',title:'数据类型',width:100,align:'center',editor:'textbox'},
	        {field:'inout',title:'入参出参(存过用)',width:100,align:'center',editor:'textbox'}
	    ]]});
}
function formatItem(row){
	var s = '<span style="font-weight:bold">' + row.elementcode + '</span><br/>' +
	 '<span style="color:#888;align:right">'+row.lable + '</span>';
	 return s;
}
var data1;
function displayDatasetCol(datasetid){
	if(datasetid!=null && datasetid!=""){
		$('#datasetcol').datagrid({
			iconCls: 'icon-edit',
		    url:'displaydatasetcol.jhtml',
		    method:'POST',
		    toolbar: '#tb2',
		    loadMsg:'加载中...',
		    singleSelect:true,
		    rownumbers:true,
		    onClickCell: onClickCell2,
		    onEndEdit: onEndEdit,
		    queryParams: {
				datasetid: datasetid
			},
		    columns:[[
		        {field:'datasetId',title:'数据集编号',width:100,align:'center',editor:'textbox'},
		        {field:'id',title:'字段编码',width:150,align:'center',editor:'textbox',formatter:function(value,row){
	                return row.name;
	            }},
		        {field:'name',title:'字段名称',width:100,align:'center',editor:'textbox'},
		        {field:'bizType',title:'渲染空间编码',width:100,align:'center',editor:'textbox'},
		        {field:'dataType',title:'数据类型',width:100,align:'center',editor:'textbox'},
		        {field:'converterSqlexp',title:'编码转名称',width:100,align:'center',editor:'textbox'}
		    ]]});
	}
	else{
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "analyssql.jhtml",// 请求的action路径
			data : {
				sql : $('#SQL').val(),
				id  : datasetId
			},
			error : function(data) {// 请求失败处理函数
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : 'data.finalsql'
				});
			},
			success : function(data) {
				reloadDatasetCol(data);
			}
		});
	}
	
}
function reloadDatasetCol(data2){
	// 请求成功后处理函数。
	$('#datasetcol').datagrid({
		iconCls: 'icon-edit',
	    toolbar: '#tb2',
	    loadMsg:'加载中...',
	    singleSelect:true,
	    striped: true, 
	    rownumbers:true,
	    onClickCell: onClickCell2,
	    onEndEdit: onEndEdit,
	    data:data2.fields,
	    columns:[[
	        {field:'datasetId',title:'数据集编号',width:100,align:'center',editor:'textbox'},
	        {field:'name',title:'字段名称',width:100,align:'center',editor:'textbox'},
	        {field:'id',title:'字段编码',width:150,align:'center',editor:'textbox',formatter:function(value,row){
	            return row.name;
	        }},
	        {field:'bizType',title:'渲染空间编码',width:100,align:'center',editor:'textbox'},
	        {field:'dataType',title:'数据类型',width:100,align:'center',editor:'textbox'},
	        {field:'converterSqlexp',title:'编码转名称',width:100,align:'center',editor:'textbox'}
	    ]]
	});
}
var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#custrptcomp').datagrid('validateRow', editIndex)){
        $('#custrptcomp').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (editIndex != index){
        if (endEditing()){
            $('#custrptcomp').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
            var ed = $('#custrptcomp').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function(){
                $('#custrptcomp').datagrid('selectRow', editIndex);
            },0);
        }
    }
}
function onEndEdit(index, row){
}
function append(){
    if (endEditing()){
        $('#custrptcomp').datagrid('appendRow',{status:'P'});
        editIndex = $('#custrptcomp').datagrid('getRows').length-1;
        $('#custrptcomp').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
    }
}
function removeit(){
    if (editIndex == undefined){return}
    $('#custrptcomp').datagrid('cancelEdit', editIndex)
            .datagrid('deleteRow', editIndex);
    editIndex = undefined;
}
function accept(){
    if (endEditing()){
        $('#custrptcomp').datagrid('acceptChanges');
    }
}
function reject(){
    $('#custrptcomp').datagrid('rejectChanges');
    editIndex = undefined;
}

/*
 * datasetcol
*/
var editIndex2 = undefined;
function endEditing2(){
    if (editIndex2 == undefined){return true}
    if ($('#datasetcol').datagrid('validateRow', editIndex2)){
        $('#datasetcol').datagrid('endEdit', editIndex2);
        editIndex2 = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell2(index, field){
    if (editIndex2 != index){
        if (endEditing2()){
            $('#datasetcol').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
            var ed = $('#datasetcol').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex2 = index;
        } else {
            setTimeout(function(){
                $('#datasetcol').datagrid('selectRow', editIndex2);
            },0);
        }
    }
}
function append2(){
    if (endEditing2()){
        $('#datasetcol').datagrid('appendRow',{status:'P'});
        editIndex2 = $('#datasetcol').datagrid('getRows').length-1;
        $('#datasetcol').datagrid('selectRow', editIndex2)
                .datagrid('beginEdit', editIndex2);
    }
}
function removeit2(){
    if (editIndex2 == undefined){return}
    $('#datasetcol').datagrid('cancelEdit', editIndex2)
            .datagrid('deleteRow', editIndex2);
    editIndex2 = undefined;
}
function accept2(){
    if (endEditing2()){
        $('#datasetcol').datagrid('acceptChanges');
    }
}
function reject2(){
    $('#datasetcol').datagrid('rejectChanges');
    editIndex2 = undefined;
}

/*
 * datasetcond
*/
var editIndex1 = undefined;
function endEditing1(){
    if (editIndex1 == undefined){return true}
    if ($('#datasetcond').datagrid('validateRow', editIndex1)){
        $('#datasetcond').datagrid('endEdit', editIndex1);
        editIndex1 = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell1(index, field){
    if (editIndex1 != index){
        if (endEditing1()){
            $('#datasetcond').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
            var ed = $('#datasetcond').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex1 = index;
        } else {
            setTimeout(function(){
                $('#datasetcond').datagrid('selectRow', editIndex1);
            },0);
        }
    }
}
function append1(){
    if (endEditing1()){
        $('#datasetcond').datagrid('appendRow',{status:'P'});
        editIndex1 = $('#datasetcond').datagrid('getRows').length-1;
        $('#datasetcond').datagrid('selectRow', editIndex1)
                .datagrid('beginEdit', editIndex1);
    }
}
function removeit1(){
    if (editIndex1 == undefined){return}
    $('#datasetcond').datagrid('cancelEdit', editIndex1)
            .datagrid('deleteRow', editIndex1);
    editIndex1 = undefined;
}
function accept1(){
    if (endEditing1()){
        $('#datasetcond').datagrid('acceptChanges');
    }
}
function reject1(){
    $('#datasetcond').datagrid('rejectChanges');
    editIndex1 = undefined;
}

function isJsonType(){
	var err = new Error();

	try{
		var jsonStr=jQuery.parseJSON($('#gridHead').val());
		$('#gridHead').val(JSON.stringify(jsonStr,null,6));
		BootstrapDialog.alert({
			title : '表头JSON解析信息',
			draggable : true,
			message : "解析成功!"
		});;
	}catch(err){
		BootstrapDialog.alert({
			title : '表头JSON解析信息',
			draggable : true,
			message : "Error name: " + err.name + "\n"+"Error message: " + err.message
		});
	}
}

function analysSql(){
	displayDatasetCol(null);
}