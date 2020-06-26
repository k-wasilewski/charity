<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title><spring:message code="title" text="default"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<c:choose>
    <c:when test="${user.blocked==0}">
        <jsp:include page="header-auth.jsp"/>

        <section class="form--steps">
            <div class="form--steps-instructions">
                <div class="form--steps-container">
                    <h3><spring:message code="important" text="default"/></h3>
                    <p data-step="1" class="active">
                        <spring:message code="fillUp1" text="default"/>
                    </p>
                    <p data-step="2">
                        <spring:message code="fillUp1" text="default"/>
                    </p>
                    <p data-step="3">
                        <spring:message code="fillUp2" text="default"/>
                    </p>
                    <p data-step="4"><spring:message code="fillUp3" text="default"/></p>
                </div>
            </div>

            <div class="form--steps-container">
                <div class="form--steps-counter"><spring:message code="step" text="default"/> <span>1</span>/4</div>

                <form:form modelAttribute="donation" method="post">
                    <!-- STEP 1: class .active is switching steps -->
                    <div data-step="1" class="active">
                        <c:if test="${msg==true}">
                            <div class="form-group--checkbox" style="color: red; font-family: 'Merriweather', serif;
                            font-weight: 300; font-style: normal; font-size: 2rem; margin-bottom: 10px;">
                                <spring:message code="formError" text="default"/>
                            </div>
                        </c:if>
                        <h3><spring:message code="fillUp4" text="default"/></h3>
                        <div class="form-group form-group--checkbox">
                            <c:forEach items="${categories}" var="cat">
                                <label>
                                    <span class="checkbox springcheckbox"></span>
                                    <form:checkbox path="categories" label="${cat.name}"
                                                  cssClass="things ${cat.name}" value="${cat}"/>
                                </label>
                            </c:forEach>
                            <form:errors path="categories" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                        </div>

                        <div class="form-group form-group--buttons">
                            <button type="button" class="btn next-step"><spring:message code="next" text="default"/></button>
                        </div>
                    </div>

                    <!-- STEP 2 -->
                    <div data-step="2">
                        <h3><spring:message code="fillUp5" text="default"/></h3>

                        <div class="form-group form-group--inline">
                            <label>
                                <spring:message code="quantity" text="default"/>
                                <form:input cssClass="quantity" path="quantity"/>
                            </label>
                            <form:errors path="quantity" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                        </div>

                        <div class="form-group form-group--buttons">
                            <button type="button" class="btn prev-step"><spring:message code="back" text="default"/></button>
                            <button type="button" class="btn next-step"><spring:message code="next" text="default"/></button>
                        </div>
                    </div>



                    <!-- STEP 4 -->
                    <div data-step="3">
                        <h3><spring:message code="fillUp6" text="default"/></h3>

                        <c:forEach items="${institutions}" var="inst">
                            <div class="form-group form-group--checkbox">
                                <label>
                                    <form:radiobutton cssClass="institution ${inst.name}" path="institution" value="${inst}"/>
                                    <span class="checkbox radio"></span>
                                    <span class="description">
                              <div class="title">"${inst.name}"</div>
                              <div class="subtitle">
                                ${inst.description}
                              </div>
                            </span>
                                </label>
                            </div>
                        </c:forEach>
                        <form:errors path="institution" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />

                        <div class="form-group form-group--buttons">
                            <button type="button" class="btn prev-step"><spring:message code="back" text="default"/></button>
                            <button type="button" class="btn next-step"><spring:message code="next" text="default"/></button>
                        </div>
                    </div>

                    <!-- STEP 5 -->
                    <div data-step="4">
                        <h3><spring:message code="fillUp7" text="default"/></h3>

                        <div class="form-section form-section--columns">
                            <div class="form-section--column">
                                <h4><spring:message code="address" text="default"/></h4>
                                <div class="form-group form-group--inline">
                                    <label> <spring:message code="street" text="default"/> <form:input cssClass="street" path="street" /> </label>
                                    <form:errors path="street" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                </div>

                                <div class="form-group form-group--inline">
                                    <label> <spring:message code="city" text="default"/> <form:input cssClass="city" path="city"/> </label>
                                    <form:errors path="city" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                </div>

                                <div class="form-group form-group--inline">
                                    <label>
                                        <spring:message code="zipcode" text="default"/> <form:input cssClass="zipcode" path="zipCode" />
                                    </label>
                                    <form:errors path="zipCode" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                </div>
                            </div>

                             <div class="form-section--column">
                                 <h4><spring:message code="pickupDatetime" text="default"/></h4>
                                 <div class="form-group form-group--inline">
                                    <label> <spring:message code="date" text="default"/> <form:input cssClass="pickupdate" type="date" path="pickUpDate"/> </label>
                                     <form:errors path="pickUpDate" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                 </div>

                                 <div class="form-group form-group--inline">
                                    <label>
                                        <spring:message code="time" text="default"/>
                                        <form:input cssClass="pickuptime" type="time" path="pickUpTime" />
                                    </label>
                                     <form:errors path="pickUpTime" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                 </div>

                                 <div class="form-group form-group--inline">
                                    <label>
                                        <spring:message code="comments" text="default"/>
                                        <form:textarea cssClass="pickupcomment" path="pickUpComment" rows="5"/>
                                    </label>
                                     <form:errors path="pickUpComment" cssStyle="color: red" cssClass="form-group--checkbox" element="div" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group form-group--buttons">
                            <button type="button" class="btn prev-step">Wstecz</button>
                            <button type="button" class="btn next-step summaryBtn">Dalej</button>
                        </div>
                    </div>

                    <!-- STEP 6 -->
                    <div data-step="5">
                        <h3><spring:message code="donationSummary" text="default"/></h3>

                        <div class="summary">
                            <div class="form-section">
                                <h4><spring:message code="giveOut" text="default"/></h4>
                                <ul>
                                    <li>
                                        <span class="icon icon-bag"></span>
                                        <span class="summary--text"
                                        ><span id="summaryQuantity"></span> <spring:message code="bagsThings" text="default"/> <span id="summaryThings"></span></span
                                        >
                                    </li>

                                    <li>
                                        <span class="icon icon-hand"></span>
                                        <span class="summary--text"
                                        ><spring:message code="forInstit" text="default"/> "<span id="summaryInstitution"></span>"</span
                                        >
                                    </li>
                                </ul>
                            </div>

                            <div class="form-section form-section--columns">
                                <div class="form-section--column">
                                    <h4><spring:message code="pickupAddress" text="default"/></h4>
                                    <ul>
                                        <li><span id="summaryStreet"></span> </li>
                                        <li><span id="summaryCity"></span></li>
                                        <li><span id="summaryZipcode"></span></li>
                                    </ul>
                                </div>

                                <div class="form-section--column">
                                    <h4><spring:message code="pickupDatetime1" text="default"/></h4>
                                    <ul>
                                        <li><span id="summaryPickupdate"></span></li>
                                        <li><span id="summaryPickuptime"></span></li>
                                        <li><span id="summaryPickupcomment"></span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <form:hidden path="owner" value="${username}" />

                        <div class="form-group form-group--buttons">
                            <button type="button" class="btn prev-step"><spring:message code="back" text="default"/></button>
                            <button type="Asubmit" class="btn"><spring:message code="confirm" text="default"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </section>
    </c:when>
    <c:otherwise>
        <jsp:include page="../header/header-blocked.jsp"/>
    </c:otherwise>
</c:choose>

<jsp:include page="../footer/footer.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/resources/js/app.js"></script>
</body>
</html>
