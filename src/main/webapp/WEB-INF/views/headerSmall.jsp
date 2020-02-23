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
    <title><spring:message code="title" text="default"/><</title>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--main-page">

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
            <li><a href="/login" class="btn btn--small btn--without-border"><spring:message code="login" text="default"/></a></li>
            <li><a href="/register" class="btn btn--small btn--highlighted"><spring:message code="register" text="default"/></a></li>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="/#steps" class="btn btn--without-border"><spring:message code="steps" text="default"/></a></li>
            <li><a href="/#about-us" class="btn btn--without-border"><spring:message code="about" text="default"/></a></li>
            <li><a href="/#help" class="btn btn--without-border"><spring:message code="institutions" text="default"/></a></li>
            <li><a href="/#contact" class="btn btn--without-border"><spring:message code="contact" text="default"/></a></li>
        </ul>
    </nav>
    <div style="height: 20px"></div>
    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                <c:if test="${msg1==true}">
                <p style="color: red"><spring:message code="invalidToken" text="default"/></p>
                </c:if>
                <c:if test="${msg2==true}">
                    <p style="color: red"><spring:message code="expiredToken" text="default"/></p>
                </c:if>
                <c:if test="${msg3==true}">
                    <p style="color: red"><spring:message code="pwdNotMatch" text="default"/></p>
                </c:if>
                <c:if test="${msg4==true}">
                    <p style="color: red"><spring:message code="pwdChangeFailed" text="default"/></p>
                </c:if>
                <c:if test="${msg10==true}">
                    <p style="color: red; font-size: 18px"><spring:message code="pwdInvalid" text="default"/> </p>
                </c:if>
            </h1>
        </div>
    </div>
</header>
<script src="/resources/js/app.js"></script>
</body>
</html>
