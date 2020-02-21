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
<c:choose>
  <c:when test="${user.blocked==0}">
    <jsp:include page="header-confirm.jsp"/>
  </c:when>
  <c:otherwise>
    <jsp:include page="header-blocked.jsp"/>
  </c:otherwise>
</c:choose>

  <jsp:include page="../footer.jsp"/>

  <script src="/resources/js/app.js"></script>
  </body>
</html>