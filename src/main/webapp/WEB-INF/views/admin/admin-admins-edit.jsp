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
<jsp:include page="header-admin-regular.jsp"/>

<section class="login-page">
    <c:choose>
        <c:when test="${add==true}">
            <h2>Dodaj administratora</h2>
        </c:when>
        <c:otherwise>
            <h2>Edytuj administratora</h2>
        </c:otherwise>
    </c:choose>
    <c:if test="${msg==true}">
        <h3><p style="color: red">Hasła muszą się zgadzać</p></h3>
    </c:if>
    <form method="post" action="/admin/admins/edit">
        <div class="form-group">
            <c:choose>
                <c:when test="${empty admin.username}">
                    <input type="email" name="username" placeholder="Email"/>
                </c:when>
                <c:otherwise>
                    <input type="email" name="username" placeholder="${admin.username}"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="Hasło" />
        </div>
        <div class="form-group">
            <input type="password" name="password2" placeholder="Powtórz hasło" />
        </div>
        <input hidden type="number" name="id" value="${admin.id}">

            <div class="form-group form-group--buttons">
                <button class="btn" type="submit">
                    <c:choose>
                        <c:when test="${add==true}">
                            Dodaj
                        </c:when>
                        <c:otherwise>
                            Edytuj
                        </c:otherwise>
                    </c:choose>
                </button>
            </div>
        </form>
</section>

<jsp:include page="../footer.jsp"/>
<script src="/resources/js/app.js"></script>
</body>
</html>
