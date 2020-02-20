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
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<jsp:include page="headerSmall.jsp"/>

<section class="login-page">
    <h2>Przypomnij hasło</h2>
    <form action="/forgotten" method="post">
        <div class="form-group">
            <input type="email" name="email" placeholder="Email" />
        </div>

        <div class="form-group form-group--buttons">
            <a href="/register" class="btn btn--without-border">Załóż konto</a>
            <button class="btn" type="submit">Wyślij mi wiadomość</button>
        </div>
    </form>
</section>

<jsp:include page="footer.jsp"/>
<script src="/resources/js/app.js"></script>
</body>
</html>
