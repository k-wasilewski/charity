<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>

    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <c:if test="${username!=null}">
                <li><a href="/auth/donation" class="btn btn--without-border">Przekaż dary</a></li>
            </c:if>
            <li><a href="/#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <h2>
            <c:choose>
                <c:when test="${msg==true}">
                    <p style="color: red"> Nie ma takiego użytkownika w bazie. </p>
                </c:when>
                <c:otherwise>
                    Na podanego maila wysłaliśmy Twoje nowe hasło.
                </c:otherwise>
            </c:choose>
        </h2>
    </div>
</header>
<script src="<c:url value="resources/js/app.js"/>"></script>
</body>
</html>
