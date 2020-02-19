<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<jsp:include page="header-auth.jsp"/>

<section class="login-page">
<h2>Zmień hasło</h2>
<form action="/changePwd" method="post">
    <div class="form-group">
        <input type="email" name="username" value="${username}" placeholder="Email" />
    </div>
    <div class="form-group">
        <input type="password" name="old-pwd" placeholder="Stare hasło" />
    </div>
    <div class="form-group">
        <input type="password" name="new-pwd" placeholder="Nowe hasło" />
    </div>
    <div class="form-group">
        <input type="password" name="new-pwd2" placeholder="Powtórz nowe hasło" />
    </div>

    <div class="form-group form-group--buttons">
        <button class="btn" type="submit">Zmień hasło</button>
    </div>
</form>
</section>

<jsp:include page="footer.jsp"/>

<script src="js/app.js"></script>
</body>
</html>
