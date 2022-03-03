<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<div style="text-align: center">
    <style>
        body {
            background: url(https://images5.alphacoders.com/891/891165.jpg);
        }
    </style>
    <head>
        <%@include file="header.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="page.index.reg"/></title>
    </head>
    <body>

    <form action="${pageContext.request.contextPath}/registration" method="post">
        <label for="name">
            <div><fmt:message key="page.registration.name"/></div>
            <input type="text" name="name" id="name" required style="background: azure">
        </label><br><br>
        <label for="emailId">
            <div><fmt:message key="page.index.email"/></div>
            <input type="text" name="email" id="emailId" required style="background: azure">
        </label><br><br>
        <label for="passwordId">
            <div><fmt:message key="page.index.password"/></div>
            <input type="text" name="password" id="passwordId" required style="background: azure">
        </label><br><br>
        <c:if test="${not empty requestScope.errorsDao}">
            <jsp:useBean id="errorsDao" scope="request" type="java.lang.String"/>
            <span style="color: fuchsia">${errorsDao}</span>
        </c:if>
        <br>
        <input type="hidden" name="command" value="registration">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.index.reg"/></button>
        <br><br>
        <input type="button" style="background: lightcyan" onclick="location.replace('http://localhost:8081/hotel/'); return false;" value="<fmt:message key="page.processing.back"/>"/>
        <br>
        <c:if test="${not empty requestScope.errors}">
            <br><br>
            <c:forEach var="error" items="${requestScope.errors}">
                <span style="color: fuchsia">${error.message}</span>
                <br><br>
            </c:forEach>
        </c:if>
    </form>
    </body>
</div>
</html>
