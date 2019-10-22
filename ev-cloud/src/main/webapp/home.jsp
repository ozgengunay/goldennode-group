<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title></title>
</head>
<body>
	<div class="page-header">
		<h1>
			<spring:message code="label.homepage.title" />
			<sec:authentication property="principal.firstName" />
			<sec:authentication property="principal.lastName" />
		</h1>
	</div>
	<div>
		<p>
			<spring:message code="text.homepage.greeting" />
		</p>
	</div>
	<div>
		<p>Api Key: ${apiKey}</p>
		<p>Secret Key: ${secretKey}</p>
	</div>
	<form action="/logout" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Logout">
	</form>
</body>
</html>