<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>
<fmt:setBundle basename="translations"/>
<head>
    <title><fmt:message key="page.successful.succes"/></title>
</head>
<body>
<div style="text-align: center">
    <h1 style="color: #c2dbcd"><fmt:message key="page.successful.message"/></h1>
    <br>
    <h1 style="color: #c2dbcd"><fmt:message key="page.successful.message2"/></h1>
    <br><br>
    <form action="${pageContext.request.contextPath}/login" method="get">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.application.home"/></button>
        <input type="hidden" name="command" value="login">
    </form>
    <br>
    <form action="${pageContext.request.contextPath}/profile" method="get">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.home.myapplications"/></button>
        <input type="hidden" name="command" value="profile">
    </form>
</div>
</body>
</html>
