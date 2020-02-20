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

    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<jsp:include page="header-admin.jsp"/>

<section id="help">
    <h2>Lista fundacji w bazie danych</h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
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

<jsp:include page="footer.jsp"/>
</body>
</html>
