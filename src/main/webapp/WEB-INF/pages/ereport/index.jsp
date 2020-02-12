<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="">
<head>
<%@ include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<asiainfo:resource type="Bootstrap,easyui,jquery,dynamicform,validate"></asiainfo:resource>
<script src="${ctx}/resources/js/formatter/decimalFormat.js"></script>
<script src="${ctx}/resources/js/dateFormatter.js"></script>
<script src="${ctx}/resources/js/jquery.serializejson.min.js"></script>
<script src="${ctx}/resources/js/echarts-all-3.js"></script>
<script src="${ctx}/resources/js/jquery.ui.widget.min.js"></script>
<script src="${ctx}/resources/js/jquery.ui.echart.js"></script>
<%-- <script src="${ctx}/resources/js/html5/html5shiv.min.js"></script> --%>
<script src="${ctx}/resources/js/html5/respond.src.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/ext/datagrid-groupview.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/ext/datagrid-filter.js"></script>
<script src="${ctx}/resources/js/jquery-easyui/ext/datagrid-style.js"></script>
<script src="${ctx}/resources/js/addtabs/loadfinish.js"></script>
<title>${report.reportMeta.name}</title>
<script type="text/javascript">
	window.reportid = '${report.reportMeta.id}';
</script>
</head>
<body class="container body" style="background: #F7F7F7;">
	<div class="main_container">
		<!-- page content -->
		<div class="right_col" role="main" style="margin-left: 0px">
		<!-- 
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="page-title">
						<div class="title_left">
							<h2 title="${report.reportMeta.name}- ${report.reportMeta.id}">
								<i class="fa fa-bookmark"></i>&nbsp;&nbsp;${report.reportMeta.name}
							</h2>
						</div>
					</div>
				</div>
			</div>
			 -->
			${reportcontent}
		</div>
	</div>

</body>
<script type="text/javascript">
	var validform = function() {
		var result = validateFront();
		return result;
	}
	var validateFront = function() {
		$(document.forms[0]).parsley().validate();
		var valid = $(document.forms[0]).parsley().isValid();
		//alert(valid);
		if (valid) {
			$('.bs-callout-info').removeClass('hidden');
			$('.bs-callout-warning').addClass('hidden');
		} else {
			$('.bs-callout-info').addClass('hidden');
			$('.bs-callout-warning').removeClass('hidden');
		}
		return valid;
	};

	$(function() {
		$(".fa-collapse").each(
				function() {

					var t = $(this).attr("collapse");
					$(this).on(
							'click',
							function() {
								$(this).toggleClass('fa-angle-double-up')
										.toggleClass('fa-angle-double-down');
								$("#" + t).slideToggle("slow");

							})

				})
	})
</script>
</html>