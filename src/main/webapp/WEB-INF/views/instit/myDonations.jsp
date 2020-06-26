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
<c:choose>
    <c:when test="${user.blocked==0}">
        <jsp:include page="header-instit.jsp"/>

        <section id="help">
            <h2><spring:message code="donationsForInstit" text="default"/></h2>

            <!-- SLIDE 1 -->
            <div class="help--slides active" data-id="1">
                <ul class="help--slides-items">
                    <c:forEach items="${donations}" var="don" varStatus="status">
                        <div style="font-size: x-large; font-weight: bold" class="title">${don.categories}</div>
                        <div style="font-size: large" class="subtitle">${don.quantity} <spring:message code="bags" text="default"/></div>
                        <br>
                        <div style="font-size: large" class="subtitle"><spring:message code="institAddress" text="default"/> </div>
                        <div style="font-size: large" class="subtitle">${don.street}</div>
                        <div style="font-size: large" class="subtitle">${don.zipCode}, ${don.city}</div>
                        <br>
                        <div style="font-size: large" class="subtitle"><spring:message code="institDatetime" text="default"/></div>
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
                            <c:choose>
                                <c:when test="${don.pickedUp==0}">
                                    <a onclick="return confirm('<spring:message code="statusConfirm" text="default"/>')" href="/instit/donation/pickedupOn?id=${don.id}"><button class="btn"><spring:message code="pickedOn" text="default"/></button></a>
                                </c:when>
                                <c:otherwise>
                                    <a onclick="return confirm('<spring:message code="statusConfirm" text="default"/>')" href="/instit/donation/pickedupOff?id=${don.id}"><button class="btn"><spring:message code="pickedOff" text="default"/></button></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>

                </ul>

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
