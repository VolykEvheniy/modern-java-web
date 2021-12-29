<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Активності</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/base_styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/admin_activities.styles.css">
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
            <h3>Створити активність</h3>
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="INSERT_ACTIVITY">
                <label>
                    Name:
                    <input type="text" name="activityName" />
                </label>
                <label>
                    Category:
                    <select name="categoryId">
                        <option value="select" selected="selected"></option>
                        <c:forEach items="${sessionScope.categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <label>
                    Description:
                    <textarea name="description"></textarea>
                </label>
                <button type="submit">Створити</button>
            </form>
        </div>
        <div class="sort_filter_bar">
            <form method="post" action="/main/">
                <input type="text" hidden name="command" value="SHOW_ACTIVITIES">
                <input type="text" hidden name="activityId" value="${sessionScope.activityId}" />
                <label>
                    Критерій сортування:
                    <select name="sortCriteria">
                        <option value="NAME" selected="selected">За назвою</option>
                        <option value="CATEGORY">За категорією</option>
                        <option value="USERS_COUNT">За кількістю користувачів</option>
                    </select>
                </label>
                <label>
                    Порядок сортування:
                    <select name="sortOrder">
                        <option value="ASC" selected="selected">За зростанням</option>
                        <option value="DESC">За спаданням</option>
                    </select>
                </label>
                <label>
                    Фільтрувати за:
                    <select name="filterCriteria">
                        <option value="" selected="selected"></option>
                        <option value="CATEGORY">За категорією</option>
                    </select>
                </label>
                <label>
                    Значення для фільтрування:
                    <input type="text" name="filterValue" />
                </label>
                <button type="submit">Пошук</button>
            </form>
        </div>
        <div class="table_wrapper">
            <ul class="responsive-table">
                <li class="table-header">
                    <div class="col col-1">Name</div>
                    <div class="col col-2">Category</div>
                    <div class="col col-3">Description</div>
                    <div class="col col-4">Delete</div>
                    <div class="col col-5">Open</div>
                </li>
                <c:forEach items="${sessionScope.activities}" var="activity" varStatus="loop">
                    <li class="table-row">
                        <div class="col col-1" data-label="Name">
                            <c:out value="${activity.name}"/>
                        </div>
                        <div class="col col-2" data-label="Category">
                            <c:out value="${activity.category.name}"/>
                        </div>
                        <div class="col col-3" data-label="Description">
                            <c:out value="${activity.description}"/>
                        </div>
                        <div class="col col-4" data-label="Delete">
                            <c:if test="${!activity.deleted}">
                                <form method="post" action="/main/">
                                    <input hidden name="command" value="DELETE_ACTIVITY">
                                    <input type="text" name="activityId" hidden value="${activity.id}" />
                                    <button type="submit" class="delete_button">
                                        Delete
                                    </button>
                                </form>
                            </c:if>
                        </div>
                        <div class="col col-5" data-label="Open">
                            <form method="post" action="/main/">
                                <input hidden name="command" value="SHOW_ACTIVITY_MANAGEMENT">
                                <input type="text" name="activityId" hidden value="${activity.id}" />
                                <button type="submit" class="open_button">
                                    Open
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
