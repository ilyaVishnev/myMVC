<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 18.09.2018
  Time: 0:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sighIn</title>
</head>
<body>
<c:if test="${error!=''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/sign" method="post">
    Login : <input type="text" name="login"></br>
    Password : <input type="password" name="password"></br>
    <input type="submit" value="Вход">
</form>
</body>
</html>
