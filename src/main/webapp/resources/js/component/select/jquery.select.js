var asiainfoSelect = $.widget("ui.asiainfoSelect", {
	version : "1.0.0-beta.1",
	defaultElement : "<div>",
	delay : 300,
	options : {
		id : '#select1',
		element_id : '12345679',
		listeners : [],// 当自己改变时，要通知刷新的列表
		filterfields : [], // 当自己刷新时，要传入的参数 {selector:'#test1',type:'select'}
		datadefault : {
			"id" : "",
			"text" : "请选择"
		},
		dataid : '', // 用来自定义全选符号
		datatext : '',// 用来自定义请选择的名字
		initData : true,
		url : window.contaxtrot + '/enume/select.jhtml',
		disabled : false,
		filtervals : {},
		placeholder : '请选择',
		default_value : '',
		hasall : true,
		required : false,
		readonly : false,
		messcontainer : '.errormessage',
		tooltipmess : ''
	},
	_create : function() {
		this.element.addClass('form-control');
		var hiddeninput = $('<input>').attr({
			type : 'hidden',
			name : this.element.attr('id') + '_name',
			id : this.element.attr('id') + '_name'
		});
		this.element.after(hiddeninput);
		if (this.options.initData) {
			var filtervals;
			var fireList = this.options.filtervals;
			if (fireList == undefined || fireList == null) {
				filtervals = null;
			} else {
				filtervals = "{ ";
				for ( var i=0;i<fireList.length;i++) {
					filtervals += "'" + fireList[i].sqlfieldname + "':'" + $(fireList[i].filedname).val() + "',";
				}
				filtervals += " }";
			}
			this._getSelectData(this.options, filtervals);
		}
		if (this.options.required) {
			$(this.options.id).attr({
				'required' : 'required'
			});
		}
		$(this.options.id).attr({
			'data-parsley-errors-container' : this.options.messcontainer
		});
		if (this.options.tooltipmess != '') {
			$(this.options.id).attr({
				'data-parsley-error-message' : this.options.tooltipmess
			});
		}
		;
		this._on($(this.options.id), {
			"change" : function(event) {
				var nameelement_id = this.element.attr('id') + '_name';
				var text1 = $(
						"#" + this.element.attr('id') + " option:selected")
						.text();
				$('#' + nameelement_id).val(text1);
				this.fireListener()
			}
		});
	},
	_getSelectData : function(options, params) {
		$.ajax({
			type : "POST",
			url : options.url,
			data : {
				element_id : options.element_id,
				params : params
			},
			context : this.options,
			dataType : "json",
			success : function(data) {
				if (this.hasall) {
					if (this.dataid != '') {
						this.datadefault.id = this.dataid;
					}
					if (this.datatext != '') {
						this.datadefault.id = this.datatext;
					}
					data.unshift(this.datadefault);
					// $.merge(tdata,data);
				}
				var matchdefaultvalue = false;
				$(this.id).empty();
				if (this.readonly) {
					if (this.default_value != '') {
						data.default_value = this.default_value
						data = $.grep(data, function(val, key) {
							$this = data;
							if (val.id == data.default_value) {
								matchdefaultvalue = true;
								return true;
							}
							return false;
						}, false);
					} 
					else {
						var arrayObj = new Array();
						arrayObj[0] = this.datadefault;
						data = arrayObj;
					}
				}
				data.id = this.id;
				$.each(data, function(key, val) {
					$(data.id).append(
							'<option value="' + val.id
									+ '" style="white-space:nowrap;">'
									+ val.text + '</option>');
				});
				if ((this.default_value != '' && matchdefaultvalue
						&& this.readonly) || (this.default_value != ''
						&& !this.readonly)) {
					$(this.id).val(this.default_value);
					var text1 = $(this.id + " option:selected").text();
					$(this.id + "_name").val(text1);
				} else {
					$(this.id).val(this.datadefault.id);
					var text1 = $(this.id + " option:selected").text();
					$(this.id + "_name").val(text1);
				}
			},
			error : function(info) {
				console.log("请求下拉框数据失败！")
			}
		});
	},
	_resSelect : function(element_id, filterfields) {
		var list = this.options.filterfields;
		var rs = '{';
		var size = list.length - 1;
		for (i in list) {
			e_name = list[i].sqlfieldname;
			if (e_name == null || e_name == '' || e_name == undefined) {
				continue;
			}
			rs += '\"' + e_name + '\"';
			rs += ':"';
			rs += $(list[i].filedname).val() + '"';
			if (size != i) {
				rs += ',';
			}
		}
		rs += '}';
		console.log(rs);
		this._getSelectData(this.options, rs);
	},
	value : function() {
		return $(this.options.id).find("option:selected").val();
	},
	refreshdata : function() {
		this._resSelect(this.options, this.options.filterfields);
	},
	fireListener : function() {// 通知别人刷新
		for (i in this.options.listeners) {
			// $(this.options.listeners[i]).asiainfoSelect("refreshdata");
			var id = this.options.listeners[i].selector;
			if (!($(id).val() == ''))
				$(id).empty();
			var type = this.options.listeners[i].type;
			var pluginName = "asiainfo" + type.replace(/(\w)/, function(v) {
				return v.toUpperCase()
			});
			var func = "$('" + id + "')." + pluginName + "(\"refreshdata\")";
			eval(func);
		}
	}

});