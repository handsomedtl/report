/**
 * jquery 下拉框插件
 */
(function($) {
	$.fn.extend({
		jquerydate : function(option) {
			var $this = this;
			/**
			 * 初始化组件
			 */
			var init = function() {
				$($this).daterangepicker(
						{
							singleDatePicker : true,
							calender_style : "picker_3",
							ampm: true,
							timeFormat: 'h-m',
						    format: 'YYYY-MM-DD',
						    showDropdowns:true,
							locale : {
								applyLabel : '确定',
								cancelLabel : '取消',
								fromLabel : '起始时间',
								toLabel : '结束时间',
								customRangeLabel : '自定义',
								daysOfWeek : [ '日', '一', '二', '三', '四', '五',
										'六' ],
								monthNames : [ '一月', '二月', '三月', '四月', '五月',
										'六月', '七月', '八月', '九月', '十月', '十一月',
										'十二月' ],
								firstDay : 1
							}
						}, function(start, end, label) {
						});
			};

			init();
		}
	});
})(jQuery)