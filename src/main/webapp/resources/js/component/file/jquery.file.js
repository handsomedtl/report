var widgetFile = $
		.widget(
				"ui.asiainfoFile",
				{
					version : "1.0.0-beta.1",
					defaultElement : "<div>",
					delay : 300,
					options : {
						id : '#test1',
						name : 'test1',
						filename : 'file', // 上传文件时的名字
						element_id : '12345679',
						listeners : [],// 当自己改变时，要通知刷新的列表
						filterfields : [], // 当自己刷新时，要传入的参数
											// {selector:'#test1',type:'select'}
						url : window.contaxtrot + '/file/upload.jhtml',
						swf : window.contaxtrot
								+ '/resources/js/uploadify/uploadify.swf',
						button_image_url : window.contaxtrot
								+ '/resources/js/uploadify/img/kong.png',
						down_url : window.contaxtrot
								+ '/file/downAttached.jhtml',
						baseurl : '',
						b_width : 90,
						b_height : 30,
						h_text_width : '90%',
						h_btn_name : '附件列表',
						pop_btn_name : '上传附件',
						h_text_value : '', // 页面显示附件列表
						h_value : '',// 页面传值
						default_value : '',
						required : false,
						readonly : false,
						readonly_upload : true,// 隐藏
						readonly_cal : true,// 删除按钮
						upload_div_id : '',
						customButton : false,
						popButton : null,
						tooltipmess : '',
						messcontainer : '.errormessage',
						fileSizeLimit:'10484560B',//文件大小限制
						fileTypeExts : '*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.txt;*.zip;*.rar;*.gz;*.bz2;*.gif;*.jpg;*.jpeg;*.png;*.bmp' // '*.doc;*.pdf;*.rar'
					},

					_create : function() {
						// 初始化参数
						if (this.options.default_value != undefined
								&& this.options.default_value != '') {
							var y_value = this.options.default_value;
							var list = y_value.split('|');
							var onelist;
							var y_name = '';
							var t = '';
							for (var i = 0; i < list.length; i++) {
								t = list[i];
								onelist = t.split(':');
								y_name += onelist[1];
								if (i != list.length - 1) {
									y_name += ';'
								}
							}
							this.options.h_text_value = y_name;
							this.options.h_value = y_value;
						}
						// 画页面
						this._initHtml();
						if (this.options.readonly == true) {
							// $('#'+this.options.pop_file_id).uploadify('disable',
							// true);
							if (this.options.readonly_upload) {
								$('#' + this.options.pop_file_id).hide();
								$(this.options.upload_div_id).hide();
							}
							if (this.options.readonly_cal) {
								$('.pop_li_cal').hide();
							}
						}

					},
					_initHtml : function() {
						//this.element.addClass("input-group");
						// 添加html内容
						var h_text_id = this.options.name + '_h_text';
						this.options.h_text_id = h_text_id;
						var h_text = $(
								'<input type="text" id="' + h_text_id
										+ '" readonly="readonly" value="'
										+ this.options.h_text_value + '"/>')
								.addClass('form-control');
						if (this.options.required) {
							h_text.attr({
								required : 'required'
							});
						}
						//
						h_text
								.attr({
									'data-parsley-errors-container' : this.options.messcontainer
								});
						if (this.options.tooltipmess != '') {
							h_text
									.attr({
										'data-parsley-error-message' : this.options.tooltipmess
									});
						}
						//
						var h_value_id = this.options.name + '_h_value';
						this.options.h_value_id = h_value_id;
						var h_value = $('<input type="hidden" id="'
								+ h_value_id + '" name="' + this.options.name
								+ '" value="' + this.options.h_value + '" />');
						var h_btn_id = this.options.name + '_h_btn';
						this.options.h_btn_id = h_btn_id;

						var h_span = $('<span class="input-group-btn"></span>')
						var h_btn = $(
								'<input type="button" id="' + h_btn_id
										+ '" value="' + this.options.h_btn_name
										+ '"/>').addClass('btn btn-primary');
						h_span.append(h_btn);
						// 添加num序列
						var index_num_id = 'index_num_' + this.options.name;
						this.options.index_num_id = index_num_id;
						var index_num = $('<input type="hidden" id="'
								+ index_num_id + '" name="index_num_'
								+ this.options.name + '"  value="0" />');
						// this.options.tr_num_id='tr_num_'+this.options.name;
						var grouo_div=$('<div class="input-group"></div>')
						grouo_div.append(h_text).append(h_span)
						$(this.options.id).append(grouo_div)
								.append(h_value).append(index_num);

						// 添加弹出框
						var pop_div_id = this.options.name + '_pop_div';
						var pop_div = $('<div id="' + pop_div_id + '"></div>');
						pop_div.css({
							padding : '5px',
							overflow : 'scroll',
							position : 'absolute',
							left : '-10px',
							top : '10px',
							display : ' none',
							margin : '10px 20px',
							width : '350px',
							height : '400px',
							border : 'medium none',
							'background-color' : '#fff',
							'border-radius' : ' 2px',
							'border-top' : '1px solid #999999',
							'display' : 'none',
							'border-left' : 'solid 1px #fec899',
							'border-right' : 'solid 1px #fec899',
							'border-bottom' : 'solid 1px #fec899',
							'background-color' : 'rgb(255, 255, 255)',
							'border-radius' : '2px',
							'z-index' : 500

						});

						var pop_file_id = this.options.name + '_pop_file';
						this.options.pop_file_id = pop_file_id;

						// 上传按钮
						var upload_div_id = this.options.name + '_div_id';
						this.options.upload_div_id = upload_div_id;
						var upload_div = $(
								'<div id=' + upload_div_id + ' ></div>');
						var pop_file = $('<input type="file" id="'
								+ pop_file_id + '"name="'
								+ this.options.filename + '">');
						upload_div.append(pop_file);
						// 清空浮动
						var clear_float = $('<div style="clear:both"></div>');
						var pop_list_div = $('<div><div>')
								.addClass(
										'col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix');
						// 添加table
						var pop_ul_id = this.options.name + '_pop_ul';
						this.options.pop_ul_id = pop_ul_id;
						var pop_ul = $('<ul id="' + pop_ul_id + '"></ul>')
								.addClass('enclosure-list');

						if (this.options.h_value != '') {
							var list = this.options.h_value.split('|');
							var onelist;
							var t = '';
							var tr_num = 0;
							for (var i = 0; i < list.length; i++) {
								var pop_li_id = this.options.name + '_pop_li_'
										+ (++tr_num);
								var pop_li = $('<li id="' + pop_li_id
										+ '"></li>');
								t = list[i];
								onelist = t.split(':');
								pop_li.append($('<span>' + onelist[1]
										+ '<span>'));
								var s_value = list[i];
								if (i != 0) {
									s_value = '|' + s_value;
								}
								var s_text = onelist[1];
								if (i != 0) {
									s_text = ';' + s_text;
								}
								var s_file = t;
								var cal = $('<a href="#" onclick="$(\''
										+ this.options.id
										+ '\').asiainfoFile(\'deltr\',{tr_id:\''
										+ pop_li_id + '\',value:\'' + s_value
										+ '\',text:\'' + s_text + '\'})" ></a>');
								var cali = $('<i></i>')
										.addClass(
												'glyphicon glyphicon-trash file-del-ico');
								cal.append(cali);
								if(!this.options.readonly){
									pop_li.append(cal);
								}

								var down = $('<a href="#" onclick="window.location.href=\''
										+ this.options.down_url
										+ '?file='
										+ s_file + '\'"></a>');
								var downi = $('<i></i>')
										.addClass(
												'glyphicon glyphicon-download-alt file-download-ico');
								down.append(downi);
								pop_li.append(down);

								pop_ul.append(pop_li);
							}
							;
							this._setNum(tr_num);
						}

						pop_list_div.append(pop_ul);
						// 添加弹出框元素
						pop_div.append(upload_div).append(pop_list_div);
						$(this.options.id).append(pop_div);

						// 设置弹出事件
						$('#' + h_btn_id).click(function(event) {
							event.stopPropagation();
							var offset = $(event.target).offset();
							$('#' + pop_div_id).css({
							// top : offset.top + $(event.target).height() +
							// "px",
							// left : offset.left
							});
							// 按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。
							$('#' + pop_div_id).toggle('slow');
						});
						// 点击空白处或者自身隐藏弹出层，下面分别为滑动和淡出效果。
						$(document).click(function(event) {
							$('#' + pop_div_id).slideUp('slow')
						});
						$('#' + pop_div_id).click(function(event) {
							// $(this).fadeOut(1000)
							return false;
						});

						// 添加上传
						$("#" + pop_file_id)
								.uploadify(
										{
											swf : this.options.swf,
											uploader : this.options.url,
											height : this.options.b_height,
											width : this.options.b_width,
											'buttonClass' : 'btn btn-info btn-xs uploadfile-btn',
											button_image_url : this.options.button_image_url,
											'buttonText' : this.options.pop_btn_name,
											fileTypeExts : this.options.fileTypeExts,
											fileSizeLimit:this.options.fileSizeLimit,
											'formData' : {
												'dir' : 'attached',
												'pop_ul_id' : this.options.pop_ul_id,
												'h_value_id' : this.options.h_value_id,
												'h_text_id' : this.options.h_text_id,
												'id' : this.options.id,
												'name' : this.options.name,
												'down_url' : this.options.down_url,
												'index_num_id' : this.options.index_num_id,
												'pop_file_id' : this.options.pop_file_id
											},
											onSelectError:function(file, errorCode, errorMsg){
												var msgText = "上传失败\n";
												if(errorCode=='QUEUE_LIMIT_EXCEEDED'||errorCode=='-100'){
													//this.queueData.errorMsg='"任务数量超出队列限制';
													msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
												}else if(errorCode=='FILE_EXCEEDS_SIZE_LIMIT'||errorCode=='-110'){
													//this.queueData.errorMsg='文件大小超出限制';
													msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
												}else if(errorCode=='ZERO_BYTE_FILE'||errorCode=='-120'){
													//this.queueData.errorMsg='文件不能为空';
													msgText += "文件大小为0";
													
												}else if(errorCode=='INVALID_FILETYPE'||errorCode=='-130'){
													//this.queueData.errorMsg='文件类型不符合要求';
													msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
												}else{
													msgText += "错误代码：" + errorCode + "\n" + errorMsg;
												}
												//bootbox.alert(msgText);
												this.queueData.errorMsg=msgText;
											},
											'onUploadError':function(file, errorCode, errorMsg, errorString){
												bootbox.alert("文件上传失败");
											},
											/*
											 * 'onUploadStart' : function(file) {
											 * $("#"+pop_file_id).uploadify("settings",
											 * "dir", 'attached');
											 *  },
											 */
											// 上传到服务器，服务器返回相应信息到data里
											'onUploadSuccess' : function(e,
													data) {
												var jsondata = $
														.parseJSON(data);
												if (jsondata.error != '0') {
													console.log('文件上传失败');
													console
															.log(jsondata.message);
													bootbox
															.alert(jsondata.message);
													return false;
												}
												$this = this;
												$formData = this.settings.formData;

												var t_value = jsondata.file
														+ ':' + jsondata.name;
												var t_text = jsondata.name;
												var h_value = $(
														'#'
																+ $formData.h_value_id)
														.val();
												var s_file = t_value;
												if (h_value != ''
														&& h_value != null
														&& h_value != undefined) {
													t_value = '|' + t_value;
													t_text = ';' + t_text;
												}

												var tr_num = $(
														'#'
																+ $formData.index_num_id)
														.val();
												var ul_id = $formData.pop_ul_id;
												var pop_li_id = $formData.name
														+ '_pop_li_'
														+ (++tr_num);
												var pop_li = $('<li id="'
														+ pop_li_id + '"></li>');
												pop_li.append($('<span>'
														+ jsondata.name
														+ '<span>'));

												var cal = $('<a href="#" onclick="$(\''
														+ $formData.id
														+ '\').asiainfoFile(\'deltr\',{tr_id:\''
														+ pop_li_id
														+ '\',value:\''
														+ t_value
														+ '\',text:\''
														+ t_text
														+ '\'})" ></a>');
												var cali = $('<i></i>')
														.addClass(
																'glyphicon glyphicon-trash file-del-ico');
												cal.append(cali);
												pop_li.append(cal);

												var down = $('<a href="#" onclick="window.location.href=\''
														+ $formData.down_url
														+ '?file='
														+ s_file
														+ '\'" ></a>');
												var downi = $('<i></i>')
														.addClass(
																'glyphicon glyphicon-download-alt file-download-ico');
												down.append(downi);
												pop_li.append(down);
												$('#' + ul_id).append(pop_li);

												t_value = $(
														'#'
																+ $formData.h_value_id)
														.val()
														+ t_value;
												t_text = $(
														'#'
																+ $formData.h_text_id)
														.val()
														+ t_text;

												$('#' + $formData.h_value_id)
														.val(t_value);
												$('#' + $formData.h_text_id)
														.val(t_text);
												$('#' + $formData.index_num_id)
														.val(tr_num);
											}
										});

					},
					_destroy : function() {
						this.element.removeClass("asiainfoFile").val("");
					},
					deltr : function(data) {
						var tr_id = data.tr_id;
						var text = data.text;
						var value = data.value;
						$('#' + tr_id).remove();

						var t_value = $('#' + this.options.h_value_id).val();
						var t_text = $('#' + this.options.h_text_id).val();
						if (t_value.indexOf(value) == 0
								&& t_value.length > value.length) {
							value += '|'
						} else if (t_value.length - t_value.indexOf(value) == value.length
								&& t_value.indexOf(value) < 0) {
							value = value.substr(1, value.length - 1);
						}

						if (t_text.indexOf(text) == 0
								&& t_text.length > text.length) {
							text += ';'
						} else if (t_text.length - t_text.indexOf(text) == text.length
								&& t_text.indexOf(text) < 0) {
							text = text.substr(1, text.length - 1);
						}

						$('#' + this.options.h_text_id).val(
								t_text.replace(text, ''));
						$('#' + this.options.h_value_id).val(
								t_value.replace(value, ''));
					},
					_getNum : function() {
						return $('#' + this.options.index_num_id).val();
					},
					_setNum : function(num) {
						$('#' + this.options.index_num_id).val(num);
					},
					refreshdata : function() {
					},
					fireListener : function() {//通知别人刷新
						for (i in this.options.listeners) {
							//$(this.options.listeners[i]).asiainfoSelect("refreshdata");
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
					}

				});