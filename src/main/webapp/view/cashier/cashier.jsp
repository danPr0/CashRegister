<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Cashier</title>
</head>
<body>
<fmt:bundle basename="messages">

    <c:choose>
        <c:when test="${requestScope.checkSize == 0}">
            <a href="<c:url value="/cashier/add-product-to-check"/>">Create check</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/cashier/add-product-to-check"/>">Add product to check</a><br/>
            <a href="<c:url value="/cashier/close-check"/>">Close check</a>
        </c:otherwise>
    </c:choose>

    <jsp:include page="/view/menu/languageInterface.jsp"/>

</fmt:bundle>
</body>
</html>
