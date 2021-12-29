<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Звіт</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/admin_report.styles.css">
</head>

<body>
<div class="header">
    <div class="container">
        <a class="header-text" href="/">Облік часу</a>
        <nav class="menu">
            <ul>
                <c:if test="${sessionScope.user == null}">
                    <li class="headerli1">
                        <form method="post" action="/main/">
                            <input type="text" hidden name="command" value="OPEN_LOGIN" />
                            <a onclick="this.closest('form').submit();return false;">Login</a>
                        </form>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="headerli2">
                        <form method="post" action="/main/">
                            <input type="text" hidden name="command" value="LOGOUT_USER" />
                            <a onclick="this.closest('form').submit();return false;">Logout</a>
                        </form>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<div class="container">
    <div class="table_wrapper">
        <ul class="responsive-table">
            <li class="table-header">
                <div class="col col-1">Login</div>
                <div class="col col-2">Total Activities Amount</div>
                <div class="col col-3">Total Time Spent</div>
            </li>
            <c:forEach items="${sessionScope.userReports}" var="userReport" varStatus="loop">
                <li class="table-row">
                    <div class="col col-1" data-label="Login">
                        <c:out value="${userReport.login}"/>
                    </div>
                    <div class="col col-2" data-label="Activities Amount">
                        <c:out value="${userReport.activitiesAmount}"/>
                    </div>
                    <div class="col col-3" data-label="Total Time Spent">
                        <c:out value="${userReport.totalTimeSpent / 60}"/>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
