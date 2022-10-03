<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Main</title>
    <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container p-3 h-100 bg-dark text-white">
    <jsp:include page="/view/menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <div class="row">
            <div class="col"><hr></div>
            <div class="col-auto">Check</div>
            <div class="col"><hr></div>
        </div>

        <div class="mb-5">
            <c:choose>
                <c:when test="${requestScope.checkSize == 0}">
                    <p>Check is empty!</p>
                </c:when>
                <c:otherwise>
                    <p>Check size is ${requestScope.checkSize}</p>
                    <a href="<c:url value="/senior-cashier/cancel-product-in-check"/>" class="card-link">Cancel product in check</a><br/>
                    <a href="<c:url value="/senior-cashier/cancel-check"/>" class="card-link">Cancel check</a><br/>
                </c:otherwise>
            </c:choose>
        </div>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="text-success pl-3">${param.success}</p>

        <div class="row">
            <div class="col"><hr></div>
            <div class="col-auto">Report</div>
            <div class="col"><hr></div>
        </div>

        <c:choose>
            <c:when test="${requestScope.reportSize == 0}">
                <div>
                    <span>
                        No checks were committed after last Z report<br/>
                        You can download last Z report:
                    </span>
                    <a class="card-link dropdown-toggle" href="#" id="fileTypeDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        file
                    </a>
                    <div class="dropdown-menu" style="min-width: 1rem; line-height: 0.5rem">
                        <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=csv"/>"
                           target="_blank" class="dropdown-item px-2 py-2">.csv</a><br/>
                        <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=pdf"/>"
                           target="_blank" class="dropdown-item px-2 py-2">.pdf</a><br/>
                        <a href="<c:url value="/senior-cashier/download-report?reportType=z&format=xls"/>"
                           target="_blank" class="dropdown-item px-2 py-2">.xls</a><br/>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/senior-cashier/create-x-report"/>" class="card-link">Create X report</a><br/>
                <a href="<c:url value="/senior-cashier/create-z-report"/>" class="card-link">Create Z report</a><br/>
            </c:otherwise>
        </c:choose>
    </fmt:bundle>
</div>
</body>
</html>
