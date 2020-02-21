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
<c:choose>
    <c:when test="${user.blocked==0}">
        <jsp:include page="header-auth.jsp"/>

        <section id="help">
            <h2>Twoje przekazane dary</h2>

            <!-- SLIDE 1 -->
            <div class="help--slides active" data-id="1">
                <div style="text-align: center">
                    <a href="/auth/donation"><button class="btn--small" style="display: inline-block">
                        Przekaż nowy dar</button></a>
                </div>
                <ul class="help--slides-items">
                    <c:forEach items="${donations}" var="don" varStatus="status">
                        ${don.id};
                        <div style="font-size: x-large; font-weight: bold" class="title">${don.categories} dla Fundacji ${don.institution}</div>
                        <div style="font-size: large" class="subtitle">${don.quantity} worków</div>
                        <br>
                        <div style="font-size: large" class="subtitle">Adres przekazania: </div>
                        <div style="font-size: large" class="subtitle">${don.street}</div>
                        <div style="font-size: large" class="subtitle">${don.zipCode}, ${don.city}</div>
                        <br>
                        <div style="font-size: large" class="subtitle">Termin przekazania:</div>
                        <div style="font-size: large" class="subtitle">${don.pickUpTime}, ${don.pickUpDate}</div>
                        <br>
                        <div style="font-size: large" class="subtitle">${don.pickUpComment}</div>
                        <br>
                        <c:choose>
                            <c:when test="${don.pickedUp==1}">
                                <div style="font-size: large" class="subtitle">Dar został odebrany</div>
                            </c:when>
                            <c:otherwise>
                                <div style="font-size: large" class="subtitle">Dar nie został jeszcze odebrany</div>
                            </c:otherwise>
                        </c:choose>
                        <br>
                        <div style="font-size: large" class="subtitle">Data utworzenia wpisu: ${don.created}</div>

                        <div style="font-size: large" class="form-group form-group--buttons">
                            <a href="/auth/donation?id=${don.id}"><button class="btn">Edytuj</button></a>
                            <a onclick="return confirm('Czy na pewno chcesz usunąć?')" href="/auth/donation/del?id=${don.id}"><button class="btn">Usuń</button></a>
                        </div>
                    </c:forEach>

                </ul>

        </section>

        <jsp:include page="../footer.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="header-blocked.jsp"/>
        <jsp:include page="../footer.jsp"/>
    </c:otherwise>
</c:choose>
<script src="/resources/js/app.js"></script>
</body>
</html>
