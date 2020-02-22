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
        <jsp:include page="header-instit.jsp"/>

        <section id="help">
            <h2>Dary przekazane dla Twojej instytucji</h2>

            <!-- SLIDE 1 -->
            <div class="help--slides active" data-id="1">
                <ul class="help--slides-items">
                    <c:forEach items="${donations}" var="don" varStatus="status">
                        <div style="font-size: x-large; font-weight: bold" class="title">${don.categories}</div>
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
                            <c:choose>
                                <c:when test="${don.pickedUp==0}">
                                    <a onclick="return confirm('Czy na pewno chcesz zmienić status?')" href="/instit/donation/pickedupOn?id=${don.id}"><button class="btn">Oznacz jako odebrany</button></a>
                                </c:when>
                                <c:otherwise>
                                    <a onclick="return confirm('Czy na pewno chcesz zmienić status??')" href="/instit/donation/pickedupOff?id=${don.id}"><button class="btn">Oznacz jako nieodebrany</button></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>

                </ul>

        </section>

        <jsp:include page="../footer.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../header-blocked.jsp"/>
        <jsp:include page="../footer.jsp"/>
    </c:otherwise>
</c:choose>
<script src="/resources/js/app.js"></script>
</body>
</html>
