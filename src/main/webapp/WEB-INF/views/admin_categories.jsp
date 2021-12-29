<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Категорії</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/admin_categories.styles.css">
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
            <h3>Створити категорію</h3>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="CREATE_CATEGORY">
                <input type="text" name="categoryName" placeholder="Name..." />
                <button type="submit">Створити</button>
            </form>
        </div>
        <div class="table_wrapper">
            <ul class="responsive-table">
                <li class="table-header">
                    <div class="col col-1">Name</div>
                    <div class="col col-2">Delete</div>
                </li>
                <c:forEach items="${sessionScope.categories}" var="category" varStatus="loop">
                    <li class="table-row">
                        <div class="col col-1" data-label="Name">
                            <c:out value="${category.name}"/>
                        </div>
                        <div class="col col-2" data-label="Delete">
                            <c:if test="${!category.deleted}">
                                <form method="post" action="/main/">
                                    <input hidden name="command" value="DELETE_CATEGORY">
                                    <input type="text" name="categoryId" hidden value="${category.id}" />
                                    <button type="submit" class="delete_button">
                                        Delete
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
