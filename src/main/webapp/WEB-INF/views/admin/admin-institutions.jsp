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
<jsp:include page="header-admin-regular.jsp"/>

<section id="help">
    <h2><spring:message code="institList" text="default"/></h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <div style="text-align: center">
            <a href="/admin/institutions/add"><button class="btn--small" style="display: inline-block">
                <spring:message code="addInstit" text="default"/></button></a>
        </div>
        <ul class="help--slides-items">
            <c:forEach items="${institutions}" var="inst" varStatus="status">
                <jsp:useBean id="status" type="javax.servlet.jsp.jstl.core.LoopTagStatus" />


                <c:if test="<%=status.getCount()%2==0%>">
                    <div class="col">
                        <div class="title">${inst.name}</div>
                        <div class="subtitle">${inst.description}</div>
                        <div class="form-group form-group--buttons">
                            <a href="/admin/institutions/edit?id=${inst.id}"><button class="btn"><spring:message code="edit" text="default"/></button></a>
                            <a onclick="return confirm('<spring:message code="usure" text="default"/>')" href="/admin/institutions/del?id=${inst.id}"><button class="btn"><spring:message code="del" text="default"/></button></a>
                        </div>
                    </div>
                    </li>
                </c:if>

                <c:if test="<%=status.getCount()%2!=0%>">
                    <li>
                    <div class="col">
                        <div class="title">${inst.name}</div>
                        <div class="subtitle">${inst.description}</div>
                        <div class="form-group form-group--buttons">
                            <a href="/admin/institutions/edit?id=${inst.id}"><button class="btn"><spring:message code="edit" text="default"/></button></a>
                            <a onclick="return confirm('<spring:message code="usure" text="default"/>')" href="/admin/institutions/del?id=${inst.id}"><button class="btn"><spring:message code="del" text="default"/></button></a>
                        </div>
                    </div>
                    <c:if test="<%=status.isLast()%>"></li></c:if>
                </c:if>
            </c:forEach>

        </ul>
    </div>

</section>

<jsp:include page="../footer/footer.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
