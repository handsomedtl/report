var asiainfoTree = $
		.widget(
				"ui.asiainfoTree",
				{
					version : "1.0.0-beta.1",
					defaultElement : "<div>",
					delay : 300,
					options : {
						/**
						 * 组件id
						 */
						treeid : 'tree1view',
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
						multiSelect : false,
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
						serverurl : window.contaxtrot + '/tree/treeData.jhtml',
						defaultvalue : {
							text : '',
							value : ''
						},
						required : false,
						readonly : false,
						tooltipmess : '',
						messcontainer : '.errormessage'
					},
					/**
					 * 构造方法
					 */
					_create : function() {
						var $this = this;
						this.container;
						// 构造popuptree 界面
						this.element.css({
							position : 'relative'
						});
						var inputdiv = $('<div>').addClass('popuptree')
						var popup_filed = $('<input>').attr({
							type : 'text',
							name : this.options.fieldname + "_pop",
							id : this.options.fieldname + "_pop",
							readonly : 'readonly'
						}).css({
							float : 'left',
							width : '90%'
						}).val(this.options.defaultvalue.text).addClass(
								'form-control col-md-11');
						if (this.options.required) {
							popup_filed.attr({
								required : 'required'
							});
						}

						popup_filed
								.attr({
									'data-parsley-errors-container' : this.options.messcontainer
								});

						if (this.options.tooltipmess != '') {
							popup_filed
									.attr({
										'data-parsley-error-message' : this.options.tooltipmess
									});
						}

						var hiddenfield = $('<input>').attr({
							type : 'hidden',
							name : this.options.fieldname,
							id : this.options.fieldname
						}).val(this.options.defaultvalue.value);
						inputdiv.append(hiddenfield);
						inputdiv.append(popup_filed);
						var serveraddr = $this.options.serverurl;
						serveraddr = serveraddr.split("/")[0];
						var zoomimg = $('<div>').attr({
							'class' : 'direarr',
							'id' : $this.options.fieldname + '_img'
						});
						if (!this.options.readonly) {
							zoomimg.bind('click', function() {
								if (!$this.options.showtree) {
									$this._showtree();
									$this.options.showtree = true;
								} else {
									$this._hidetree();
									$this.options.showtree = false;
								}
							});
						}
						var imgdiv = $('<div>').css({
							float : 'left',
							width : '10%',
							height : '16px'
						});
						imgdiv.append(zoomimg);
						inputdiv.append(imgdiv)
						this.element.append(inputdiv);
						var treecontainerdiv = $('<div>').attr('id',
								this.options.fieldname + "_treecontainer")
								.addClass('asiainfoTree');
						var treediv = $('<div>').attr('id',
								this.options.fieldname + "_tree").css({
							'z-index' : 1000,
							display : 'block',
							'overflow-y' : 'scroll',
							'max-height' : '315px'
						});
						//
						treecontainerdiv.append($('<div>').text(
								this.options.title).addClass('title'));
						treecontainerdiv.append(treediv);
						var buttondiv = $('<div>').attr('id',
								this.options.fieldname + "_button").addClass(
								'zoombuton');
						var okbutton = $('<input>')
								.attr({
									type : 'button',
									name : 'treeok',
									value : '确定'
								})
								.addClass('btn btn-success btn-xs')
								.bind(
										'click',
										function() {
											$this._hidetree();
											var selectnodes = $(
													'#'
															+ $this.options.fieldname
															+ "_tree").jstree(
													"get_selected");
											//
											var jsondata = $(
													'#'
															+ $this.options.fieldname
															+ "_tree").jstree(
													"get_json", selectnodes[0]);
											if (jsondata.data != undefined
													&& jsondata.data.haschild
													&& $this.options.selectLeaf) {
												$this._setValue('', '');
												alert('请选择叶子节点。');
												return;
											}
											var selectedtext = "[";
											var selectedvalue = "";
											for (var i=0 ;i< selectnodes.length;i++) {
												var text = $(
														"#"
																+ $this.options.fieldname
																+ "_tree")
														.jstree("get_text",
																selectnodes[i]);
												if ($this.options.multiSelect) {
													selectedvalue = selectedvalue
															+ selectnodes[i]
															+ ",";
													selectedtext = selectedtext
															+ text + ",";
												} else {
													selectedvalue = selectnodes[i];
													selectedtext = selectedtext
															+ text + ",";
												}
											}
											if (selectedtext.length > 1) {
												selectedtext = selectedtext
														.substring(
																0,
																selectedtext.length - 1);
											}
											if ($this.options.multiSelect
													&& selectedvalue.length > 0) {
												selectedvalue = selectedvalue
														.substring(
																0,
																selectedvalue.length - 1);
											}
											selectedtext = selectedtext + "]";
											$this._setValue(selectedvalue,
													selectedtext);
											// 调用回调函数
											if ($this.options.multiSelect
													&& $this.options.afterselect != null
													&& $this.options.afterselect != undefined) {
												$this.options.afterselect.call(
														window, jsondata);
											}

										});
						buttondiv.append(okbutton);
						var clearbutton = $('<input>').attr({
							type : 'button',
							name : 'clear',
							value : '清除'
						}).bind('click', function() {
							$this._clearselection();
							$this._hidetree()
						}).addClass('btn btn-info btn-xs');
						buttondiv.append(clearbutton);
						treecontainerdiv.append(buttondiv);
						this.element.append(treecontainerdiv);
						// 内部初始化jstree
						var plugins = [];
						if (this.options.multiSelect) {
							plugins = [ "wholerow", "checkbox" ];
						}
						$('#' + this.options.fieldname + "_tree")
								.jstree(
										{
											"core" : {
												"animation" : 0,
												"check_callback" : true,
												"themes" : {
													"stripes" : true,
													'name' : 'proton',
													'responsive' : true
												},
												'data' : {
													'url' : $this.options.serverurl,
													'data' : function(node) {
														var params = {
															'id' : node.id,
															'treeType' : $this.options.treetype,
															'treeid' : $this.options.treeid,
															'selectedvalues' : $this.options.defaultvalue.value
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
													&& !data.node.state.loaded) {
												data.instance
														.open_node(data.node.id);
											}
											if (data.node.data != null
													&& data.node.data.haschild
													&& $this.options.selectLeaf) {
												$this._setValue('', '');
												alert('请选择末级节点。');
												return;
											}
											var selectednodes = data.instance
													.get_selected();
											var selectedtext = "[";
											var selectedvalue = "";
											for (var i=0;i<selectednodes.length;i++ ) {
												var text = data.instance
														.get_text(selectednodes[i]);
												selectedvalue = selectednodes[i];
												selectedtext = selectedtext
														+ text + ",";
											}
											//
											if (selectedtext.length > 1) {
												selectedtext = selectedtext
														.substring(
																0,
																selectedtext.length - 1);
											}
											if ($this.options.multiSelect
													&& selectedvalue.length > 0) {
												selectedvalue = selectedvalue
														.substring(
																0,
																selectedvalue.length - 1);
											}
											selectedtext = selectedtext + "]";
											if (!$this.options.multiSelect) {
												$this._setValue(selectedvalue,
														selectedtext);
											}
											if (!$this.options.multiSelect
													&& $this.options.afterselect != null
													&& $this.options.afterselect != undefined) {
												$this.options.afterselect.call(
														window, data.node);
											}

										})
								.on("dblclick.jstree", function(e, data) {
									// 先不做实现
								})
								.on(
										"loaded.jstree",
										function(e, treedata) {
											var selectedvalue = $this.options.defaultvalue;
											if (selectedvalue.value != null) {
												$
														.ajax({
															url : window.contaxtrot
																	+ "/tree/getallselectedpaths.jhtml",
															dataType : "json",
															data : {
																'treeType' : $this.options.treetype,
																'treeid' : $this.options.treeid,
																'selectedvalues' : selectedvalue.value
															},
															type : "post",
															success : function(
																	data) {

																for (var i = 0; i < data.length; i++) {
																	var path = data[i];
																	var nodes = new Array();
																	for (var j = 0; j < path.length; j++) {
																		var nodeid = path[j];
																		if (nodeid == null) {
																			continue;
																		}
																		nodes
																				.push(nodeid);
																	}

																	treedata.instance
																			.open_nodes_step_by_step(nodes);
																	/*
																	 * $( "#" +
																	 * $this.options.fieldname +
																	 * "_tree")
																	 * .jstree(
																	 * "open_nodes_step_by_step",
																	 * nodes);
																	 */
																}

															}
														});

											}

										}).on("load_node.jstree",
										function(e, data) {
										});
						// 添加2个自定义方法把代码合并到jstree 当中
						$
								.extend(
										$(
												'#' + $this.options.fieldname
														+ "_tree").jstree(),
										{
											refreshdata : function() {
												this.refresh();
											},
											fireListener : function() {
												for (i in this.settings.listeners) {
													//alert(this.settings.listeners[i]);
												}
											},
											open_nodes_step_by_step : function(
													remaining_nodes) {
												var nodes = remaining_nodes
														.slice();
												if (nodes.length > 1) {
													var nodes_left = remaining_nodes
															.slice();
													nodes_left.pop();
													var to_open = nodes.length - 1;
													this
															.open_node(
																	nodes[to_open],
																	function() {
																		this
																				.open_nodes_step_by_step(nodes_left);
																	});
												} else {
													this.select_node(nodes[0]);
												}
											}
										});

					},

					/**
					 * 获取当前弹出树值
					 */
					getValue : function() {
						// return $(this.options.id).jstree();
					},
					/**
					 * 显示弹出树同时加鼠标点击事件
					 */
					_showtree : function() {
						this.options.showtree = true;
						$('#' + this.options.fieldname + "_treecontainer")
								.show();
						this
								._on(
										document,
										{
											"click" : function(event) {
												$('#' + this.options.fieldname
														+ "_tree");
												var targetid = event.target.id;
												var targets = new Array();

												targets = this
														._sameorginal(event.target);
												sameorginal = targets;
												if (!sameorginal) {
													if (event.target.className != null
															&& event.target.className
																	.indexOf("jstree") >= 0) {
														sameorginal = true;
													}
												}
												if (!sameorginal
														&& this.options.showtree
														&& event.target.id != this.options.fieldname
																+ '_img') {
													this._hidetree();
												}
											}
										});

					},
					/**
					 * 隐藏弹出树
					 */
					_hidetree : function() {
						$('#' + this.options.fieldname + "_treecontainer")
								.hide();
						this.options.showtree = false;
					},
					/**
					 * 由别的组件调用刷新树组件
					 */
					refreshdata : function() {
						// this.options.filtervals = this._getParams();
						this.options.filtervals = $
								.parseJSON(this._getParams())
						$("#" + this.options.fieldname + "_tree").jstree(
								"refreshdata");

					},
					_getParams : function() {
						var list = this.options.filterfields;
						var rs = '{';
						var size = list.length - 1;
						for (var i=0;i<list.length;i++ ) {
							rs += '"' + list[i].sqlfieldname;
							rs += '":"';
							rs += $(list[i].filedname).val() + '"';
							if (size != i) {
								rs += ',';
							}
						}
						rs += '}';
						console.log(rs);
						return rs;
					},
					/**
					 * 当值发生变化时候出发其他组件
					 */
					_fireListener : function() {
						for (i in this.options.listeners) {
							var id = this.options.listeners[i].selector;
							var type = this.options.listeners[i].type;
							var pluginName = "asiainfo"
									+ type.replace(/(\w)/, function(v) {
										return v.toUpperCase()
									});
							var func = "$('" + id + "')." + pluginName
									+ "(\"refreshdata\")";
							eval(func);
						}
					},
					_setValue : function(selectedvalue, selectedtext) {
						$('#' + this.options.fieldname + "_pop").val(
								selectedtext);
						$('#' + this.options.fieldname).val(selectedvalue);
						// 出发其他组件
						this._fireListener();

					},
					/**
					 * 清除选择
					 */
					_clearselection : function() {
						this._setValue('', '');
					},
					/**
					 * 判断事件是否从同一div出现
					 */
					_sameorginal : function(object) {
						// result.push($(object).parent());
						var parentelement = $(object).parent().hasClass(
								"asiainfoTree");
						if (parentelement == true) {
							return true;
						}
						if ($(object).parent()[0].nodeType == 1
								|| $(object).parent()[0] == undefined) {
							return false;
						} else {
							return this._sameorginal($(object).parent());

						}
					}
				});
