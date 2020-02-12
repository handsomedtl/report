$(document).ready(function() {
	$.get("echarts_option.jhtml", function(result){
	    //$("div").html(result);
	    var myChart = echarts.init(document.getElementById('echarts-container'));

		 // 使用刚指定的配置项和数据显示图表。
		 myChart.setOption(result);
	 },'json');
	
});