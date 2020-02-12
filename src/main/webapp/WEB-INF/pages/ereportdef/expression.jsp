<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="expressionPanel" style="width: 500px; height: 300px;">
	<div id="expression" style="width: 100%; height: 120px">
		<textarea name="expressiontext" id="expressiontext"
			style="width: 100%;"></textarea>
	</div>
	

	<div style="width: 100%; height: 150px;">
		<div style="width: 100%; text-align: center;">所有函数</div>
		<ul style="list-style-type: none;border: 1px solid #E0ECFF;" id="functionList">
			<li><a href="javascript:selectFunction('sum()');">sum--合计</a></li>
			<li><a href="javascript:selectFunction('count()');">count--总数</a></li>
			<li><a href="javascript:selectFunction('avg()');">avg--平均值</a></li>
			<li><a href="javascript:selectFunction('min()');">min--最小值</a></li>
			<li><a href="javascript:selectFunction('max()');">max--最大值</a></li>
		</ul>
	</div>
	<div
		style="height: 130px; width: 100%;  float: left;">

		<div style="width: 100%; text-align: center;">操作说明</div>
		<div style="width: 100%; height: 80px;border: 1px solid #E0ECFF;" id="opercomm">
		 
		</div>
	</div>
</div>