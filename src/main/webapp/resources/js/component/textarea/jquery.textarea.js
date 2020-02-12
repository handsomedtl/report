var asiainfoText = $.widget("ui.asiainfoTextarea", {
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
		/**
		 * 显示行数
		 */
		rows : 5,
		/**
		 * 列数
		 */
		cols : 20,
		readonly : false,
		tooltipmess : '',
		messcontainer : '.errormessage'
	},
	/**
	 * 构造方法
	 */
	_create : function() {
		this.element.attr('rows', this.options.rows);
		this.element.attr('cols', this.options.cols);
		if (this.options.required) {
			this.element.attr({
				required : 'required'
			});
		}
		if (this.options.readonly) {
			this.element.attr({
				readonly : 'readonly'
			});
		}
		this.element.attr({
			'data-parsley-errors-container' : this.options.messcontainer
		});
		if (this.options.tooltipmess != '') {
			this.element.attr({
				'data-parsley-error-message' : this.options.tooltipmess
			});
		}
		var default_value = this.options.default_value;
		if (default_value != "") {
			this.element.val(default_value);
		}
		// 给组件添加监听方法
		this._on($(this.element), {
			"change" : function(event) {
				this._fireListener();
			}
		});

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
			var pluginName = "asiainfo" + type.replace(/(\w)/, function(v) {
				return v.toUpperCase()
			});
			var func = "$('" + id + "')." + pluginName + "(\"refreshdata\")";
			eval(func);
		}
	}
});
