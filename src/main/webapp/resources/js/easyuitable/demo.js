$(document).ready(function() {
	$('#dg1').datagrid({
		   // url:'http://127.0.0.1?reportid=1001&gridid=1',
		    url:'datagrid_data.jhtml',
		    loadMsg : '数据加载中...',
		    sortOrder : 'asc',
		    singleSelect : true,
		    fit : true,
		    showFooter : true,
		    columns: [
		    [
		        {
		            title: '员工编号'
		                ,field: 'STAFF_ID'
		                ,align: 'LEFT'
		                ,width: '300'
		                ,rowspan: 1
		                ,colspan: 1
		        },
		        
		        {
		            title: '部门编码'
		                ,field: 'CUST_ID'
		                ,align: 'RIGHT'
		                ,width: '300'
		                ,formatter: function(value,row,index){
		                   return  value; //'###,###.00';
		                }
		                ,rowspan: 1
		                ,colspan: 1
		        },
		        
		        {
		            title: '员工名称'
		                ,field: 'UPDATE_TIME'
		                ,align: 'LEFT'
		                ,width: '300'
		                ,formatter: function(value,row,index){
		                   return  value; //'yyyy/m/d h:mm';
		                }
		                ,rowspan: 1
		                ,colspan: 1
		        },
		        
		        {
		            title: '费用'
		                ,field: 'UPDATE_TIME'
		                ,align: 'LEFT'
		                ,width: '300'
		                ,formatter: function(value,row,index){
		                
		                  return  value ; //'yyyy/m/d h:mm';
		                }
		                ,rowspan: 1
		                ,colspan: 1
		        }
		        
		        
		    ]
		 ]
		  ,autoRowHeight:true
		 ,striped:true
		 ,rownumbers:true
		  ,pagination:true 
		  ,pageSize:10
		  ,pageList:[10,30,50,70,100]
		 ,queryParams:function(){
		    return {
				name: 'easyui',
				subject: 'datagrid'
			};
		 },
		 toolbar: [{
				iconCls: 'icon-save',
				handler: function(){alert('filesave')}
			},'-',{
				iconCls: 'icon-print',
				handler: function(){alert('print')}
			}],
		 onLoadSuccess : function(data) {
			  $("#dg1").datagrid("clearSelections");
			}
		});
		$('#dg1').datagrid('getPager').pagination({
				beforePageText : '',
				afterPageText : '/{pages}',
				displayMsg : '{from}-{to}共{total}条',
				showPageList : true,
				showRefresh : true
			});
			$('#dg1').datagrid('getPager').pagination({
				onBeforeRefresh : function(pageNumber, pageSize) {
					$(this).pagination('loading');
					$(this).pagination('loaded');
				}
			});

});


/*
$('#dg').datagrid({
	url : 'datagrid_data.jhtml',
	frozenColumns : [ [ {
		field : 'code',
		title : '表头1',
		width : '200px',
		align : 'center',
		colspan : 2

	} ],

	[ {
		field : 'code',
		title : '表头1',
	
		align : 'center',
		colspan : 2

	} ], [ {
		field : 'code',
		title : '表头11',
		
		align : 'center'

	}, {
		field : 'name',
		title : '表头12',
		
		align : 'center'

	} ] ],
	columns : [ [ {
		field : 'name',
		title : '表头3',
		rowspan : 1,
		width : '500px',
		colspan : 3,
		align : 'center'
	}

	],

	[ {
		field : 'code',
		title : 'Code',
	}, {
		field : 'name',
		title : 'Name',

	}, {
		field : 'price',
		title : 'Price',
		align : 'right'
	} ] ],
	autoRowHeight : true,
	pagination:true,
	rownumbers:true,
	pageSize:100,
	pageList:[100,200,300,400,500],
	showFooter:true,
	queryParams:function(){
		return   {
			name: 'easyui',
			subject: 'datagrid'
		 }
	},
	onLoadSuccess:function(data){
		$("#dg").datagrid("clearSelections");
	},
});*/
