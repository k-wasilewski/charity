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
    <title><spring:message code="title" text="default"/></title>

    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">

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
                    <li><a href="/auth/all-donations"><spring:message code="myDonations" text="default"/></a></li>
                    <li><a href="/logout"><spring:message code="logout" text="default"/></a></li>
                </ul>
            </li>
        </ul>

        <div style="font-size: 14px; color: rebeccapurple; visibility: hidden" id="msgInstitToUser">
            <span style="font-size: 16px; font-weight: bold"><spring:message code="lastMsg" text="default"/>
            </span>: <spring:message code="msgInstitU" text="default"/> <span id="msgInstitU">
            </span> <spring:message code="msgReceivedU" text="default"/> <span id="msgUserU"></span>
            <span id="msgQuantU"></span> <spring:message code="msgBagsInThemI" text="default"/> <span id="msgThingsU"></span>
        </div>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <c:if test="${username!=null}">
                <li><a href="/auth/donation" class="btn btn--without-border"><spring:message code="passThings" text="default"/></a></li>
            </c:if>
            <li><a href="/#steps" class="btn btn--without-border"><spring:message code="steps" text="default"/></a></li>
            <li><a href="/#about-us" class="btn btn--without-border"><spring:message code="about" text="default"/></a></li>
            <li><a href="/#help" class="btn btn--without-border"><spring:message code="institutions" text="default"/></a></li>
            <li><a href="/#contact" class="btn btn--without-border"><spring:message code="contact" text="default"/></a></li>
        </ul>
    </nav>
    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                <c:if test="${msg2==true}">
                    <p style="color: red"><spring:message code="pwdWrong" text="default"/></p>
                </c:if>
                <c:if test="${msg3==true}">
                    <p style="color: red"><spring:message code="pwdChangeFailed" text="default"/></p>
                </c:if>
                <c:if test="${msg4==true}">
                    <p style="font-weight: bold"> <spring:message code="pwdChangedSucc" text="default"/></p>
                </c:if>
                <br>
                <spring:message code="greeting1" text="default"/><br/>
                <spring:message code="greeting2" text="default"/>
            </h1>
        </div>
    </div>
</header>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
