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
            <h2>Dodaj fundację</h2>
        </c:when>
        <c:otherwise>
            <h2>Edytuj fundację</h2>
        </c:otherwise>
    </c:choose>
        <form:form modelAttribute="inst" action="/admin/instututions/edit" method="post">
            <div class="form-group">
                <form:input path="name"/>
                <form:errors path="name" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
            </div>
            <div class="form-group">
                <form:textarea path="description" rows="5" />
                <form:errors path="description" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
            </div>

            <form:hidden path="id" />

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
        </form:form>
</section>

<jsp:include page="../footer.jsp"/>
<script src="/resources/js/app.js"></script>
</body>
</html>
