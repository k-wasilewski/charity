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
  <jsp:include page="headerSmall.jsp"/>

    <section class="login-page">
      <h2><spring:message code="newAcc" text="default"/></h2>
      <form method="post">
        <div class="form-group">
          <input type="email" name="username" placeholder="Email" />
        </div>
        <div class="form-group">
          <input type="password" name="password" placeholder="<spring:message code="password" text="default"/>" />
        </div>
        <div class="form-group">
          <input type="password" name="password2" placeholder="<spring:message code="password2" text="default"/>" />
        </div>

        <div class="form-group form-group--buttons">
          <a href="/login" class="btn btn--without-border"><spring:message code="login" text="default"/></a>
          <button class="btn" type="submit"><spring:message code="newAcc" text="default"/></button>
        </div>
      </form>
    </section>

  <jsp:include page="footer.jsp"/>
  <script src="/resources/js/app.js"></script>
  </body>
</html>
