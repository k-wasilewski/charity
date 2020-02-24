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
<footer>
    <div id="contact">
        <h2><spring:message code="contactUs" text="default"/></h2>
        <h3><spring:message code="contactForm" text="default"/></h3>
        <form class="form--contact" action="/msg" method="post">
            <div class="form-group form-group--50"><input type="text" name="name" placeholder="<spring:message code="name" text="default"/>"/></div>
            <div class="form-group form-group--50"><input type="text" name="surname" placeholder="<spring:message code="surname" text="default"/>"/></div>

            <div class="form-group"><textarea name="message" placeholder="<spring:message code="msg" text="default"/>" rows="1"></textarea></div>

            <button class="btn" type="submit"><spring:message code="send" text="default"/></button>
        </form>
    </div>
    <div class="bottom-line">
        <span class="bottom-line--copy">Copyright&copy; Kuba Wasilewski, 2018</span>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"><img src="<c:url value="/resources/images/icon-facebook.svg"/>"/></a> <a href="#"
                                                                                            class="btn btn--small"><img
                src="<c:url value="/resources/images/icon-instagram.svg"/>"/></a>
        </div>
    </div>
</footer>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
