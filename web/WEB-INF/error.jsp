<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>

<div style="text-align: center">
    <%@include file="header.jsp" %>
    <fmt:setBundle basename="translations"/>
    <head>
        <br><br>
        <title><fmt:message key="page.home.reception"/></title>
        <h1>
            <div style="color: #c2dbcd"><fmt:message key="page.error.message"/></div>
        </h1>
    </head>
</div>
</html>

