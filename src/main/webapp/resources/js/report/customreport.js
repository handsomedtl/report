var customreport = {

	/**
	 * 绑定字段列表
	 */
	bindfields : new Array(),
	/**
	 * 分组设置
	 */
	groupfields : new Array(),
	/**
	 * 是否分页
	 */
	paging : true,
    /**
	 * 是否导出
	 */
	exp : true,
	/**
	 * 功能列表
	 */
	functions : new Array(),
	/**
	 * 条件列表
	 */
	conditions : new Array(),
	/**
	 * 模板表单id
	 */
	templateformid : '',
	/**
	 * 表头字符串
	 */
	headstr : '',
	/**
	 * 定义表头
	 */
	bindrowindex : 0,
	/**
	 * 定义表头行索引 --可能多行
	 */
	headerrowindex : 0,
	/**
	 * 当前列索引名称
	 */
	currentCondFieldName : '',
	/**
	 * 当前列索引
	 */
	currentcolIndex : '',
	/**
	 * 对应的数据集id
	 */
	datasetId : '',
	/**
	 * 报表id
	 */
	reportId : '',
	/**
	 * 报表名称
	 */
	reportName : '',
	/**
	 * 报表分类
	 */
	category : '',
	/**
	 * 编辑或新建状态
	 */
	isEditing : false,
	/**
	 * 报表对应表单id
	 */
	formId:'',
	/**
	 * 报表对应的girdId
	 */
	gridId:'',
	/**
	 * 报表对应数据集id
	 */
	reportDatasetId:'',
	/**
	 * 添加绑定字段
	 */
	addBindField : function(nodeInfo, currentCol) {
		
		var dataType='string';
		if(nodeInfo.dataType==2||nodeInfo.dataType==4||nodeInfo.dataType==8||nodeInfo.dataType==6||nodeInfo.dataTyp==3||nodeInfo.dataTyp==-5){
			dataType='number'
		}else if(nodeInfo.dataType==12||nodeInfo.dataType==1){
			 dataType='string';
		}else if(nodeInfo.dataType==92||nodeInfo.dataType==91||nodeInfo.dataType==93){
			 dataType='date';
		}else{
			dataType='string';
		}
		var rowindex = $(currentCol).parent("tr").prevAll().length + 1;
		var colindex = $(currentCol).prevAll().length;
		var indexNameTdI = '#gridTab tr:eq(0) th:eq(' + colindex + ')';
		var indexNameTd = $(indexNameTdI);
		var colIndexName = indexNameTd.text();
		nodeInfo=$.extend(nodeInfo,{colIndexName:colIndexName});
		if(this.isContainBindField(nodeInfo)){
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '绑定字段已经存在，不能重复绑定。'
			});
			return;
		}
		if (rowindex == 1 || rowindex == 2) {// 如果是落到表头区域和绑定区域直接生成表头和字段绑定关系
			var fieldText=nodeInfo.name;
			if(nodeInfo.formula){
				fieldText=nodeInfo.id;
			}
			var appendDiv = $('<div>').attr({
				rowindex : 1,
				colindex : colindex,
				id : 'div_1_' + colindex,
				fieldName : nodeInfo.id
			}).text(nodeInfo.name).css({
				'float' : 'left'
			});
			var tdIndex = '#gridTab tr:eq(1) td:eq(' + colindex + ')';
			var headtd = $(tdIndex);
			headtd.empty().append(appendDiv);
			var bindTd = $('#gridTab tr:eq(2) td:eq(' + colindex + ')');
			if(!nodeInfo.formula){
				bindTd.addClass('dragedTd');
			}
			var bindDiv = $('<div>').attr({
				rowindex : 2,
				colindex : colindex,
				fieldName : nodeInfo.id
			}).text(fieldText).css({
				'float' : 'left'
			});
			bindTd.attr({
				fieldName : nodeInfo.id
			}).css({position: 'relative'});
			this._bindselectAction(bindTd);
			bindTd.empty().append(bindDiv).append(
					this._createDelFieldButton(2, colindex, nodeInfo.id));
			var bindField = new BindField();
			var isContian=false;
            if(nodeInfo.formula){
            	bindField=this.getBindFieldByIndex(colIndexName);
            	if(bindField==null||bindField==undefined){
            		bindField = new BindField();	
            	}else{
            		isContian=true;
            	}
            }
			bindField.setoption('fieldName', nodeInfo.id);
			bindField.setoption('datasetId', nodeInfo.datasetId);
			bindField.setoption('title', nodeInfo.name);
			bindField.setoption('colIndexName', colIndexName);
			bindField.setoption('colIndex', colindex);
			bindField.setoption('dataType', nodeInfo.dataType);
			bindField.setoption('dataTypeString', dataType);
			bindField.setoption('formula', nodeInfo.formula);
			if(!isContian){
				this._addBindField(bindField);	
			}
			
		} else {// 如果是表尾区域则生成合计行
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '表尾不支持直接拖入，请点直接点击进行编辑。'
			});
		}

	},
	addFunctionBindField:function(expression,colIndex){
		// 校验公式的正确性并写入表格
		if (expression.trim()!=''&&expression.trim().startWith("=")) {// 公式
			var expressioni = expression.substring(1);
			var checkresult = this._checkExpression(expressioni,2);
			if (!checkresult.success) {
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : checkresult.mess
				});
			}
			var renderInfo={
					 id: expression,
					 dataType:2,
					 name:expression,
					 datasetId:customreport.datasetId,
					 formula:true
			 };
			 var indexNameTdI = '#gridTab tr:eq(1) td:eq(' + colIndex + ')';
			 var indexNameTd = $(indexNameTdI);
			 this.addBindField(renderInfo,indexNameTd);
		}else{
			this.deleteBindField(2,colIndex);
		}
	},
	/**
	 * 
	 */
	_bindselectAction : function(bindTd) {
		bindTd.bind('click', function() {
			$('#gridTab .selectedcondtion').removeClass('selectedcondtion');
			$(this).addClass('selectedcondtion');
			var fieldName = $(this).attr('fieldName');
			var bindField = customreport.getBindField(fieldName);
			$("#toolbar").AIPropertyToolbar('enable', 'data-format', bindField)
		});
	},

	_createDelFieldButton : function(rowindex, colindex, fieldName) {
		var button = $('<div class="fa fa-close close-icon-text"></div>').attr({
			rowindex : rowindex,
			colindex : colindex,
			fieldName : fieldName
		}).bind(
				'click',
				function() {
					customreport.deleteBindField($(this).attr('rowindex'), $(
							this).attr('colindex'), $(this).attr('fieldName'));
				});
		return button;

	},
	/**
	 * 添加绑定字段
	 */
	_addBindField : function(bindField) {
		this.bindfields.push(bindField);
	},
	deleteBindField : function(rowIndex, colIndex, fieldName) {
		var tdIndex = '#gridTab tr:eq(1) td:eq(' + colIndex + ')';
		var headtd = $(tdIndex);
		headtd.empty();
		var bindTd = $('#gridTab tr:eq(2) td:eq(' + colIndex + ')');
		bindTd.removeClass('selectedcondtion');
		bindTd.removeClass('dragedTd');
		bindTd.empty();
		bindTd.unbind('click');
		bindTd.css({position:'static'});
		
		this._deleteBindField(fieldName,colIndex);

	},
	editColHead : function(colIndex) {
		var divid = 'div_1_' + colIndex;
		var innerInput = $('<input>').val($('#' + divid).text()).attr({
			type : 'text'
		}).css({
			position : 'relative',
			top : '-15px',
			color:'#000'
		});
		var tdIndex = '#gridTab tr:eq(1) td:eq(' + colIndex + ')';
		var headtd = $(tdIndex);
		headtd.append(innerInput);
		innerInput.focus().blur(
				function() {
					if ($(this).val() != '') {
						$('#' + divid).text($(this).val());
					}
					var fieldName = $('#' + divid).attr('fieldName');
					customreport.setBindFieldProperty(fieldName, 'title', $(
							this).val());
					$(this).remove();
				});
	},
	editGroupField : function(colIndex, expression) {
		var indexNameTdI = '#gridTab tr:eq(0) th:eq(' + colIndex + ')';
		var indexNameTd = $(indexNameTdI);
		var colIndexName = indexNameTd.text();
		if (expression.trim() == '') {
			this._deleteGroup(colIndexName);
			return;
		}
		var groupTdIndex = '#gridTab tr:eq(3) td:eq(' + colIndex + ')';
		var groupTd = $(groupTdIndex);
		if (expression.trim()!=''&&expression.trim().startWith("=")) {// 公式
			var expressioni = expression.substring(1);
			var checkresult = this._checkExpression(expressioni,1);
			if (!checkresult.success) {
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : checkresult.mess
				});
			}
			var group = new GroupField();
			group.setoption('colIndex', colIndexName);
			group.setoption('isformula', true);
			this._addGroup(group);
			var groupField = this.getGroupField(colIndexName);
			groupField.setoption('expression', expression);
			groupField.setoption('isformula', true);
			groupField.setoption('dataTypeString', 'number');
			groupTd.attr({
				colIndex : colIndexName
			});
			this._createSelectGroupDiv(groupTd);
			var expdiv = $('<div>').attr({
				rowIndex : 3,
				colIndex : colIndex,
				id : 'div_3_' + colIndex
			}).text(expression);
			groupTd.empty().append(expdiv);
		} else if(expression.trim()!=''&&!expression.trim().startWith("=")) {// 常量
			var group = new GroupField();
			group.setoption('colIndex', colIndexName);
			group.setoption('isformula', false);
			this._addGroup(group);
			var groupField = this.getGroupField(colIndexName);
			groupField.setoption('expression', expression);
			groupField.setoption('isformula', false);
			groupField.setoption('dataTypeString', 'string');
			var tdIndex = '#gridTab tr:eq(3) td:eq(' + colIndex + ')';
			var headtd = $(tdIndex);
			var divid = 'div_3_' + colIndex;
			   headtd.empty();
			  headtd.append($('<div>').attr({
					'id' : 'div_3_' + colIndex,
					rowIndex : 3,
					'colIndex' : colIndex
				}).text(expression)).attr({'colIndex':colIndexName});
			
			this._createSelectGroupDiv(headtd);
		}else{// 认为没有公式了从公式列表删除
			this._deleteGroup(colIndexName);
		}
		console.log(expression);
	},

	_checkExpression : function(expression,parseType) {
		var returnvalue = {
			success : false,
			mess : '',
			parseResult:''
		};
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "checkExpression.jhtml",// 请求的action路径
			data : {
				expression : expression,
				bingfields : JSON.stringify(this.bindfields),
				parseType:parseType
			},
			error : function() {// 请求失败处理函数
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : '请求失败'
				});
			},
			success : function(data) { // 请求成功后处理函数。
				returnvalue.success = data.success;
				returnvalue.mess = data.mess;
				returnvalue.parseResult=data.parseResult
			}
		});
		return returnvalue;

	},
	_createSelectGroupDiv : function(grouptd) {
		grouptd.bind('click', function() {
			$('#gridTab .selectedcondtion').removeClass('selectedcondtion');
			$(this).addClass('selectedcondtion');
			var colIndex = $(this).attr('colIndex');
			var groupField = customreport.getGroupField(colIndex);
			$("#toolbar")
					.AIPropertyToolbar('enable', 'data-format', groupField)
		});

	},
	_deleteBindField : function(fieldname,colIndex) {
		for (i = 0; i < this.bindfields.length; i++) {
			if (this.bindfields[i].fieldName == fieldname) {
				this.bindfields.splice(i, 1);
			}
		}
		for (i = 0; i < this.bindfields.length; i++) {
			if (this.bindfields[i].colIndex == colIndex&&this.bindfields[i].formula) {
				this.bindfields.splice(i, 1);
			}
		}
	},
	/**
	 * 判断是否已经存在过滤条件
	 */
	isContainBindField : function(toAdd) {
		for (var i = 0; i < this.bindfields.length; i++) {
			var bindfield = this.bindfields[i];
			if (bindfield.fieldName == toAdd.id&&!toAdd.formula) {
				return true;
			}else if(bindfield.colIndexName != toAdd.colIndexName&&bindfield.fieldName == toAdd.id&&toAdd.formula){
				return true;
			}
		}
		return false;
	},
	/**
	 * 设置绑定字段属性
	 */
	setBindFieldProperty : function(fieldName, propertyName, propertyValue) {
		for (i in this.bindfields) {
			if (this.bindfields[i].fieldName == fieldName) {
				this.bindfields[i].setoption(propertyName, propertyValue);
			}
		}
	},

	deleteCondition : function(fieldname) {
		for (i = 0; i < this.conditions.length; i++) {
			if (this.conditions[i].fieldName == fieldname) {
				this.conditions.splice(i, 1);
			}
		}
		var renderdiv = $('#selarea');
		this.renderSelForm(renderdiv);
	},
	/**
	 * 添加查询条件配置
	 */
	_addReportCondition : function(reportCondition) {
		this.conditions.push(reportCondition);
	},
	_addGroup : function(groupField) {
		var iscontain = false;
		for (i = 0; i < this.groupfields.length; i++) {
			if (this.groupfields[i].colIndex == groupField.colIndex) {
				iscontain = true;
			}
		}
		if (!iscontain) {
			this.groupfields.push(groupField);
		}

	},
	_deleteGroup : function(colIndex) {
		var colNameIndex='A';
		var delIndex=0;
		for(i=0;i<26;i++){
			if(String.fromCharCode(colNameIndex.charCodeAt(0)*1+i)==colIndex){
				delIndex=i+1;
			}
		}
		
		var tdIndex = '#gridTab tr:eq(3) td:eq(' + delIndex + ')';
		var groupTd = $(tdIndex);
		groupTd.empty().removeClass('selectedcondtion');
		for (i = 0; i < this.groupfields.length; i++) {
			if (this.groupfields[i].colIndex == colIndex) {
				this.groupfields.splice(i, 1);
			}
		}
	},
	addCondition : function(draggedNode, conditiondiv) {
		var condition = new ReportCondition();
		condition.setoption('fieldName', draggedNode.id);
		condition.setoption('renderElementCode', draggedNode.bizType);
		if(draggedNode.bizType==''||draggedNode.bizType==null){
			condition.setoption('renderElementCode', $('#templateTextId').val());
		}
		condition.setoption('nodeInfo', draggedNode);
		condition.setoption('datasetId', draggedNode.datasetId);
		condition.setoption('required', true);
		if(draggedNode.name==null||draggedNode.name==''){
			condition.setoption('lable', draggedNode.id);
		}else{
			condition.setoption('lable', draggedNode.name);
		}
		this._addReportCondition(condition);
		this.renderSelForm(conditiondiv);
	},
	renderBindField : function() {

	},
	renderSelForm : function(conditiondiv) {
		var rowdiv = $('<div>').attr({
			'class' : 'row'
		});
		var bootstrapdiv = $('<div>').attr({
			'class' : 'col-md-12 col-sm-12 col-xs-12'
		});
		$(conditiondiv).empty();
		$(conditiondiv).append(rowdiv);
		rowdiv.append(bootstrapdiv);
		var formdiv = $('<div>').attr({
			'class' : 'form-horizontal form-label-left'
		})
		bootstrapdiv.append(formdiv);
		var formgroupdiv = $('<div>').attr({
			'class' : 'form-group'
		});
		for (i = 0; i < this.conditions.length; i++) {
			if (i % 3 == 0) {
				formgroupdiv = $('<div>').attr({
					'class' : 'form-group'
				});
				formdiv.append(formgroupdiv);
			}
			this.rendercomponent(this.conditions[i], formgroupdiv);
		}
	},
	/**
	 * 判断是否已经存在过滤条件
	 */
	isContainCondition : function(fieldName) {
		for (var i = 0; i < this.conditions.length; i++) {
			var condition = this.conditions[i];
			if (condition.fieldName == fieldName) {
				return true;
			}
		}
		return false;
	},
	rendercomponent : function(conditionInfo, conditiondiv) {
		var biztype = conditionInfo.renderElementCode;
		var formelement = null;
		if (biztype != null) {
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				url : "getFormElement.jhtml",// 请求的action路径
				data : {
					elementcode : biztype
				},
				error : function() {// 请求失败处理函数
				},
				success : function(data) { // 请求成功后处理函数。
					formelement = data;
				}
			});
		} else {
			formelement = {
				lable : conditionInfo.lable
			};
		}
		if (biztype != null &&formelement!=null&& formelement.formElementDef.type == 'tree') {
			this._rendertree(conditionInfo, formelement, conditiondiv);
			conditionInfo.commType='tree';
		} else if (biztype != null
				&&formelement!=null&& formelement.formElementDef.type == 'select') {
			this._renderselect(conditionInfo, formelement, conditiondiv);
			conditionInfo.commType='select';
		} else if (biztype != null &&formelement!=null&& formelement.formElementDef.type == 'date') {
			this._renderdate(conditionInfo, formelement, conditiondiv);
			conditionInfo.commType='date';
		}else if (biztype != null &&formelement!=null&& formelement.formElementDef.type == 'number') {
			this._rendertext(conditionInfo, formelement, conditiondiv);
			conditionInfo.commType='number';
		} 
		else {
			this._rendertext(conditionInfo, formelement, conditiondiv);
			conditionInfo.commType='text';
		}

	},
	_createDeleteButton : function(fieldname) {
		return $('<div class="fa fa-close close-icon"></div>').bind('click', function() {
			customreport.deleteCondition(fieldname);
		});
	},
	/**
	 * 渲染树组件
	 */
	_rendertree : function(conditionInfo, formelement, conditiondiv) {
		var lablediv = $('<label>').attr({
			'for' : conditionInfo.fieldName,
			'class' : 'control-label  col-md-3 col-sm-3 col-xs-3',
		}).text(conditionInfo.lable);
		var treediv = $('<div>').attr({
			id : conditionInfo.fieldName,
			'class' : ' col-md-8 col-sm-8 col-xs-8'
		}).css({
			float : 'left'
		});
		var contentdiv = $('<div>').attr({
			'class' : 'col-md-4 col-sm-8 col-xs-12 contentdiv'
		});

		var deletediv = $('<div>').attr({
			'class' : 'col-md-1 col-sm-1 col-xs-1'
		}).append(this._createDeleteButton(conditionInfo.fieldName));
		contentdiv.append(lablediv).append(treediv).append(deletediv);
		$(conditiondiv).append(contentdiv);
		this._addClickEvent(contentdiv, conditionInfo.fieldName);
		$('#' + conditionInfo.fieldName).asiainfoTree(
				{
					treeid : formelement.formElementDef.id,
					fieldname : conditionInfo.fieldName,
					treetype : '1',
					title : '请选' + formelement.lable,
					listeners : formelement.listeners == null ? []
							: formelement.listeners,
					filterfields : formelement.filterfields == null ? []
							: formelement.filterfields,
					filtervals : formelement.filtervals == null ? []
							: formelement.filtervals,
					required : true
				});

	},
	/**
	 * 渲染下拉框组件
	 */
	_renderselect : function(conditionInfo, formelement, conditiondiv) {
		var lablediv = $('<label>').attr({
			'for' : conditionInfo.fieldName,
			'class' : 'control-label col-md-3 col-sm-3 col-xs-3',
		}).text(conditionInfo.lable);

		var selectdiv = $('<div>').attr({
			id : 'div' + conditionInfo.fieldName,
			'class' : 'col-md-8 col-sm-8 col-xs-8'
		}).css({
			float : 'left'
		});
		var select = $('<select>').attr({
			id : conditionInfo.fieldName,
			name : conditionInfo.fieldName,

		}).css({
			'width' : '100%',
			'height' : '34px'
		});
		selectdiv.append(select);
		var contentdiv = $('<div>').attr({
			'class' : 'col-md-4 col-sm-8 col-xs-12 contentdiv'
		});
		var deletediv = $('<div>').attr({
			'class' : 'col-md-1 col-sm-1 col-xs-1'
		}).append(this._createDeleteButton(conditionInfo.fieldName));
		contentdiv.append(lablediv).append(selectdiv).append(deletediv);
		$(conditiondiv).append(contentdiv);
		this._addClickEvent(contentdiv, conditionInfo.fieldName);
		$('#' + conditionInfo.fieldName).asiainfoSelect(
				{
					id : '#' + conditionInfo.fieldName,
					element_id : formelement.formElementDef.id,
					listeners : formelement.listeners == null ? []
							: formelement.listeners,
					filterfields : formelement.filterfields == null ? []
							: formelement.filterfields,
					filtervals : formelement.filtervals == null ? []
							: formelement.filtervals,
					required : true
				});

	},
	/**
	 * 渲染日期组件
	 */
	_renderdate : function(conditionInfo, formelement, conditiondiv) {

		var lablediv = $('<label>').attr({
			'for' : conditionInfo.fieldName,
			'class' : 'control-label col-md-3 col-sm-3 col-xs-3',
		}).text(conditionInfo.lable);

		var datediv = $('<div>').attr({
			id : 'div' + conditionInfo.fieldName,
			'class' : 'col-md-8 col-sm-8 col-xs-8'
		}).css({
			float : 'left'
		});
		var select = $('<input>').attr({
			id : conditionInfo.fieldName,
			name : conditionInfo.fieldName,

		}).css({
			'width' : '100%',
			'height' : '34px'
		});
		datediv.append(select);

		var contentdiv = $('<div>').attr({
			'class' : 'col-md-4 col-sm-8 col-xs-12 contentdiv'
		});
		var deletediv = $('<div>').attr({
			'class' : 'col-md-1 col-sm-1 col-xs-1'
		}).append(this._createDeleteButton(conditionInfo.fieldName));
		contentdiv.append(lablediv).append(datediv).append(deletediv);
		$(conditiondiv).append(contentdiv);
		this._addClickEvent(contentdiv, conditionInfo.fieldName);
		$('#' + conditionInfo.fieldName).asiainfoDate(
				{
					id : '#' + conditionInfo.fieldName,
					element_id : formelement.formElementDef.id,
					listeners : formelement.listeners == null ? []
							: formelement.listeners,
					filterfields : formelement.filterfields == null ? []
							: formelement.filterfields,
					filtervals : formelement.filtervals == null ? []
							: formelement.filtervals
				});

	},
	_rendertext : function(conditionInfo, formelement, conditiondiv) {
		var lablediv = $('<label>').attr({
			'for' : conditionInfo.fieldName,
			'class' : 'control-label col-md-3 col-sm-3 col-xs-3',
		}).text(conditionInfo.lable);

		var textdiv = $('<div>').attr({
			id : 'div' + conditionInfo.fieldName,
			'class' : 'col-md-8 col-sm-8 col-xs-8'
		}).css({
			float : 'left'
		});
		var select = $('<input>').attr({
			id : conditionInfo.fieldName,
			name : conditionInfo.fieldName,

		}).css({
			'width' : '100%',
			'height' : '34px'
		});
		textdiv.append(select);
		var contentdiv = $('<div>').attr({
			'class' : 'col-md-4 col-sm-8 col-xs-12 contentdiv'
		});
		var deletediv = $('<div>').attr({
			'class' : 'col-md-1 col-sm-1 col-xs-1'
		}).append(this._createDeleteButton(conditionInfo.fieldName));
		contentdiv.append(lablediv).append(textdiv).append(deletediv);
		$(conditiondiv).append(contentdiv);
		this._addClickEvent(contentdiv, conditionInfo.fieldName);
	},
	_addClickEvent : function(contentdiv, fieldName) {
		contentdiv
				.bind(
						'click',
						function() {
							$('#selarea .selectedcondtion').removeClass(
									'selectedcondtion');
							$(this).addClass('selectedcondtion');
							customreport.setCurrentCondition(fieldName);
							for (i in customreport.conditions) {
								if (customreport.conditions[i].fieldName == fieldName) {
									var value = customreport.conditions[i].required == undefined ? false
											: customreport.conditions[i].required;
									// 获取 选中元素的值
									var isrequiredCheckbox = document
											.querySelector('#isrequired');
									if (value != isrequiredCheckbox.checked) {
										isrequiredCheckbox.click();
									}
									// 根据组件类型去组织操作符
									if(customreport.conditions[i].commType!=null&&customreport.conditions[i].commType!=undefined){
									  var options=new Array();
									  if(customreport.conditions[i].commType=='tree'){
										  options.push({value:'=',text:'='});
										  options.push({value:'%',text:'%'});
									  }else if(customreport.conditions[i].commType=='date'){
										  options.push({value:'=',text:'='});
										  options.push({value:'<=',text:'<='});
										  options.push({value:'>=',text:'>='});
										  options.push({value:'>',text:'>'});
										  options.push({value:'<',text:'<'});										  
									  }else if(customreport.conditions[i].commType=='select'){
										  options.push({value:'=',text:'='});
										  options.push({value:'%',text:'%'});
									  }else if(customreport.conditions[i].commType=='number'){
										  options.push({value:'=',text:'='});
										  options.push({value:'<=',text:'<='});
										  options.push({value:'>=',text:'>='});
										  options.push({value:'>',text:'>'});
										  options.push({value:'<',text:'<'});	
										  
									  }else{
										  options.push({value:'=',text:'='});
										  options.push({value:'%',text:'%'});  
									  }
									   $('#oper').empty();
									    for(k in options){
									    	$('#oper').append($('<option>').attr({value:options[k].value}).text(options[k].text));
									    }
									    if(customreport.conditions[i].oper==null||customreport.conditions[i].oper==undefined||customreport.conditions[i].oper==''){
									    	$('#oper').val('='); 	
									    }else{
									    	$('#oper').val(customreport.conditions[i].oper); 	
									    }
										
									}
									
									
								}
							}
						});

	},
	/**
	 * 配置查询条件属性
	 */
	setReportConditionProperty : function(fieldName, propertyName,
			propertyValue) {
		for (i in this.conditions) {
			if (this.conditions[i].fieldName == fieldName) {
				this.conditions[i].setoption(propertyName, propertyValue);
			}
		}
	},
	setBindFieldProperty : function(fieldName, propertyName, propertyValue) {
		for (i in this.bindfields) {
			if (this.bindfields[i].fieldName == fieldName) {
				this.bindfields[i].setoption(propertyName, propertyValue);
			}
		}
	},
	setGroupProperty : function(index, propertyName, propertyValue) {
		for (i in this.groupfields) {
			if (this.groupfields[i].colIndex == index) {
				this.groupfields[i].setoption(propertyName, propertyValue);
			}
		}
	},
	getGroupField : function(colIndex) {
		for (i in this.groupfields) {
			if (this.groupfields[i].colIndex == colIndex) {
				return this.groupfields[i];
			}
		}
	},
	getBindField : function(fieldName) {
		for (i in this.bindfields) {
			if (this.bindfields[i].fieldName == fieldName) {
				return this.bindfields[i];
			}
		}
	},
	getBindFieldByIndex : function(colIndexName) {
		for (j in this.bindfields) {
			if (this.bindfields[j].colIndexName == colIndexName) {
				return this.bindfields[j];
			}
		}
	},
	/**
	 * 设置表单id
	 */
	setTemplateId : function(templateid) {
		this.templateformid = templateid;
	},
	/**
	 * 是否分页
	 */
	setPaging : function(paging) {
		this.paging = paging;
	},
	setExp : function(exp) {
		this.exp = exp;
	},
	/**
	 * 设置功能列表
	 */
	setFunctionList : function(functionList) {
		functionList = functionList.split(',');
		for (i in functionList) {
			this.functions.push(functionList[i]);
		}
	},
	setCurrentCondition : function(fieldname) {
		this.currentCondFieldName = fieldname;
	},
	setCurrentColIndex : function(colIndex) {
		this.currentcolIndex = colIndex;
	},
	setDatasetId : function(datasetId) {
		$('#selarea').empty();
			for (var i = customreport.bindfields.length - 1; i >= 0
					&& customreport.bindfields.length > 0; i--) {
				customreport.deleteBindField(2,
						customreport.bindfields[i].colIndex,
						customreport.bindfields[i].fieldName);
			}
			for (var i = this.groupfields.length - 1; i >= 0
					&& this.groupfields.length > 0; i--) {
				this._deleteGroup(this.groupfields[i].colIndex);
			}
			this.bindfields = new Array()			
			this.groupfields = new Array()
			this.conditions= new Array()
		// }
		this.datasetId = datasetId;
	},
	createnew : function(){
		$('#datasettreeid').empty();
		this.setDatasetId("");
		this.reportId="";		
		this.formId='';
		this.gridId='';
		this.reportDatasetId='';
		this.isEditing=false;
		this.paging=true;
		var ispagingchexbox = document.querySelector('#ispaging');
        if (true != ispagingchexbox.checked) {
    	   ispagingchexbox.click();
        }
		this.exp=true;
		$('#reportName').text('新建报表');
		$('#reporttreeid').find('.tree-node').removeClass('tree-node-selected');
		var isexportbox = document.querySelector('#isexport');
        if (true != isexportbox.checked) {
        	isexportbox.click();
        }
	},
	presave:function(){
		
		
	},
	save:function(){
		
		var colNameIndex='A';
		var nameAndIndexMaps= new  Array();
		for(i=0;i<26;i++){
			var nameAndIndex={name:String.fromCharCode(colNameIndex.charCodeAt(0)*1+i),index:i+1};
			nameAndIndexMaps.push(nameAndIndex);
		}
		for(i=0; i<this.bindfields.length;i++){
			for(j=0;j<nameAndIndexMaps.length;j++){
				var filed=this.bindfields[i];
				var colIndex=nameAndIndexMaps[j];
				if(colIndex.name==filed.colIndexName){
					var tdIndex = '#gridTab tr:eq(2) td:eq(' + colIndex.index + ')';
					var td = $(tdIndex); 
					var tdWidth=td.innerWidth();
					filed.setoption('width',tdWidth) ;
				}
			}
		}
		for (i = 0; i < this.conditions.length; i++) {
			var conditioni = this.conditions[i];
			conditioni.setoption('order',i+1);
			delete conditioni.nodeInfo;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "save.jhtml",// 请求的action路径
			data : {
				reportMeta : JSON.stringify({
					bindfields : this.bindfields,
					groupfields : this.groupfields,
					paging : this.paging,
					conditions : this.conditions,
					formId : this.formId,
					gridId:this.gridId,
					datasetId : this.datasetId,
					reportDatasetId:this.reportDatasetId,
					reportId : this.reportId,
					reportName : this.reportName,
					category : this.category
				})
			},
			error : function() {// 请求失败处理函数
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : '请求失败'
				});
			},
			success : function(data) { // 请求成功后处理函数。
				BootstrapDialog.alert({
					title : '提示',
					draggable : true,
					message : data.mess
				});
				if(data.scuccess){
					customreport.reportId=data.reportId;
					customreport.isEditing = false;
					$('#reporttreeid').tree({
						url : "myreporttree.jhtml",
						loadFilter : function(treeData) {
							return treeData;
						},
						onLoadSuccess : function(Loddata){
							var node = $('#reporttreeid').tree('find', 'report_'+customreport.reportId);
							if(node!=null&&node!=undefined){
								$('#reporttreeid').tree('expandTo', node.target).tree('select', node.target);
							}							
						}
					});	
				}		
			}
		});
		
	},
	dosave : function() {
		if(this.isEditing){// 如果是编辑状态则直接保存
			this.save();
		}else{
			BootstrapDialog.show({
				title : '请填写报表分类和报表名称！',
				draggable : true,
				cssClass : 'dialogcss',
				message : $('<div></div>').load('fillrptcategory.jhtml'),
				/*
				 * onshown : function() { $('#rptcategory').asiainfoSelect({ id :
				 * '#rptcategory', element_id : '20160909', datadefault : { "id" :
				 * "defaulttype", "text" : "请选择" } }); },
				 */
				buttons : [{
								label : '确定',
								cssClass : "m-b0 btn-orange", 
								action : function(dialogRef) {
									if($('#rptcategory').val()=="")  { 
										BootstrapDialog.show({
											title:"提示",
											message:"请选择分类名称！"
										});
										return
									}
									if($('#rptname').val()=="")  { 
										BootstrapDialog.show({
											title:"提示",
											message:"请填写报表名称！"
										});
										return;
									}
									customreport.category=$('#rptcategory').val();
									customreport.reportName=$('#rptname').val();
									customreport.save();
									dialogRef.close();
								}
							}, 
							{
								label : '取消',
								action : function(dialogRef) {
									dialogRef.close();
							}
					  }]
			})
			
		}
	},
	deleteall : function() {
		if (!$('#reporttreeid').tree('getSelected')
				) {
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '请选择删除的分类或者报表节点！'
			});
			
			return;
		}
		if ($('#reporttreeid').tree('getSelected').attributes.id == "defaulttype") {
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '默认分类节点不能删除！'
			});
			return;
		}
		if ($('#reporttreeid').tree('getSelected').attributes.categoryNode
				|| $('#reporttreeid').tree('getSelected').attributes.isLeaf) {
			var categoryorrpt = $('#reporttreeid').tree('getSelected').attributes.isLeaf;
			var id = $('#reporttreeid').tree('getSelected').attributes.id;
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : 'json',
				url : "deleteall.jhtml",// 请求的action路径
				data : {
					categoryorrpt : categoryorrpt,
					id : id
				},
				error : function(data) {// 请求失败处理函数
					BootstrapDialog.alert({
						title : '提示',
						draggable : true,
						message : data.mess
					});
				},
				success : function(data) { // 请求成功后处理函数。
					BootstrapDialog.alert({
						title : '提示',
						draggable : true,
						message : data.mess
					});
					if (data.success) {
						$('#reporttreeid').tree({
							url : "myreporttree.jhtml",
							loadFilter : function(data) {
								return data;
							}
						});
					}
				}
			});
		}
		;
	},
	preview : function(){
		if(this.reportId==''){
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '没有可预览的报表'
			});
			return;
		}
		// window.open(window.contaxtrot+'/report/'+this.reportId+'.jhtml');
		if(window.systemType == 'portal'){
			var title = this.reportName;
    		var url = window.contaxtrot+'/report/'+$('#reporttreeid').tree('getSelected').attributes.id+'.jhtml';
    		parent.parent.addNavFrame('contentframe',url,title);	
		}else{
			window.open(window.contaxtrot+'/report/'+$('#reporttreeid').tree('getSelected').attributes.id+'.jhtml');	
		}
	},
	open : function(){
		if(!this.isEditing){
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '请保存报表！再打开报表。'
			});
			return ;
		}
		if($('#reporttreeid').tree('getSelected')){
			if(!$('#reporttreeid').tree('getSelected').attributes.isLeaf){
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '请选择打开的报表！'
			});
			return;
			}
		}else{
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '请选择打开的报表！'
			});
			return;
		}
		if(window.systemType == 'portal'){
			var title = this.reportName;
    		var url = window.contaxtrot+'/report/'+$('#reporttreeid').tree('getSelected').attributes.id+'.jhtml';
    		parent.parent.addNavFrame('contentframe',url,title);	
		}else{
			window.open(window.contaxtrot+'/report/'+$('#reporttreeid').tree('getSelected').attributes.id+'.jhtml');	
		}
		
		
	},
	viewReport : function(reportData) {
		var cons=new Array();
	    for(i=0;i< reportData.conditions.length;i++){
	    	var condition=new ReportCondition();
	    	for(j in reportData.conditions[i]){
	    		condition[j]=reportData.conditions[i][j];
	    	}
	    	cons.push(condition);
	    }
	    this.conditions = cons;
		this.paging = reportData.paging;
		this.exp = reportData.exp;
		this.datasetId = reportData.datasetId;
		this.exp = reportData.exp;
		this.reportId = reportData.reportId;
		this.reportName = reportData.reportName;
		$('#reportName').text(this.reportName);
		this.category = reportData.category;
		this.isEditing = true;
		this.reportId=reportData.reportId;
		this.reportDatasetId=reportData.reportDatasetId;
		//
		var ispagingchexbox = document.querySelector('#ispaging');
        if (this.paging != ispagingchexbox.checked) {
    	   ispagingchexbox.click();
        }
		this.exp=true;
		var isexportbox = document.querySelector('#isexport');
        if (this.exp != isexportbox.checked) {
        	isexportbox.click();
        }
		//
		// 渲染表单
		this.renderSelForm($('#selarea'));
		var nameAndIndexMaps= new  Array();
		var colNameIndex='A';
		for(i=0;i<26;i++){
			var nameAndIndex={name:String.fromCharCode(colNameIndex.charCodeAt(0)*1+i),index:i+1};
			nameAndIndexMaps.push(nameAndIndex);
		}
		for (i = 0; i < reportData.bindfields.length; i++) {
			var renderInfo = {
				id : reportData.bindfields[i].fieldName,
				name : reportData.bindfields[i].title,
				datasetId : reportData.bindfields[i].datasetId,
				dataType:reportData.bindfields[i].dataType,
				formula:reportData.bindfields[i].formula,
			};
			var tdIndex=i+1;
			for(j=0;j<nameAndIndexMaps.length;j++){
				if(nameAndIndexMaps[j].name==reportData.bindfields[i].colIndexName){
					tdIndex=nameAndIndexMaps[j].index;
					break;
				}
			}
			var selectid = '#gridTab tr:eq(2) td:eq(' + tdIndex + ')'
			this.addBindField(renderInfo, $(selectid));
		}
		var bindFileds=new Array();
		for(i = 0; i < reportData.bindfields.length; i++){
			var bindFiled=new BindField();
			for(j in reportData.bindfields[i]){
				bindFiled[j]=reportData.bindfields[i][j];
			}
			bindFileds.push(bindFiled);
		}
		this.bindfields=bindFileds;
		for (i = 0; i < reportData.groupfields.length; i++) {
			var tdIndex=i+1;
			for(j=0;j<nameAndIndexMaps.length;j++){
				if(nameAndIndexMaps[j].name==reportData.groupfields[i].colIndex){
					tdIndex=nameAndIndexMaps[j].index;
					break;
				}
			}
			if(!reportData.groupfields[i].isformula){
				this.editGroupField(tdIndex,reportData.groupfields[i].expression);
			}else{
				this.editGroupField(tdIndex,reportData.groupfields[i].expression);
			}
			
		}
		var groupFileds=new Array();
		for(i = 0; i < reportData.groupfields.length; i++){
			var groupFiled=new GroupField();
			for(j in reportData.groupfields[i]){
				groupFiled[j]=reportData.groupfields[i][j];
			}
			groupFileds.push(groupFiled);
		}
		this.groupfields=groupFileds;
		// 拷贝
	}
};
/**
 * 绑定字段数据结构
 */
function BindField() {
	var datasetId = '';
	var fieldName = '';
	var format = '';
	var align = '';
	var showOrder = '';
	var title = '';
	var width = '';
	var colIndex = '';
	var colIndexName = '';
	var background = '';
	var style = '';
	var dataType='';
	var formula=true;

};

BindField.prototype.datasetId = '';
BindField.prototype.fieldName = '';
BindField.prototype.formater = '';
BindField.prototype.align = '';
BindField.prototype.showOrder = '';
BindField.prototype.title = '';
BindField.prototype.width = '';
BindField.prototype.colIndex = '';
BindField.prototype.colIndexName = '';
BindField.prototype.background = '';
BindField.prototype.style = '';
BindField.prototype.dataType = '';
BindField.prototype.dataTypeString = '';
BindField.prototype.formula = true;
BindField.prototype.setoption = function(optionname, optionvalue) {
	this[optionname] = optionvalue;
};

function GroupField() {
	var expression = '';
	var colIndex = '';
	var format = '';
	var align = '';
	var isformula = false;
	var background = '';
	var style = '';
};
GroupField.prototype.expression = '';
GroupField.prototype.colIndex = '';
GroupField.prototype.format = '';
GroupField.prototype.align = '';
GroupField.prototype.isformula = false;
GroupField.prototype.background = '';
GroupField.prototype.style = '';
GroupField.prototype.dataTypeString = '';
GroupField.prototype.setoption = function(optionname, optionvalue) {
	this[optionname] = optionvalue;
};
/**
 * 查询条件定义
 */
function ReportCondition() {
	var datasetId = '';
	var fieldName = '';
	var renderElementCode = '';
	var required = false;
	var nodeInfo = '';
	var order = '';
	var lable = '';
	var oper='=';
	var commType=''
};
ReportCondition.prototype.datasetId = '';
ReportCondition.prototype.fieldName = '';
ReportCondition.prototype.renderElementCode = '';
ReportCondition.prototype.required = '';
ReportCondition.prototype.nodeInfo = '';
ReportCondition.prototype.order = '';
ReportCondition.prototype.lable = '';
ReportCondition.prototype.oper = '=';
ReportCondition.prototype.commType = 'text';
ReportCondition.prototype.setoption = function(optionname, optionvalue) {
	this[optionname] = optionvalue;
};
function collapseAll() {
	$('#datasetsel').treegrid('collapseAll');
}
function query() {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : "datasettree.jhtml",// 请求的action路径
		data : {
			category : $('#typeseldataset').val(),
			name : $('#name').val()
		},
		error : function() {// 请求失败处理函数
			BootstrapDialog.alert({
				title : '提示',
				draggable : true,
				message : '请求失败'
			});
		},
		success : function(data) { // 请求成功后处理函数。
			$('#datasetsel').treegrid('loadData', data);
		}
	});

}


