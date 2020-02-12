var asiainfoPhone = $.widget("ui.asiainfoPhone", {
	version : "1.0.0-beta.1",
	delay : 300,
	options : {
		value : '',
		id : "#phone",
		elementid : 10000009,
		listeners : [],
		filterfields : [],
		filtervals : [],
		required : false,
		readonly:false,
		tooltipmess : '',
		messcontainer : '.errormessage'
	},

	_create : function() {
		this.element.attr({
			"data-parsley-type" : "number"
		});
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
		if (this.options.value != null && this.options.value != '') {
			this.element.val(this.options.value);
		}

		this._on($(this.options.id), {
			"change" : function(event) {
				this._checkPhonenum();
			}
		});
	},
	_checkPhonenum : function() {
		var value = this.element.val();
		if (value != null && value != "") {
			var reg = new RegExp("^[1][3|4|5|7|8][0-9]{9}$");
			if (!reg.test(value)) {
				alert("请输入正确的手机号码！");
				return false;
			}
		}
		return true;
	},
	_destroy : function() {
		this.element.removeClass("asiainfoPhone").val("");
	},
	refreshdata : function() {

	},
	fireListener : function() {//通知别人刷新
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