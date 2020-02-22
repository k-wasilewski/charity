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
<jsp:include page="header-admin-regular.jsp"/>

<section id="help">
    <h2><spring:message code="adminList" text="default"/></h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <div style="text-align: center">
            <a href="/admin/admins/add"><button class="btn--small" style="display: inline-block">
                <spring:message code="addAdmin" text="default"/></button></a>
        </div>
        <ul class="help--slides-items">
            <c:forEach items="${admins}" var="admin" varStatus="status">
                <jsp:useBean id="status" type="javax.servlet.jsp.jstl.core.LoopTagStatus" />


                <c:if test="<%=status.getCount()%2==0%>">
                    <div class="col">
                        <div class="title">${admin.username}</div>
                        <div class="form-group form-group--buttons">
                            <a href="/admin/admins/edit?id=${admin.id}"><button class="btn"><spring:message code="edit" text="default"/></button></a>
                            <a onclick="return confirm('<spring:message code="usure" text="default"/>')" href="/admin/admins/del?id=${admin.id}"><button class="btn"><spring:message code="del" text="default"/></button></a>
                        </div>
                    </div>
                    </li>
                </c:if>

                <c:if test="<%=status.getCount()%2!=0%>">
                    <li>
                    <div class="col">
                        <div class="title">${admin.username}</div>
                        <div class="form-group form-group--buttons">
                            <a href="/admin/admins/edit?id=${admin.id}"><button class="btn"><spring:message code="edit" text="default"/></button></a>
                            <a onclick="return confirm('<spring:message code="usure" text="default"/>')" href="/admin/admins/del?id=${admin.id}"><button class="btn"><spring:message code="del" text="default"/></button></a>
                        </div>
                    </div>
                    <c:if test="<%=status.isLast()%>"></li></c:if>
                </c:if>
            </c:forEach>

        </ul>
    </div>

</section>

<jsp:include page="../footer.jsp"/>
<script src="/resources/js/app.js"></script>
</body>
</html>
