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
    <title><spring:message code="title" text="default"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<c:choose>
    <c:when test="${user.blocked==0}">
        <jsp:include page="header-auth.jsp"/>

        <section class="login-page">
        <h2><spring:message code="changePwd" text="default"/></h2>
        <form action="/auth/changePwd" method="post">
            <div class="form-group">
                <input type="email" name="username" value="${username}" placeholder="Email" />
            </div>
            <div class="form-group">
                <input type="password" name="old-pwd" placeholder="<spring:message code="oldPwd" text="default"/>" />
            </div>
            <div class="form-group">
                <input type="password" name="new-pwd" placeholder="<spring:message code="newPwd1" text="default"/>" />
            </div>
            <div class="form-group">
                <input type="password" name="new-pwd2" placeholder="<spring:message code="newPwd2" text="default"/>" />
            </div>

            <div class="form-group form-group--buttons">
                <button class="btn" type="submit"><spring:message code="changePwd" text="default"/></button>
            </div>
        </form>
        </section>

        <jsp:include page="../footer/footer.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../header/header-blocked.jsp"/>
        <jsp:include page="../footer/footer.jsp"/>
    </c:otherwise>
</c:choose>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
