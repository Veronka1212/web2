<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>
<head>
    <%@include file="header.jsp" %>
    <title>
        <fmt:message key="page.processing.processing"/>
    </title>
</head>
<body>
<style>
    table {
        width: 60%; /* Ширина таблицы */
        background: #c2dbcd; /* Цвет фона таблицы */
        color: white; /* Цвет текста */
        border-spacing: 1px; /* Расстояние между ячейками */
    }

    td, th {
        background: #c2dbcd; /* Цвет фона ячеек */
        padding: 5px; /* Поля вокруг текста */
    }
</style>
<table>
    <tr>
        <td>
    <tr>
        <td><strong><fmt:message key="page.admin.application"/></strong></td>
        <td>${application.id}</td>
    </tr>
    <tr>
        <td><strong><fmt:message key="page.index.email"/></strong></td>
        <td>${application.email}</td>
    </tr>
    <tr>
        <td><strong><fmt:message key="page.application.beds"/></strong></td>
        <td>${application.bed}</td>
    </tr>
    <tr>
        <td><strong><fmt:message key="page.application.type"/></strong></td>
        <td>${application.type}</td>
    </tr>
    <tr>
        <td><strong><fmt:message key="page.application.time"/></strong></td>
        <td>${application.time}
        </td>
    </tr>
    <form action="${pageContext.request.contextPath}/admin" method="post">
        <input type="hidden" name="id" value="${application.id}">
        <input type="hidden" name="email" value="${application.email}">
        <input type="hidden" name="time" value="${application.time}">
        <input type="hidden" name="command" value="admin">
        <tr>
            <td><strong><fmt:message key="page.processing.processing"/></strong></td>
            <td>
                <c:choose>
                    <c:when test="${not empty requestScope.roomsFree}">
                        <select name="status" id="status" style="background: azure">
                            <option value="true"><fmt:message key="page.processing.confirm"/></option>
                            <option value="false"><fmt:message key="page.processing.reject"/></option>
                        </select>

                    </c:when>
                    <c:otherwise>
                        <select name="status" id="status" style="background: azure">
                            <option value="false"><fmt:message key="page.processing.reject"/></option>
                        </select>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>
                <h3 style="color: #eaffeb"><fmt:message key="page.processing.free"/></h3>
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty requestScope.roomsFree}">
                        <select name="roomFree" id="roomFree" style="background: azure">
                            <c:forEach var="roomsFree" items="${requestScope.roomsFree}">
                                <option>${roomsFree.id}, <fmt:message key="page.application.beds"/> ${roomsFree.bed},
                                    <fmt:message key="page.application.type"/> ${roomsFree.type},
                                    <fmt:message key="page.processing.cost"/> ${roomsFree.price}
                                </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="page.processing.norooms"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <button type="submit" style="background: lightcyan"><fmt:message key="page.processing.process"/></button>
    </form>
    </td>
    <td>
        <h3 for="room" style="color: #eaffeb"><fmt:message key="page.processing.all"/></h3>
        <select name="room" id="room" style="background: azure">
            <c:forEach var="rooms" items="${requestScope.rooms}">
                <option><fmt:message key="page.processing.room"/> ${rooms.id},
                    <fmt:message key="page.application.beds"/> ${rooms.bed},
                    <fmt:message key="page.application.type"/> ${rooms.type},
                    <fmt:message key="page.processing.cost"/> ${rooms.price}
                </option>
            </c:forEach>
        </select>
    </td>
    </tr>
</table>
<br>
</div>
<br><br>
<form action="${pageContext.request.contextPath}/helloadmin" method="get">
    <button type="submit" style="background: lightcyan"><fmt:message key="page.application.home"/></button>
    <input type="hidden" name="command" value="helloadmin">
</form>
</body>
</html>

