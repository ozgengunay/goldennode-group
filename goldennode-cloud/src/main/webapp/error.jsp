<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Spring MVC Exception Handling</title>
</head>
<body>

<h2>Spring MVC Exception Handling</h2>

<h3>${exception}</h3>
<c:forEach items="${exception.stackTrace}" var="element">
    <c:out value="${element}"/>
</c:forEach>

</body>
</html>