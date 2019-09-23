<%--
  Created by IntelliJ IDEA.
  User: Orhan Gazi
  Date: 23.09.2019
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New Owner</title>
</head>
<body>
    <form:form modelAttribute="owner" method="post">
        First name: <form:input path="firstName" /><br>
        Last name: <form:input path="lastName" /><br>
        <form:button name="submit">Edit</form:button>
    </form:form>
</body>
</html>
