<%--
  Created by IntelliJ IDEA.
  User: Orhan Gazi
  Date: 8.09.2019
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>PetClinic Login Page</h1>
<form action="login" method="post">
    Username: <input type="text" name="username">
    <br>
    Password: <input type="password" name="password">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <br>
    Remember me: <input type="checkbox" name="remember-me">
    <br>
    <input type="submit" value="Login">
    <div style="color:red">
        <c:if test="${not empty param.loginFailed}">
            <c:out value="Login failed, incorrect username or password"></c:out>
        </c:if>
    </div>
</form>
</body>
</html>
