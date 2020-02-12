var asiainfoCheckbox = $.widget("ui.asiainfoCheckbox", {
	version : "1.0.0-beta.1",
	defaultElement : "<div>",
	delay : 300,
	options : {
		/**
		 * 组件id
		 */
		elementid : 'elementid',
		/**
		 * 对应输入域的名称
		 */
		fieldname : 'checkbox',

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
		defaultvalue : [],
		required : false,
		float : 'left',
		readonly : false

	},
	/**
	 * 构造方法
	 */
	_create : function() {
		$.ajax({
			type : "POST",
			url : this.options.serverurl,
			data : {
				elementid : options.elementid
			},
			context : this.options,
			dataType : "json",
			success : function(data) {
				for (i in data) {
					var checkbox = $('<input>').attr({
						type : 'checkbox',
						name : this.options.fieldname,
						value : data[i].value
					}).text(data[i].text).css('float', this.options.float);
					this.element.append(checkbox);
				}

			},
			error : function(info) {
				console.log("获取复选框数据失败！")
			}
		});
		// 设置默认值
		for (i in defaultvalue) {
			var value = defaultvalue[i];
			$("[name =" + this.options.fieldname + "][value=" + value + "]")
					.attr("checked", true);
		}
		if (this.options.readonly == true) {
			this.element.attr("readonly", "readonly");
		}
	},

	/**
	 * 由别的组件调用刷新树组件
	 */
	refreshdata : function() {
		// 不提供刷新方法
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

	},

});
