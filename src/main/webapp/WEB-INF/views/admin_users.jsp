<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Користувачі</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/admin_users.styles.css">
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
                <div class="col col-2">Deleted</div>
                <div class="col col-3">Role</div>
                <div class="col col-4">Delete User</div>
            </li>
            <c:forEach items="${sessionScope.users}" var="user" varStatus="loop">
                <li class="table-row">
                    <div class="col col-1" data-label="Login">
                        <c:out value="${user.login}"/>
                    </div>
                    <div class="col col-2" data-label="Deleted">
                        <c:out value="${user.deleted}"/>
                    </div>
                    <div class="col col-3" data-label="Role">
                        <c:out value="${user.role.name}"/>
                    </div>
                    <div class="col col-4" data-label="Delete/Restore">
                        <c:if test="${!user.deleted}">
                            <form method="post" action="/main/">
                                <input type="text" hidden name="command" value="DELETE_USER" />
                                <input type="text" name="userId" hidden value="${user.id}" />
                                <button type="submit" class="delete_button">
                                    Delete
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${user.deleted}">
                            <form method="post" action="/main/">
                                <input type="text" hidden name="command" value="RESTORE_USER" />
                                <input type="text" name="userId" hidden value="${user.id}" />
                                <button type="submit" class="delete_button">
                                    Restore
                                </button>
                            </form>
                        </c:if>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
