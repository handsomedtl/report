$(document).ready(
		function() {
			/**
			 * 设置表格表头的大小可设置
			 */

			$("#gridTab").colResizable({
				liveDrag : true,
				gripInnerHtml : "<div class='grip'></div>",
				draggingClass : "dragging",
				resizeMode : 'flex',
				postbackSafe : true,
				partialRefresh : true
			});

			$('#gridTab tr:eq(3) td.drop').formulaEditor(
					
					{"onEditEnd":function(val,e){
						// TODO 编辑完成逻辑在这里写
						customreport.editGroupField(customreport.currentcolIndex,val);
						},
					"onBeginEdit":function(e){
						var colindex = $(this).prevAll().length+1;
						//customreport.setCurrentColIndex(colindex);
						}
					}
			);
			$("#toolbar").AIPropertyToolbar(
					{
						changes : {
							"data-format" : function(param) {
								var bindFiled = customreport
										.getBindField(param['fieldName']);
							}
						}
					});
			$('#reporttreeid').tree({
				url : "myreporttree.jhtml",
				loadFilter : function(data) {
					return data;
				}
			});
			String.prototype.startWith=function(str){     
				  var reg=new RegExp("^"+str);     
				  return reg.test(this);        
				}
		});

$(function() {
	$('#selarea')
			.droppable(
					{
						accept : $('#datasettreeid').tree('getSelected'),
						onDragEnter : function() {
							$(this).addClass('over');
						},
						onDragLeave : function() {
							$(this).removeClass('over');
						},
						onDrop : function(e, source) {
							$(this).removeClass('over');
							if ($(source).hasClass('assigned')) {
								$(this).append(source);
							} else {
								var draggedNode = $('#datasettreeid').tree(
										'getNode', source);
								if (!customreport
										.isContainCondition(draggedNode.attributes.id)) {
									customreport
											.addCondition(draggedNode.attributes, this);
								}
							}
						}
					});
	$('#datasetid').droppable({
		accept : '.assigned',
		onDragEnter : function(e, source) {
			$(source).addClass('trash');
		},
		onDragLeave : function(e, source) {
			$(source).removeClass('trash');
		},
		onDrop : function(e, source) {
			$(source).fadeOut('slow');
		}
	});
});

$(function() {
	$('.gridtable td.drop').droppable({
		accept : $('#datasettreeid').tree('getSelected'),
		onDragEnter : function() {
			$(this).addClass('over');
		},
		onDragLeave : function() {
			$(this).removeClass('over');
		},
		onDrop : function(e, source) {
			$(this).removeClass('over');
			if ($(source).hasClass('assigned')) {
				$(this).empty().append(source);
			} else {
				var node = $('#datasettreeid').tree('getNode', source);
				var renderInfo=$.extend(node.attributes,{formula:false});
				customreport.addBindField(renderInfo, this);
			}
		}
	});
})
function datasetContent() {
	BootstrapDialog
			.show({
				title : '选择数据集',
				draggable : true,
				cssClass : 'dialogcss',
				message : $('<div></div>').load('datasetsel.jhtml'),
				
				onshown : function() {
					$(".easyui-treegrid").treegrid();
					/*
					$('#typeseldataset').asiainfoSelect({
						id : '#typeseldataset',
						element_id : '20160902',
						datadefault : {
							"id" : "%",
							"text" : "请选择"
						},
					});*/
				},
				buttons : [
						{
							label : '确定',
							cssClass : "m-b0 btn-orange",
							action : function(dialogRef) {
								var id = '';
								if ($('#datasetsel').treegrid('getSelected'))
									var id = $('#datasetsel').treegrid(
											'getSelected').attributes.id;
								customreport.setDatasetId(id);
								if (id == ''
										|| !($('#datasetsel').treegrid(
												'getSelected').attributes.dataset)) {
									BootstrapDialog.alert({
										title : '提示',
										draggable : true,
										message : '请选择数据集节点！'
									});
									return;
								}
								var treeid = '';
								customreport.setDatasetId(id);
								datasetGridExpand({datasetId:id});
								dialogRef.close();
							}
						}, {
							label : '取消',
							action : function(dialogRef) {
								dialogRef.close();
							}
						} ]
			});
}
$(function() {
	$('table td.drop').dblclick(function() {
		if (!$(this).is('.input')) {
			var colindex = $(this).prevAll().length;
			customreport.currentcolIndex=colindex;
			var rowindex = $(this).parent("tr").prevAll().length + 1;
			if (rowindex == 2) {
				// 如果不是拖进来来的元素则显示公式编辑
				if($(this).hasClass('dragedTd')){
					return ;
				}
				$(this).formulaEditor(
						{"onEditEnd":function(val,e){
							customreport.addFunctionBindField(val,colindex);
						},
						"onBeginEdit":function(e){
							
						}
						}
				);
				return;
			} else if (rowindex == 1) {
				customreport.editColHead(colindex);
			} else if (rowindex == 3) {
				// customreport.editGroupField(colindex);
			}

		}
	})
});

var selectFunction = function(fun) {
	customreport.Keditor.focus();
	customreport.Keditor.insertHtml(fun);
	if (fun == 'sum()') {
		$('#opercomm')
				.text(
						'请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D 也可以进行函数嵌套 sum(sum(A)+B) 等。');
	} else if (fun == 'count()') {
		$('#opercomm')
				.text(
						'请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D 也可以进行函数嵌套 count(count(A)+B) 等。');
	} else if (fun == 'avg()') {
		$('#opercomm')
				.text(
						'请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D 也可以进行函数嵌套 avg(avg(A)+B) 等。');
	} else if (fun == 'min()') {
		$('#opercomm')
				.text(
						'请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D 也可以进行函数嵌套 min(min(A)+B) 等。');
	} else if (fun == 'max()') {
		$('#opercomm')
				.text(
						'请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D 也可以进行函数嵌套 max(max(A)+B) 等。');
	}
}
var selectOper = function(oper) {
	BootstrapDialog.alert({
		title : '提示',
		draggable : true,
		message : oper
	});
}

$(function() {
	$('#reporttreeid').tree({
		onSelect: function(node){
			if(node.attributes.isReport){
					$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						dataType : "json",
						url : "getseldefreportattr.jhtml",// 请求的action路径
						data : {
							 id:node.attributes.id
						},
						error : function() {// 请求失败处理函数
							BootstrapDialog.alert({
								title : '提示',
								draggable : true,
								message : '请求失败！'
							});
						},
						success : function(data) { // 请求成功后处理函数。
							datasetGridExpand(data);
							customreport.viewReport(data);
						}
					})
			}
		}
	})
});
function datasetGridExpand(data){
	customreport.setDatasetId(data.datasetId);
	$('#datasettreeid').tree({
		url : "dataset.jhtml?id=" + data.datasetId,
		loadFilter : function(data) {
			return data;
		},
		onLoadSuccess:function(target,data){
			data=data[0];
			for (var i = 0; i < data.children.length; i++) {
				var nodei = data.children[i];
				if (nodei.attributes.isleaf) {
					$('#' + nodei.domId).draggable({
						revert : true,
						proxy : function(source) {
							var n = $('<div class="proxy"></div>');
							n.html($(source).html()).appendTo('body');
							return n;
						}
					})
				}
			}
		}
		
		
	});
	
}
function newRptCategory() {
	BootstrapDialog
			.show({
				title : '新增自定义报表分类',
				draggable : true,
				cssClass : 'dialogcss',
				message : $('<div></div>').append('<label>分类名称</label><input type="text" id="categoryname"/>'),
				buttons : [
						{
							label : '确定',
							cssClass : "m-b0 btn-orange",
							action : function(dialogRef) {
								if($('#categoryname').val()=="")  { 									
									BootstrapDialog.alert({
										title : '提示',
										draggable : true,
										message : '请填写分类名称！'
									}); 
									return
									}
								$.ajax({
									async : false,
									cache : false,
									type : 'POST',
									dataType : 'text',
									url : "savenewrptcategory.jhtml",// 请求的action路径
									data : {
										 categoryName:$('#categoryname').val()
									},
									error : function() {// 请求失败处理函数
										BootstrapDialog.alert({
											title : '提示',
											draggable : true,
											message : '新增失败！'
										}); 
									},
									success : function(data) { // 请求成功后处理函数。
										
										BootstrapDialog.alert({
											title : '提示',
											draggable : true,
											message : '添加成功！'
										}); 
										dialogRef.close();
										$('#reporttreeid').tree({
											url : "myreporttree.jhtml",
											loadFilter : function(data) {
												return data;
											}
										});
									}
								})
							}
						}, {
							label : '取消',
							action : function(dialogRef) {
								dialogRef.close();
							}
						} ]
			});
}

$(function(){
	/** ****** switchery *********************** * */
if ($(".js-switch")[0]) {
    var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
    elems.forEach(function (html) {
        var switchery = new Switchery(html, {
            color: '#3798dc',
            size:"small"
        });
    });
    var pagingCheckbox = document.querySelector('#ispaging');
    pagingCheckbox.onchange = function() {
    	 customreport.setPaging(pagingCheckbox.checked);
    };
    var isexportCheckbox = document.querySelector('#isexport');
    isexportCheckbox.onchange = function() {
    	 customreport.setExp(isexportCheckbox.checked);
   };
   var isrequiredCheckbox = document.querySelector('#isrequired');
   isrequiredCheckbox.onchange = function() {
	   if (customreport.currentCondFieldName == '') {
			BootstrapDialog.show({
				title:"提示",
				message:"请选择要设置的查询条件。！"
			});
			return;
		}
		customreport.setReportConditionProperty(
				customreport.currentCondFieldName, 'required',
				isrequiredCheckbox.checked);
  };
  $('#oper').change(function(){
	  if (customreport.currentCondFieldName == '') {
			BootstrapDialog.show({
				title:"提示",
				message:"请选择要设置的查询条件。！"
			});
			return;
		}
		customreport.setReportConditionProperty(
				customreport.currentCondFieldName, 'oper',
				$(this).val());
  });
}
/** ****** /switcher *********************** * */
})

$(function(){
// $( window ).resize(setHeight);
// $("#reportattr").resize(setHeight);
	window.setTimeout(setHeight(),3000);
	function setHeight(){
		console.log(1111);
		var $attr =$("#reportattr");
		var c = $("#grid-def-area");
		var h= $attr.outerHeight();
		var ph=$attr.parent().outerHeight();
		 c.css({height:(ph-h-5)+"px"})
		 $("#grid-layout").layout('resize',{height:(ph-h-5)});
		
    }
	
	$("#outer-layout").layout("panel","center").panel({onResize:function(w,h){setHeight();}});
})
