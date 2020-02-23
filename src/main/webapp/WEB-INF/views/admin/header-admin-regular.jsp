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
<header class="header--institutions">

    <div class="dropdown">
        <button class="dropbtn">
            <spring:message code="changeLang" text="default"/>
        </button>
        <div class="dropdown-content">
            <a href="?lang=en">EN</a>
            <a href="?lang=pl">PL</a>
        </div>
    </div>

    <nav class="container container--70">
        <ul class="nav--actions">
            <li class="logged-user">
                <spring:message code="greeting" text="default"/> ${username}
                <ul class="dropdown">
                    <li><a href="/auth/changePwd"><spring:message code="changePwd" text="default"/></a></li>
                    <li><a href="/logout"><spring:message code="logout" text="default"/></a></li>
                </ul>
            </li>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="/admin/institutions" class="btn btn--without-border"><spring:message code="institutions" text="default"/></a></li>
            <li><a href="/admin/admins" class="btn btn--without-border"><spring:message code="admins" text="default"/></a></li>
            <li><a href="/admin/users" class="btn btn--without-border"><spring:message code="users" text="default"/></a></li>
            <li><a href="/#contact" class="btn btn--without-border"><spring:message code="contact" text="default"/></a></li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                <c:if test="${msg==true}">
                <p style="color: red"><spring:message code="cantDelYourself" text="default"/></p>
                </c:if>
            </h1>
        </div>
    </div>

</header>
<script src="/resources/js/app.js"></script>
</body>
</html>
