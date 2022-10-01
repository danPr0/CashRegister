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
            <p>Check is empty!</p>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/senior-cashier/cancel-product-in-check"/>">Cancel product in check</a><br/>
            <a href="<c:url value="/senior-cashier/cancel-check"/>">Cancel check</a><br/>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${requestScope.reportSize == 0}">
            <p>
                No checks were committed after last Z report<br/>
                You can download last Z report:
            </p>
            <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=csv"/>" target="_blank">Csv</a><br/>
            <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=pdf"/>" target="_blank">Pdf</a><br/>
            <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=xls"/>" target="_blank">Xls</a><br/>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/senior-cashier/create-x-report"/>">Create X report</a><br/>
            <a href="<c:url value="/senior-cashier/create-z-report"/>">Create Z report</a><br/>
        </c:otherwise>
    </c:choose>

    <jsp:include page="/view/menu/menu.jsp"/>

</fmt:bundle>
</body>
</html>
