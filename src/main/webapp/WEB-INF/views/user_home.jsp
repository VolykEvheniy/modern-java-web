<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Активності</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/user_home.styles.css">
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
                <div class="col col-1">Name</div>
                <div class="col col-2">Description</div>
                <div class="col col-3">Time Spent, m</div>
                <div class="col col-4">Set Time</div>
            </li>
            <c:forEach items="${sessionScope.userActivities}" var="user2activity" varStatus="loop">
                <li class="table-row">
                    <div class="col col-1" data-label="Name">
                        <c:out value="${user2activity.name}"/>
                    </div>
                    <div class="col col-2" data-label="Description">
                        <c:out value="${user2activity.description}"/>
                    </div>
                    <div class="col col-3" data-label="Time Spent">
                        <c:out value="${user2activity.timeSpent / 60}"/>
                    </div>
                    <div class="col col-4" data-label="Set Time Spent">
                        <form method="post" action="/main/">
                            <input hidden name="command" value="SET_TIME_SPENT">
                            <input type="text" hidden name="userToActivityId" value="${user2activity.id}" />
                            <input type="text" name="timeSpent" placeholder="Minutes..." />
                            <button type="submit" class="set_button">
                                Set
                            </button>
                        </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
