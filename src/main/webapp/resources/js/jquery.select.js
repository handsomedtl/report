/**
 * jquery 下拉框插件
 */
(function($) {
	$.fn.extend({
		jqueryselect : function(option) {
			/**
			 * 默认属性
			 */
			var defaults = {
				/**
				 * 选择器
				 */
				for_name : "#sid",
				/**
				 * 获取数方式 1:调用枚举服务2:调用自定义服务
				 */
				getDataType : '1',
				/**
				 * 枚举id
				 */
				enumId : '1000',
				/**
				 * 获取数据地址
				 */
				sourceurl : '/enume/getenumes.jhtml',
				/**
				 * 
				 */
				getDataParams : {
					type : this.enumId
				},
				type : 1,
				/**
				 * 表单提交方式
				 */
				method : 'post',
			};
			/**
			 * 组件属性
			 */
			var options = $.extend(defaults, option);
			/**
			 * 获取数据
			 */
			var getValue = function() {
				return $(options.forname).val();
			};

			var $this = this;
			/**
			 * 初始化组件
			 */
			var init = function() {
				$.ajax({
					type : options.method,
					url : options.sourceurl,
					data : options.getDataParams,
					dataType : "json",
					success : function(data) {
						$($this).empty();
						$($this).append(
								$('<option>').attr('value', '0').text('请选择'));
						for (i in data) {
							$($this).append(
									$('<option>').attr('value', data[i].id)
											.text(data[i].name));
						}
					},
					error : function(info) {
						alert('获取数据失败' + info);
					}
				});
			};
			/**
			 * 设置枚举类id
			 */
			var refresh = function(optionsi) {
				var options = $.extend(optionsi, option);
				this.init();
			};
			init(options);
		}
	});
})(jQuery)