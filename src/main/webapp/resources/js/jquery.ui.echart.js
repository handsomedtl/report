/**
 * jQuery 用 Echarts 插件。
 * 作者 baowenshou
 *  2016.08.03
 */
 
 /**
 * 调用方法1 $("selector").echarts({echartOption:echartOption})
 * 调用方法2 $("selector").echarts({optionUrl:""})
 * 目前参数 echartOption 为 Echart原始配置格式的参数,optionUrl为异步获取echartOption的地址。。
 */
(function($){
	$.widget("ui.echarts", {
	    options: {
	        optionUrl:"",
	        echartOption:null,
	        myChart:null,
	        data:{},
	        containerClass:"echarts-container"
	    },
	    _create: function() {
	    	var e = this.element;
	    	this.options.myChart = echarts.init(this.element.get(0));
	    	$(window).resize(this.options.myChart,function(event){
				var instance =event.data;
				instance.resize();
			})
	    },
	    _init: function(){
            var e = this.element;
            if(typeof(this.options.optionUrl)=="string"){
            	$.ajax({ 
            		url: this.options.optionUrl, 
            		context: this, 
            		data:this.options.data,
            		dataType:"json",
            		error:function(r,e){
            			alert("ajax-error:"+e);
            		},
            		success: function(result){
				        var myChart = echarts.getInstanceByDom(this.element.get(0));
						myChart.setOption(result);
					}
				});
	    	}else if(typeof(this.options.echartOption)=="object"){
	    		var myChart = echarts.getInstanceByDom(this.element.get(0));
	    		myChart.setOption(this.options.echartOption);
	    	}
		},
		destroy:function(){
			var e = this.element;
		}
	});
})(jQuery);