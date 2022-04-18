<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <div id="locale">
        <form action="${pageContext.request.contextPath}/locale" method="post">
            <input type="hidden" name="command" value="locale">
            <button type="submit" style="background: lightcyan" name="lang" value="ru_RU">RU</button>
            <button type="submit" style="background: lightcyan" name="lang" value="en_US">EN</button>
        </form>
    </div>
    <fmt:setLocale
            value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null? param.lang : 'us_US')}"/>
    <fmt:setBundle basename="translations"/>
    <c:if test="${not empty sessionScope.user}">
        <div id="logout">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="command" value="logout">
                <button type="submit" style="background: lightcyan"><fmt:message key="page.header.exit"/></button>
            </form>
        </div>
    </c:if>
</div>

