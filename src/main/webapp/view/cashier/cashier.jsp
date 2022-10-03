<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<c:set var="checkTotalPerPage" value="1" scope="application"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Main</title>
    <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container p-3 h-100 bg-dark text-white">
    <jsp:include page="../menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <c:choose>
            <c:when test="${requestScope.checkSize == 0}">
                <a href="<c:url value="/cashier/add-product-to-check"/>" class="btn btn-primary">Create check</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/cashier/add-product-to-check"/>" class="btn btn-primary">Add product to check</a>
                <a href="<c:url value="/cashier/close-check"/>" class="btn btn-secondary">Close check</a>
            </c:otherwise>
        </c:choose>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="text-success pl-3">${param.success}</p>
    </fmt:bundle>
</div>
</body>
</html>
