var widgetsMenu = $.widget("ui.asiainfoCustom", {
	version : "1.0.0-beta.1",
	delay : 300,
	options : {
		value : 0,
		elementid : 10000001,
		/**
		 * 当自己改变时，要通知刷新的列表
		 */

		listeners : [],
		/**
		 * 当自己刷新时，要传入的参数
		 */
		filterfields : [],
		filtervals : [],
		serverurl : window.contaxtrot+'/custom/getcustomdata.jhtml',
		readonly : false,
		tooltipmess : '',
		messcontainer : '.errormessage',
		datatype : '',
		inputType:''
	},
	_create : function() {
		if(this.options.inputType!=''){
			this.element.attr('type', 'hidden');
		}
		// 初始化插件，给默认值
		this.element.val(this.options.value);
		if (this.options.readonly == true) {
			this.element.attr("readonly", "readonly");
		}
		this.element.attr({
			'data-parsley-errors-container' : this.options.messcontainer
		});
		if (this.options.tooltipmess != '') {
			this.element.attr({
				'data-parsley-error-message' : this.options.tooltipmess
			});
		}
		if (this.options.datatype != '') {
			this.element.attr({
				"data-parsley-type" : this.options.datatype
			});
		}
		this.fireListener();
	},
	_getParams : function() {
		var list = this.options.filterfields;
		var rs = '{';
		var size = list.length - 1;
		for (i in list) {
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
	_getData : function(elementid, params) {
		var $this = this;
		$.ajax({
			type : "POST",
			url : $this.options.serverurl,
			data : {
				elementid : elementid,
				params : params
			},
			dataType : "json",
			success : function(data) {
				$this.element.val(data.textvalue);
			},
			error : function(info) {
				console.log("请求数据失败！");
			}
		});
	},
	_destroy : function() {
		this.element.removeClass("asiainfoCustom").val("");
	},
	refreshdata : function() {
		var params = this._getParams();
		this._getData(this.options.elementid, params);
		this.fireListener();
	},
	fireListener : function() {// 通知别人刷新
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