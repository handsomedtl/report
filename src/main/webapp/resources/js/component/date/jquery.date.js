var widgetDate = $.widget("ui.asiainfoDate", {
	version : "1.0.0-beta.1",
	defaultElement : "<div>",
	delay : 300,
	options : {
		id : '#date1',
		element_id : '12345679',
		required : false,
		timepicker : false,
		formatDate : 'Y-m-d',
		listeners : [],// 当自己改变时，要通知刷新的列表
		filterfields : [], // 当自己刷新时，要传入的参数 {selector:'#test1',type:'select'}
		url : window.contaxtrot + '/date.jhtml',
		mask : {
			replace : function() {
			}
		},
		readonly : false,
		tooltipmess : '',
		messcontainer : '.errormessage'
	/*
	 * i18n : { de : { months : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月',
	 * '八月', '九月', '十月', '十一月', '十二月', ], dayOfWeek : [ "星期日", "星期一", "星期二",
	 * "星期三", "星期四", "星期五", "星期六", ] } },
	 */
	},

	_create : function() {
		// this._getData(this.options,null);
		if (this.options.required) {
			this.element.attr({
				required : 'required'
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
		if (this.options.readonly == true) {
			this.options.minDate = 0;
			this.options.minTime = 0;
			this.options.maxDate = 0;
			this.options.maxTime = 0;
		}
		$(this.options.id).datetimepicker(this.options);
		if (this.options.readonly) {
			$(this.options.id).attr("readonly", "readonly")
		}

		// 给组件添加监听方法
		this._on($(this.element), {
			"change" : function(event) {
				this.fireListener();
			}
		});
	},
	_getData : function(options, params) {
		// $this=this;
		$.ajax({
			type : "POST",
			url : this.options.url + '?time=' + new Date().getTime(),
			data : {
				element_id : options.element_id,
				params : params
			},
			// async:false,
			context : this.options,
			dataType : "json",
			success : function(data) {
				if (data.format != "" && data.format != undefined) {
					this.format = data.format;
				}
				if (data.min != "" && data.min != undefined) {
					this.min = data.min;
				}
				if (data.max != "" && data.max != undefined) {
					this.max = data.max;
				}
				if (data.yearStart != "" && data.yearStart != undefined) {
					this.yearStart = data.yearStart;
				}
				if (data.yearEnd != "" && data.yearEnd != undefined) {
					this.yearEnd = data.yearEnd;
				}
				if (data.mask != "" && data.mask != undefined) {
					this.mask = data.mask;
				}
				if (data.timepicker == false) {
					this.timepicker = data.timepicker;
				}
				if (data.datepicker == false) {
					this.datepicker = data.datepicker;
				}
				if (data.todayButton == false) {
					this.todayButton = data.todayButton;
				}
				$(this.id).datetimepicker(this);
			},
			error : function(info) {
				console.log("请求下拉框数据失败！")
			}
		});
	},
	refreshdata : function() {
	},
	fireListener : function() {// 通知别人刷新
		for (i in this.options.listeners) {
			// $(this.options.listeners[i]).asiainfoSelect("refreshdata");
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