<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div style="text-align: center">
  <style>
    body {
      background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
  </style>
  <%@include file="/WEB-INF/header.jsp" %>
  <form action="${pageContext.request.contextPath}/login" method="post">
    <br><br>
    <h1>
      <div style="color: #c2dbcd"><fmt:message key="page.index.welcom"/></div>
    </h1>
    <br><br>
    <c:if test="${param.error!=null}">
      <div style="color: white">
        <span><fmt:message key="page.index.error"/></span>
      </div>
    </c:if>
    <label for="email">
      <div><fmt:message key="page.index.email"/></div>
      <input type="email" name="email" id="email" value="${param.email}" required style="background: azure">
    </label><br><br>
    <label for="password">
      <div><fmt:message key="page.index.password"/></div>
      <input type="text" name="password" id="password" required style="background: azure">
    </label><br><br>
    <input type="hidden" name="command" value="login">
    <button type="submit" style="background: lightcyan"><fmt:message key="page.index.login"/></button>
  </form>
    <form action="${pageContext.request.contextPath}/registration" method="get">
        <input type="hidden" name="command" value="registration">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.index.reg"/></button>
    </form>
</div>
