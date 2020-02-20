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
    <h2>${user.username}</h2>

            <div class="form-group form-group--buttons">
                <a href="/admin/users/edit?id=${user.id}"> <button class="btn">Zmień hasło</button></a>
                <a href="/admin/users/block?id=${user.id}"><button class="btn">Zablokuj</button></a>
            </div>
        </form>
</section>

<jsp:include page="footer.jsp"/>
<script src="/resources/js/app.js"></script>
</body>
</html>
