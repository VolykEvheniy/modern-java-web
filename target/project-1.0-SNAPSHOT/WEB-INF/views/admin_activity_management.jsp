<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Керування активністю</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/admin_activity_management.styles.css">
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
    <div class="content">
        <div class="creation_section">
            <h3>Прикріпіть користувачів до цієї активності</h3>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="PIN_ACTIVITY">
                <input type="text" hidden name="activityId" value="${sessionScope.activityId}" />
                <label>
                    User:
                    <select name="userId">
                        <option value="select" selected="selected"></option>
                        <c:forEach items="${sessionScope.users}" var="user">
                            <option value="${user.id}">${user.login}</option>
                        </c:forEach>
                    </select>
                </label>
                <button type="submit">Прикріпити</button>
            </form>
        </div>
        <div class="table_wrapper">
            <ul class="responsive-table">
                <li class="table-header">
                    <div class="col col-1">User Id</div>
                    <div class="col col-2">Time Spent, m</div>
                    <div class="col col-3">Completed</div>
                    <div class="col col-4">Assigned At</div>
                    <div class="col col-5">Unpin</div>
                </li>
                <c:forEach items="${sessionScope.user2activities}" var="user2activity" varStatus="loop">
                    <li class="table-row">
                        <div class="col col-1" data-label="User Id">
                            <c:out value="${user2activity.user.id}"/>
                        </div>
                        <div class="col col-2" data-label="Time Spent">
                            <c:out value="${user2activity.timeSpent / 60}"/>
                        </div>
                        <div class="col col-3" data-label="Completed">
                            <c:out value="${user2activity.completed}"/>
                        </div>
                        <div class="col col-4" data-label="Assigned At">
                            <c:out value="${user2activity.assigned_at}"/>
                        </div>
                        <div class="col col-5" data-label="Unpin">
                            <form method="post" action="/main/">
                                <input hidden name="command" value="UNPIN_ACTIVITY">
                                <input type="text" hidden name="activityId" value="${user2activity.activity.id}" />
                                <input type="text" hidden name="userId" value="${user2activity.user.id}" />
                                <button type="submit" class="unpin_button">
                                    Unpin
                                </button>
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
