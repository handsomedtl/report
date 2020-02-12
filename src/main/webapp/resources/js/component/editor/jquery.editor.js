var asiainfoText = $
		.widget(
				"ui.asiainfoEditor",
				{
					version : "1.0.0-beta.1",
					defaultElement : "<div>",
					delay : 300,
					options : {
						/**
						 * 选择器
						 */
						id : '',

						elmentid : 'elmentid',

						fieldname : '',
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
						 * 默认值
						 */
						default_value : '',

						required : false,
						/**
						 * 由系统统一验证则设置为false
						 */
						valid : false,
						cssPath : window.contaxtrot+'/resources/js/kindeditor/plugins/code/prettify.css',
						uploadJson : window.contaxtrot+'/file/upload.jhtml',
						fileManagerJson : window.contaxtrot+'/file/manager.jhtml',
						allowFileManager : true,
						afterBlur : function() {
							this.sync();
						},
						readonly : false,
						tooltipmess : '',
						messcontainer : '.errormessage'
					},
					/**
					 * 构造方法
					 */
					_create : function() {
						if (this.options.required) {
							this.element.attr({
								required : 'required'
							});
						}
						this.element
								.attr({
									'data-parsley-errors-container' : this.options.messcontainer
								});
						if (this.options.tooltipmess != '') {
							this.element
									.attr({
										'data-parsley-error-message' : this.options.tooltipmess
									});
						}
						if (this.options.fieldname != '') {
							this.element.attr('name', this.options.fieldname);
						}
						KindEditor.v_options = this.options;
						KindEditor.ready(function(K) {
							var options = KindEditor.v_options;
							var editor = K.create(options.id, options);
							var default_value = options.default_value;
							if (default_value != "") {
								editor.html(default_value);
							}
							var readonly = options.readonly;
							if (readonly) {
								editor.readonly();
							}
						});
						// 给组件添加监听方法
						this._on($(this.element), {
							"change" : function(event) {
								this._fireListener();
							}
						});

					},
					setValue : function(context) {
					},
					getValue : function() {
						return this.element.val();
					},
					/**
					 * 由别的组件调用刷新树组件
					 */
					refreshdata : function() {
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
					}
				});
