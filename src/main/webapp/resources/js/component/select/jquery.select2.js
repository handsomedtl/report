var asiainfoSelect = $.widget("ui.asiainfoSelect", {
	version : "1.0.0-beta.1",
	defaultElement : "<div>",
	delay : 300,
	options : {
		id : '#select1',
		element_id:'12345679',
		listeners : [],//当自己改变时，要通知刷新的列表
		filterfields:[], //当自己刷新时，要传入的参数 {selector:'#test1',type:'select'}
		data:[{"id":"","text":"请选择"}],
		minimumResultsForSearch: Infinity,  //隐藏搜索框
		initData:true,
		url:window.contaxtrot+'/enume/select.jhtml',
		disabled:false,
		filtervals:{},
		placeholder: '请选择',
		required:false,
		default_value:'',
		readonly:false,
		messcontainer : '.errormessage',
		tooltipmess : '',
		
		//allowClear: true,
		/*
		templateResult:function(state){
			var text=state.text;
			var leg=state.text.length;
			if(leg>8){
				text=text.substr(0,8)+'..';
			}
			return text;
		}
		*/
		templateSelection:function(state){
			var text='<span style="white-space:nowrap;">' + state.text + '</span> '
			return text;
		}
	},
	_create : function() {
		if(this.options.initData){
			var m_disabled=$(this.options.id).attr("disabled");
			//this._resSelect(this.options.element_id,this.options.filterfields)
			var filtervals;
			var fireList=this.options.filtervals;
			if(fireList==undefined||fireList==null){
				filtervals=null;
			}else{
				filtervals = "{ ";
			    for(var item in fireList){
			    	filtervals += "'"+item+"':'"+fireList[item]+"',";
			    }
			    filtervals += " }";
			}
			this._getSelectData(this.options,filtervals);
			if(this.options.default_value!=undefined&&this.options.default_value!=''){
				$(this.options.id+" option[value='"+this.options.default_value+"']").attr("selected", true);
				$(this.options.id).select2(this.options);
			}
			if(m_disabled=='true'||m_disabled=='disabled'||this.options.disabled==true){
				$(this.options.id).prop("disabled", true); 
			}
		}else{
			$(this.options.id).select2(this.options);
		}
		
		this._on( $(this.options.id), {
			  "change":function( event ) {
				  this.fireListener()
			  }
		});
		if(this.options.readonly){
			$(this.options.id).prop("readonly", true);
		}
		//
		$(this.options.id).attr({
			'data-parsley-errors-container' : this.options.messcontainer
		});
		if (this.options.tooltipmess != '') {
			$(this.options.id).attr({
				'data-parsley-error-message' : this.options.tooltipmess
			});
		}
		//
		
	},
	_getSelectData : function(options,params){
		$.ajax({
			type : "POST",
			url : options.url,
			data : {
				element_id : options.element_id,
				params:params
			},
			context:this.options,
			dataType : "json",
			success : function(data) {
//				this.data=$.extend([{}],$.parseJSON(one),data);
				data1=$.merge(this.data, data);
				this.data=data1;
				
				//$(this.id).val(null).empty();
				$(this.id).select2(this);
				//$(this.id).prepend("<option>请选择</option>");
				if(this.default_value!=undefined&&this.default_value!=''){
					$(this.id+" option[value='"+this.default_value+"']").attr("selected", true);
					$(this.id).select2(this);
				}
				if(this.disabled==true||this.disabled=="true"){
					$(this.id).prop("disabled", true);
				}
				//this._resData(this.options)
			},
			error : function(info) {
				console.log("请求下拉框数据失败！")
			}
		});
	},
	/*
	_resData:function(options){
		$(options.id).val(null).empty();;
		$(options.id).select2(options);
	},
	*/
	_resSelect:function(element_id,filterfields){
		var list = this.options.filterfields;
		var rs='{';
		var size=list.length-1;
		for(i in list){
			e_name=$(list[i]).attr('name');
			if(e_name==null||e_name==''||e_name==undefined){
				continue;
			}
			rs += '\"'+e_name+'\"';
			rs += ':"';
			rs += $(list[i]).val()+'"';
			if(size != i){
				rs += ',';
			}
		}
		rs += '}';
		console.log(rs);
		this._getSelectData(this.options,rs);
	},
	setDisabled:function(param) {
		if(param==true){
			$(this.options.id).prop("disabled", true); 
		}
		if(param==false){
			$(this.options.id).prop("disabled", false); 
		}
	},
	checkSelect:function (){
		//var data=$(this.options.id).select2("data");
		if(this.options.required){
			var data=$(this.options.id).find("option:selected").val();
			if(data=="%"||data==""){
				return false;
			}
		}
		
	},
	refreshdata : function() {
		this._resSelect(this.options,this.options.filterfields);
	},
	fireListener : function() {//通知别人刷新
		for (i in this.options.listeners) {
			//$(this.options.listeners[i]).asiainfoSelect("refreshdata");
			var id = this.options.listeners[i].selector;
			var type = this.options.listeners[i].type;
			var pluginName = "asiainfo" + type.replace(/(\w)/,function(v){return v.toUpperCase()});
			var func = "$('"+id+"')."+pluginName+"(\"refreshdata\")";
			eval(func);
		}
	}
	
});