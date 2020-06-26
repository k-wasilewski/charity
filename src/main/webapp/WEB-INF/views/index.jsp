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
    <c:when test="${username==null}">
        <jsp:include page="header/header.jsp"/>
    </c:when>
    <c:when test="${user.blocked==1}">
        <jsp:include page="header/header-blocked.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="auth/header-auth.jsp"/>
    </c:otherwise>
</c:choose>

<section class="stats">
    <div class="container container--85">
        <div class="stats--item">
            <em>${donationsQuantities}</em>

            <h3><spring:message code="quantityThings" text="default"/></h3>
            <p><spring:message code="bagsMsg" text="default"/></p>
        </div>

        <div class="stats--item">
            <em>${donationsSum}</em>
            <h3><spring:message code="quantityThings2" text="default"/></h3>
            <p><spring:message code="thingsMsg" text="default"/></p>
        </div>

    </div>
</section>

<section id="steps">
    <h2><spring:message code="4steps" text="default"/></h2>

    <div class="steps--container">
        <div class="steps--item">
            <span class="icon icon--hands"></span>
            <h3><spring:message code="selThings" text="default"/></h3>
            <p><spring:message code="things" text="default"/></p>
        </div>
        <div class="steps--item">
            <span class="icon icon--arrow"></span>
            <h3><spring:message code="pack" text="default"/></h3>
            <p><spring:message code="garbageBags" text="default"/></p>
        </div>
        <div class="steps--item">
            <span class="icon icon--glasses"></span>
            <h3><spring:message code="decide" text="default"/></h3>
            <p><spring:message code="trusted" text="default"/></p>
        </div>
        <div class="steps--item">
            <span class="icon icon--courier"></span>
            <h3><spring:message code="courier" text="default"/></h3>
            <p><spring:message code="courierTime" text="default"/></p>
        </div>
    </div>

    <a href="/register" class="btn btn--large"><spring:message code="newAcc" text="default"/></a>
</section>

<section id="about-us">
    <div class="about-us--text">
        <h2><spring:message code="about" text="default"/></h2>
        <p><spring:message code="aboutMsg" text="default"/></p>
        <img src="<c:url value="/resources/images/signature.svg"/>" class="about-us--text-signature" alt="Signature"/>
    </div>
    <div class="about-us--image"><img src="<c:url value="/resources/images/about-us.jpg"/>" alt="People in circle"/>
    </div>
</section>

<section id="help">
    <h2><spring:message code="who" text="default"/></h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p><spring:message code="whoDb" text="default"/></p>

        <ul class="help--slides-items">
            <c:forEach items="${institutions}" var="inst" varStatus="status">
                <jsp:useBean id="status" type="javax.servlet.jsp.jstl.core.LoopTagStatus" />


                    <c:if test="<%=status.getCount()%2==0%>">
                        <div class="col">
                            <div class="title">${inst.name}</div>
                            <div class="subtitle">${inst.description}</div>
                        </div>
                        </li>
                    </c:if>

                    <c:if test="<%=status.getCount()%2!=0%>">
                <li>
                    <div class="col">
                        <div class="title">${inst.name}</div>
                        <div class="subtitle">${inst.description}</div>
                    </div>
                        <c:if test="<%=status.isLast()%>"></li></c:if>
                </c:if>
            </c:forEach>

        </ul>
    </div>

</section>

<jsp:include page="footer/footer.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
