<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%-- <%@ taglib prefix="asiainfo" uri="/asiainfo-tags"%> 
	<div style="display: none;">
	<form id="form${relaseId}">
		<input type="hidden" name="relaseId" value="${relaseId}"/>
	</form>
	</div>
	<asiainfo:table id="mytable${relaseId}" paging="false" checkbox="false"
		actionUrl="scopelist.jhtml"
		cssclass="table table-striped responsive-utilities jambo_table"
		formid="form${relaseId}" keyField="id" jstablenam="mytable${relaseId}">
		<asiainfo:td title="地市" field="eparchyName" width="100"></asiainfo:td>
		<asiainfo:td title="部门" field="departName" width="100"></asiainfo:td>
	</asiainfo:table>
	--%>
<div class="panel-default">
  <div class="city-box">
  	<div class="ico"></div>地市范围
  </div>
  <div class="panel-body">
  <c:set var="code" value="0" />
  <c:forEach var="r" items="${list}" varStatus="idx">
  	<c:if test="${code != eparchycode}">
  		<c:if test="${code !=0}">
  		<br/>
  		</c:if>
  		<c:set var="code" value="${eparchycode}" />
  		<span class="city-tit-default">${r.eparchyName}</span>
  	</c:if>
  		<span class="city-tit">${r.departName}</span>
   </c:forEach>
  </div>
</div>