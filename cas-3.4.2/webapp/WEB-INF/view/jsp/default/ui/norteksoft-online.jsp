<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>" mce_href="<c:url value="/images/favicon.ico"/>" type="image/x-icon" /> 
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/css.css"/>"/>
	<script src="<c:url value="/js/jquery.js"/>" type="text/javascript"></script>
	<title>登录界面</title>
	<script type="text/javascript">
	$().ready(function(){
		$('#username').focus();
		if($("#_name").attr('value')!="" && $("#_pwd").attr('value')!=""){
			$("#username").attr('value',$("#_name").attr('value'));
			$("#password").attr('value',$("#_pwd").attr('value'));
			$("#submitBtn").click();
		}
	});
	</script>
	<style type="text/css">
	ul li{padding:3px;}
	</style>
</head>
	<body >
	
<%
	String _name = "";//?type=auto&name=pt&pwd=1
	String _pwd = "";
	String service = request.getParameter("service");
	if (service!=null && service.contains(";type=auto;")) {
		String infos = service.substring(service.indexOf(";type=auto;")+11);
		int index = infos.indexOf("=");
		_name = infos.substring(0,index);
		_pwd = infos.substring(index+1);
		service = service.substring(0,service.indexOf("type"));
		System.out.println(service+"================");
		request.setAttribute("service", service);
	}
%>
<%="<input type='hidden' id='_name' value='"+_name+"' />"%>
<%="<input type='hidden' id='_pwd' value='"+_pwd+"' />"%>
	
    <div class="bg01">
	<div class="bg">
		<div class="login-bg">
		
			<div class="head-logo">平台演示系统</div>
			<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			<form:errors path="*" cssClass="login-state" id="loginState" element="div" />
	        <div class="logo"></div>
			<div class="login-body">
			
            	<table width="300" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>用户名:</td>
                    <td><form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" /></td>
                  </tr>
                  <tr>
                    <td>密&nbsp;&nbsp;码:</td>
                    <td><form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" /></td>
                  </tr>
                  <c:if test="${showCode}">
                  <tr>
                    <td>验证码:</td>
                    <td><input type="text" id="j_captcha_response" name="j_captcha_response" size="7" style="width: 100px;float: left;"/>&nbsp;<img 
                    		id="captcha" src="captcha.htm" alt="看不清，点击换一张" style="float: left;margin-left: 2px;cursor:hand;"
                    		onclick="this.src='captcha.htm?now='+ new Date().getTime();"/></td>
                  </tr>
                  </c:if>
                  <tr>
                    <td></td>
                    <td>
                    <input id="submitBtn" class="btn" name="submit" accesskey="l" value="登&nbsp;录" tabindex="4" type="submit" />
                    <input class="btn" name="reset" accesskey="c" value="清&nbsp;空" tabindex="5" type="reset" />
                    </td>
                  </tr>
                </table>
                
				<input type="hidden" name="lt" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
			</div>
			</form:form>
			<div class="login-foot">
				<a>北京北科汇智软件技术有限公司</a>
			</div>
			
		</div>
		<div style="color: yellow;position:absolute;bottom:1%;">
		<ul style="list-style: none;font-size:10pt;">
			<li>提示：</li>
			<li>1. 系统用户（密码均为123）：</li>
			<li>
				<ul style="list-style: square">
					<li>普通用户：user1、user2、user3、user4、user5</li>
					<li>三员：admin.systemAdmin、admin.securityAdmin、admin.auditAdmin </li>
				</ul>
			</li>
			<li>2. 系统中样例数据，请尽量不要删除。</li>
			<li>3.  数据清理规则：每天17:00管理员会准时清理系统中试用时录入的数据。</li>
			<li>4.  为确保在线试用系统的正常运行，将系统中部分修改、删除等功能进行了屏蔽。</li>
			<li>5.  浏览器：IE8及以上、Firefox、Google Chrome、Safari</li>
			<li>6.  分辨率：1024×768及以上</li>
		</ul>
		</div>
	</div>
    </div>
</body>
</html>
