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

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--main-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="/#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                <c:if test="${msg==true}">
                    <p style="color: red">Hasła muszą się zgadzać</p>
                </c:if>
                <c:if test="${msg2==true}">
                    <p style="color: red">Użytkownik o takim emailu już istnieje</p>
                </c:if>
                <c:if test="${msg5==true}">
                    <p style="color: red"> Rejestracja nieudana </p>
                </c:if>
                <c:if test="${msg6==true}">
                    <p style="color: red"> Niepoprawny token aktywacyjny </p>
                </c:if>
                <c:if test="${msg7==true}">
                    <p style="color: red"> Token aktywacyjny przeterminowany </p>
                </c:if>
                <c:if test="${msg8==true}">
                    <p style="font-weight: bold"> Na Twój email wysłaliśmy link aktywacyjny, ważny przez godzinę </p>
                </c:if>
                <c:if test="${msg9==true}">
                    <p style="font-weight: bold"> Zmieniono hasło </p>
                </c:if>
                <c:if test="${msg10==true}">
                    <p style="color: red"> Hasło musi zawierać co najmniej jedną małą literę, jedną dużą literę,
                        jedną cyfrę i jeden znak specjalny !@#$%^&* oraz mieć co najmniej 8 znaków </p>
                </c:if>
                Zacznij pomagać!<br/>
                Oddaj niechciane rzeczy w zaufane ręce
            </h1>
        </div>
    </div>
</header>
<script src="/resources/js/app.js"></script>
</body>
</html>
