<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Облік часу</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.styles.css">
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
    <c:if test="${sessionScope.user.role == null}">
        <div class="welcome">
            <h2>Будь ласка авторизуйтесь</h2>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.role.name.equals('ADMIN')}">
        <div class="admin_menu">
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_ALL_USERS" />
                <a onclick="this.closest('form').submit();return false;" style="cursor: pointer;">Users managing</a>
            </form>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_ACTIVITIES" />
                <a onclick="this.closest('form').submit();return false;" style="cursor: pointer;">Activities managing</a>
            </form>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_ALL_CATEGORIES" />
                <a onclick="this.closest('form').submit();return false;" style="cursor: pointer;">Categories managing</a>
            </form>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_REPORT" />
                <a onclick="this.closest('form').submit();return false;" style="cursor: pointer;">Users report</a>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.role.name.equals('USER')}">
        <div class="user_menu">
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_ALL_USER_ACTIVITIES" />
                <a onclick="this.closest('form').submit();return false;" style="cursor: pointer;">My activities</a>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
