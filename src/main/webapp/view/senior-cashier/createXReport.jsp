<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="sort" value="${requestScope.sort == null ? 'default' : requestScope.sort}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.senior-cashier.createXReport"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="../menu/menu.jsp"/>
        <jsp:include page="../util/backToMainButton.jsp"/>

        <div class="m-3">
            <jsp:include page="sortSelect.jsp">
                <jsp:param name="sort" value="${sort}"/>
            </jsp:include>
<%--            <a class="card-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">--%>
<%--                <fmt:message key="table.report.sort.dropdown"/>--%>
<%--            </a>--%>
<%--            <span class="dropdown-menu py-1" style="min-width: 3rem">--%>
<%--                <a class="dropdown-item" href="<c:url value="/senior-cashier/create-x-report?sortBy=default"/>"--%>
<%--                   <c:if test="${sort == 'default'}">style="pointer-events: none; color: gray"</c:if>>--%>
<%--                    <fmt:message key="table.report.sort.default"/>--%>
<%--                </a>--%>
<%--                <a class="dropdown-item" href="<c:url value="/senior-cashier/create-x-report?sortBy=createdBy"/>"--%>
<%--                   <c:if test="${sort == 'createdBy'}">style="pointer-events: none; color: gray"</c:if>>--%>
<%--                    <fmt:message key="table.report.sort.createdBy"/>--%>
<%--                </a>--%>
<%--                <a class="dropdown-item" href="<c:url value="/senior-cashier/create-x-report?sortBy=quantity"/>"--%>
<%--                   <c:if test="${sort == 'quantity'}">style="pointer-events: none; color: gray"</c:if>>--%>
<%--                    <fmt:message key="table.report.sort.quantity"/>--%>
<%--                </a>--%>
<%--                <a class="dropdown-item" href="<c:url value="/senior-cashier/create-x-report?sortBy=price"/>"--%>
<%--                   <c:if test="${sort == 'price'}">style="pointer-events: none; color: gray"</c:if>>--%>
<%--                    <fmt:message key="table.report.sort.price"/>--%>
<%--                </a>--%>
<%--            </span>--%>
            <span>
            <a class="card-link dropdown-toggle" href="#" data-bs-toggle="dropdown" role="button">
                <fmt:message key="button.download"/>
            </a>
            <span class="dropdown-menu" style="min-width: 3rem; line-height: 0.5rem">
                <a href="<c:url value="/senior-cashier/download-report?reportType=x&format=csv"/>"
                   target="_blank" class="dropdown-item px-2 py-2">
                    .csv
                </a>
                <a href="<c:url value="/senior-cashier/download-report?reportType=x&format=pdf"/>"
                   target="_blank" class="dropdown-item px-2 py-2">
                    .pdf
                </a>
                <a href="<c:url value="/senior-cashier/download-report?reportType=x&format=xls"/>"
                   target="_blank" class="dropdown-item px-2 py-2">
                    .xls
                </a>
            </span>
        </span>
        </div>
        <div>
            <jsp:include page="reportTable.jsp"/>

            <jsp:include page="pagination.jsp">
                <jsp:param name="sort" value="${sort}"/>
            </jsp:include>
        </div>

        <c:if test="${param.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.senior-cashier.createXReport"/></p>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
