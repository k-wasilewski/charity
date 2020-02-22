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
            <h2><spring:message code="userDonations" text="default"/></h2>

            <!-- SLIDE 1 -->
            <div class="help--slides active" data-id="1">
                <div style="text-align: center">
                    <a href="/auth/donation"><button class="btn--small" style="display: inline-block">
                        <spring:message code="passDonation" text="default"/></button></a>
                </div>
                <ul class="help--slides-items">
                    <c:forEach items="${donations}" var="don" varStatus="status">
                        ${don.id};
                        <div style="font-size: x-large; font-weight: bold" class="title">${don.categories} <spring:message code="toInstit" text="default"/> ${don.institution}</div>
                        <div style="font-size: large" class="subtitle">${don.quantity} <spring:message code="bags" text="default"/></div>
                        <br>
                        <div style="font-size: large" class="subtitle"><spring:message code="passAddress" text="default"/> </div>
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
                                <div style="font-size: large" class="subtitle"><spring:message code="pickedupOn" text="default"/></div>
                            </c:when>
                            <c:otherwise>
                                <div style="font-size: large" class="subtitle"><spring:message code="pickedupOff" text="default"/></div>
                            </c:otherwise>
                        </c:choose>
                        <br>
                        <div style="font-size: large" class="subtitle"><spring:message code="created" text="default"/> ${don.created}</div>

                        <div style="font-size: large" class="form-group form-group--buttons">
                            <a href="/auth/donation?id=${don.id}"><button class="btn"><spring:message code="edit" text="default"/></button></a>
                            <a onclick="return confirm('<spring:message code="usure" text="default"/>')" href="/auth/donation/del?id=${don.id}"><button class="btn"><spring:message code="del" text="default"/></button></a>
                        </div>
                    </c:forEach>

                </ul>
            </div>

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
