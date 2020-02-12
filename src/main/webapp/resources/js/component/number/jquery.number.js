var widgetsMenu = $.widget("ui.asiainfoNumber", {
	version : "1.0.0-beta.1",
	delay : 300,
	options : {
		value : 0,
		id : "#number",
		elementid : 10000004,
		listeners : [],
		filterfields : [],
		filtervals : [],
		datatype : 'number',
		required : false,
		formatparam : {},
		readonly : false,
		value : '',
		tooltipmess : '',
		messcontainer : '.errormessage',
		pattern : '^-?(0|([1-9][0-9]*))(.[0-9]{1,2})?$'
	},

	_create : function() {
		if (this.options.value != '') {
			this.element.val(this.options.value);
		}
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
		this._on($('#' + this.options.id), {
			"change" : function(event) {
				this._formatNumber();
				this.fireListener();
			}
		});
	},
	_formatNumber : function() {
		// 先判断是否位数字
		var value = this.element.val();
		var result = '';
		if (this.options.pattern != '' && this.options.pattern != null) {
			var reg = new RegExp(this.options.pattern);// 是否用户个性化设置的格式
			var reg1 = new RegExp("^-?(0|([1-9][0-9]*))(.[0-9]+)?$");// 是否decimal
			if (!reg.test(value) && !reg1.test(value)) {// 不是数字
				result = '';
			} else if (!reg.test(value)&&reg1.test(value) ) {// 数字但是不是用户要的
				if (value.indexOf("\.") > 0) {// 如果是小数点问题进行处理
					result = value.substr(0, value.indexOf("\.") + 3);
				} else {
					result = '';
				}
			} else if (reg.test(value)) {// 正确
				result = value;
			} else {// 其他情况
				result = '';
			}
		}

		/*
		 * if(flag){ var truncway = this.options.formatparam.truncway; var
		 * format = this.options.formatparam.format; var digit = 0; //获得小数点后面位数
		 * if(format.indexOf(".") > 0){ digit =
		 * format.toString().split(".")[1].length; } //判断截取类型 if(truncway ==
		 * "round"){ //四舍五入 value = accounting.toFixed(value,digit); } else
		 * if(truncway == "truncate"){ //截取 var reg = new
		 * RegExp("^([0-9]+\.[0-9]{"+digit+"})[0-9]*"); value =
		 * value.replace(reg,"$1"); } //判断格式化类型 if(format.indexOf(",") > 0){
		 * //货币类型 result = accounting.formatMoney(value,[symbol = ""]); } else{
		 * result = value; } }
		 */
		this.element.val(result);

	},
	_checkNumber : function(value) {
		if (value != null && value != "") {
			var reg = new RegExp("^[0-9]+(.[0-9]+)?$");
			if (!reg.test(value)) {
				alert("请输入数字！");
				return false;
			}
		}
		return true;
	},
	_destroy : function() {
		this.element.removeClass("asiainfoNumber").val("");
	},
	refreshdata : function() {

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