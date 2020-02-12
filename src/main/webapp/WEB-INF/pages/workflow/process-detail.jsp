<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>流程系统</title>
<asiainfo:resource type="jquery,dynamicform,Bootstrap,validate,datatables"></asiainfo:resource>
</head>
<body style="background: #fff;">
<div class="container">
	<div class="main_container right_col">
		<form class="form-horizontal form-label-left">
        	<div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="form-group">
                        <label class="control-label col-md-1 col-sm-2 col-xs-12">申请人</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                            <input type="text" name="" readonly value="${process.staffName}"
                                    class="form-control col-md-12 col-xs-12" />
                        </div>
                        <div class="col-md-4 col-sm-2 col-xs-12"></div>
                        <label class="control-label col-md-1 col-sm-2 col-xs-12">发起时间</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                            <input type="text" name="" readonly value="<fmt:formatDate value='${process.startDate}' type='date'/>" class="form-control col-md-12 col-xs-12" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-1 col-sm-2 col-xs-12">申请原因:</label>
                        <div id="applyreason_custinput" class="col-md-11 col-sm-10 col-xs-12 m-t8" > ${process.reason} </div>
                    </div>
                    <div class="form-group">
                        ${startForm}
                    </div>
                 </div>
            </div>
        </form>
        <!--审核详情 begin-->
        <div class="row">
        	<div class="col-md-12 col-sm-12 col-xs-12">
            	<div class="examine-box clearfix">
                	<div class="examine-tit"><b>审批详情</b></div>
                    <div class="x_content">
                    	 <!-- end of user messages -->
                            <ul class="messages">
                            	<c:forEach items="${process.processApproves}" var="approve">
                                <li class="media">
                                    <a class="pull-left border-blue profile_thumb"> <i class="fa fa-user blue"></i> </a>
                                    <div class="message_date">
                                        <h3 class="date text-info"><fmt:formatDate pattern="dd"  value="${approve.approveDate}" /></h3>
                                        <p class="month"><fmt:formatDate pattern="M"  value="${approve.approveDate}" />月</p>
                                    </div>
                                    <div class="message_wrapper">
                                        <h4 class="heading">${approve.staffName}<span class="node-tit">${approve.taskName}</span>
                                        <c:choose>
												   <c:when test="${approve.approveState==1}">
												      <span class="examine-btn examine-success-btn">${approve.approveStateName}</span>
												   </c:when>
												   <c:otherwise>
												      <span class="examine-btn examine-fail-btn">${approve.approveStateName}</span>
												   </c:otherwise>
												</c:choose>
                                       </h4>
                                        <blockquote class="message">
                                         <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${approve.approveDate}" /> ${approve.departName } ${approve.staffName } ${approve.approveStateName } </p>
                                         <p> ${approve.approveRemark}</p><p>${approve.summary}</p></blockquote>
                                    </div>
                                </li>
                                </c:forEach>
                                 <c:if test="${process.processState=='2' }">
                                 <li class="media">
                                    <a class="pull-left border-blue profile_thumb"> <i class="fa fa-user blue"></i> </a>
                                    <div class="message_date">
                                        <h3 class="date text-info"></h3>
                                        <p class="month"></p>
                                    </div>
                                    <div class="message_wrapper">
                                        <h4 class="heading"><c:if test="${process.currentStaffName==''}">负责人未认领</c:if>${process.currentStaffName }<span class="node-tit">${process.currentNode }</span><span class="examine-btn btn-info">未审核</span></h4>
                                        <blockquote class="message"></blockquote>
                                    </div>
                                </li>
                                </c:if>
                            </ul>
                            <!-- end of user messages -->
                	</div>
                </div>
            </div>
        </div>
        <!--审核详情 end-->
        <br>

        

    </div>
</div>
</body>
</html>