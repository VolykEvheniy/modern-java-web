<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Реєстрація</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/register_style.css">
</head>
<body>
<div class="container">
    <form class="register-form" method="post" action="/main/">
        <input type="text" hidden name="command" value="REGISTER_USER" />
        <h3 class="register-form__header">Форма реєстрації</h3>
        <div class="form-group">
            <label class="register-form__username">Логін</label>
            <input class="register-form__username-input" type="text" placeholder="Username" name="login" autocomplete="off" />
        </div>
        <div class="form-group">
            <label class="register-form__password">Пароль</label>
            <input class="register-form__password-input" type="password" id="register_password" placeholder="Password" name="password" />
        </div>
        <div class="form-actions">
            <button type="submit" id="register-submit-btn" class="register-btn">Зареєструватися</button>
        </div>
    </form>

</div>

</body>
</html>
