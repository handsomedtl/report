var widgetsMenu = $.widget("ui.asiainfoInnerTree", {
	version : "1.0.0-beta.1",
	delay : 300,
	options : {
		/**
		 * 组件id
		 */
		treeid : '10000002',
		/**
		 * 对应输入域的名称
		 */
		fieldname : 'treevalue',
		/**
		 * 放大镜图片的位置
		 */
		zoomimgsrc : '/resources/images/icon/direarr.jpg',
		/**
		 * 是否多选
		 */
		multiSelect : true,
		/**
		 * 必须选择叶子节点
		 */
		selectLeaf : false,
		/**
		 * 显示树
		 */
		showtree : false,
		/**
		 * 树类型
		 */
		treetype : 1,
		/**
		 * 弹出对话框标题
		 */
		title : '请选择部门',
		/**
		 * 监听字段的其他字段列表
		 */
		listeners : [],
		/**
		 * 过滤字段
		 */
		filterfields : [],
		/**
		 * 初始化过滤参数和值列表
		 */
		filtervals : [],
		/**
		 * 获取树节点的服务地址
		 */
		serverurl : window.contaxtrot+'/tree/treeData.jhtml',
		defaultvalue : {
			text : '',
			value : ''
		},
		required : false,
		readonly : false,
		tooltipmess : '',
		messcontainer : '.errormessage',
		defaultWidth : 'auto',
		defaultHeight : 'auto',
		afterselect : function(value){$("#selectedDepart").val(value)}
	},
	_create : function() {
		this._setStyle();
		var $this = this;
		var plugins = [];
		if (this.options.multiSelect) {
			plugins = [ "wholerow", "checkbox" ];
		}
		this.element
		.jstree(
				{
					"core" : {
						"animation" : 0,
						"check_callback" : true,
						"themes" : {
							"stripes" : true
						},
						'data' : {
							'url' : $this.options.serverurl,
							'data' : function(node) {
								var params = {
									'id' : node.id,
									'treeType' : $this.options.treetype,
									'treeid' : $this.options.treeid
								};
								$
										.extend(
												params,
												$this.options.filtervals);
								return params;
							}
						}
					},

					'plugins' : plugins,
					"types" : {
						"#" : {
							// "max_children" : 1,
							"max_depth" : 6,
							"valid_children" : [ "root" ]
						},
						"root" : {
							"icon" : "/static/3.3.0/assets/images/tree_icon.png",
							"valid_children" : [ "default" ]
						},
						"default" : {
							"valid_children" : [
									"default", "file" ]
						},
						"file" : {
							"icon" : "glyphicon glyphicon-file",
							"valid_children" : []
						}
					},
					'listeners' : [ 'input1', 'input2',
							'input3' ]
				})
		.on(
				'changed.jstree',
				function(e, data) {
					// 如果还没打开则打开
					if (data.node != null
							&& data.node != undefined
							) {
						$this.element
								.jstree("open_node",
										data.node.id);
					}
					alert("当前点击的节点值为："+data.node.id);
					//获取所有选中值
					var seletedJSON = $this._getSelected();
					alert("所有选中的节点值为："+seletedJSON["value"]);
					// 调用回调函数
					if ($this.options.multiSelect
							&& $this.options.afterselect != null
							&& $this.options.afterselect != undefined) {
						$this.options.afterselect.call(window,seletedJSON["text"]);
					}

				}).on("dblclick.jstree",
				function(e, data) {
					// 先不做实现
				});
	},
	_setStyle : function(){
		this.element.css({
			"width":this.options.defaultWidth,
			"height":this.options.defaultHeight,
			"min-height" : "600px",
			"overflow" : "scroll"
		});
	},
	_getSelected : function(){
		var selectnodes = this.element.jstree(
				"get_selected");
		var selectedtext = "[";
		var selectedvalue = "";
		for (i in selectnodes) {
			var text = this.element.jstree(
					"get_text", selectnodes[i]);
			if (this.options.multiSelect) {
				selectedvalue = selectedvalue + selectnodes[i] + ",";
				selectedtext = selectedtext + text + ",";
			} else {
				selectedvalue = selectnodes[i];
				selectedtext = selectedtext + text + ",";
			}
		}
		if (selectedtext.length > 1) {
			selectedtext = selectedtext.substring(0, selectedtext.length - 1);
		}
		if (this.options.multiSelect && selectedvalue.length > 0) {
			selectedvalue = selectedvalue
					.substring(0, selectedvalue.length - 1);
		}
		selectedtext = selectedtext + "]";
		var selectedJSON = {"value" : selectedvalue,"text" : selectedtext};
		return selectedJSON;
	}
})