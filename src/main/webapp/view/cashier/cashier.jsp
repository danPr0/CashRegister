<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<c:set var="checkTotalPerPage" value="1" scope="application"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.main"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
    <div class="container p-3 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="../menu/menu.jsp"/>

        <c:choose>
            <c:when test="${requestScope.checkSize == 0}">
                <a href="<c:url value="/cashier/add-product-to-check"/>" class="btn btn-primary">
                    <fmt:message key="button.createCheck"/>
                </a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/cashier/add-product-to-check"/>" class="btn btn-primary">
                    <fmt:message key="button.addProductToCheck"/>
                </a>
                <a href="<c:url value="/cashier/close-check"/>" class="btn btn-secondary">
                    <fmt:message key="button.closeCheck"/>
                </a>
            </c:otherwise>
        </c:choose>

        <c:if test="${param.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.cashier.closeCheck"/></p>
        </c:if>
        <c:if test="${param.success == 'true'}">
            <p class="text-success pl-3"><fmt:message key="msg.success.cashier.closeCheck"/></p>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
