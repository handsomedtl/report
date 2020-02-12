<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>工作流系统 |</title>

<link href="${ctx }/resources/gentelella/css/bootstrap.min.css"
	rel="stylesheet">

<link href="${ctx }/resources/gentelella/fonts/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${ctx }/resources/gentelella/css/animate.min.css" rel="stylesheet">

<link href="${ctx }/resources/gentelella/css/custom.css" rel="stylesheet">
<link href="${ctx }/resources/gentelella/css/icheck/flat/green.css"
	rel="stylesheet">
<script src="${ctx }/resources/js/jquery-1.12.1.min.js"></script>
</head>
<body style="background: #F7F7F7;">
	<div class="">
		<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
			id="tologin"></a>
		<div id="wrapper">
			<div id="login" class="animate form">
				<section class="login_content">
				<c:choose>
				<c:when test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
					<c:choose>
						<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message eq 'Bad credentials'}">
							<font style="color: #FF0000">用户名或者密码不正确！</font>
						</c:when>
						<c:otherwise>
							<font style="color: #FF0000">${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
						</c:otherwise>
					</c:choose>
				</c:when>
				</c:choose>
				<c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
				<form name='f' action="${ctx }/login/login.jhtml" method='POST'
					id="loginform">
					<h1>欢迎您登录系统</h1>
					<div>
						<input type="text" class="form-control" placeholder="用户名"
							required="" name='username' />
							<input type="hidden" name="userType" value="0" />
					</div>
					<div>
						<input type="password" class="form-control" placeholder="密码"
							required="" name='password' />
					</div>
				 
					<div>
						<p class="change_link" style="text-align: left">
							验证码： <img src="${ctx }/login/getvalidcode.jhtml" id="randCodeImage"
								style="width: 70px; height: 30px;" onclick="javascript:reloadRandCodeImage();" />
						</p>
					</div>
					<div>
						<input type="text" class="form-control" placeholder="验证码"
							required="" name='validcode' />
					</div>
					
					<div>
						<a class="btn btn-default submit" href="javascript:submitform();">登陆</a>
						<a class="reset_pass" href="#">忘记密码?</a>
					</div>
					<div class="clearfix"></div>
					<div class="separator">

						<p class="change_link">
							新用户请<a href="#toregister" class="to_register"> 注册 </a>
						</p>
						<div class="clearfix"></div>
						<br />
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> <input type="hidden"
						name="targetUrlParameter" value="${targetUrlParameter}" />
				</form>
				</section>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">
	var submitform = function() { 
		$('#loginform').submit();
	}
	function reloadRandCodeImage() {
		var date = new Date();
		var img = document.getElementById("randCodeImage");
		img.src = '${ctx }/login/getvalidcode.jhtml?a=' + date.getTime();
	}
</script>

</html>

