<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вхід</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/login_style.css">
</head>
<body>
<div class="container">
    <form class="login-form" method="post" action="/main/">
        <input type="text" hidden name="command" value="LOGIN_USER" />
        <h3 class="login-form__header">Увійти</h3>
        <div class="form-group">
            <label class="login-form__username">Логін</label>
            <input class="login-form__username-input" type="text" placeholder="Username" name="login" autocomplete="off"/>
        </div>
        <div class="form-group">
            <label class="login-form__password">Пароль</label>
            <input class="login-form__password-input" type="password" placeholder="Password" name="password" />
        </div>
        <div class="form-actions">
            <button type="submit" class="login-btn">Увійти</button>
        </div>

        <div class="create-account">
            <p>
                <a href="/register.jsp">Зареєструватись</a>
            </p>
        </div>
    </form>
</div>


</body>
</html>
