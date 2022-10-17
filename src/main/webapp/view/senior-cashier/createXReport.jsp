<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<%--<c:set var="sort" value="${requestScope.sort == null ? 'default' : requestScope.sort}"/>--%>
<%--<c:set var="order" value="${requestScope.order == null ? 'asc' : requestScope.order}"/>--%>
<c:set var="sort" value="${sessionScope.reportSortBy}"/>
<c:set var="order" value="${sessionScope.reportOrderBy}"/>
<c:set var="perPage" value="${sessionScope.reportTotalPerPage}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.senior-cashier.createXReport"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
        <script src="<c:url value="/js?file=tableParams.js"/>"></script>
        <script>addTableParamListener("/senior-cashier/create-x-report")</script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp">
            <jsp:param name="mainUrl" value="/senior-cashier"/>
        </jsp:include>

        <div class="m-3">
            <a class="dropdown-toggle" style="text-decoration: none" href="#" data-bs-toggle="dropdown" role="button">
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
        </div>

        <div class="d-flex mb-2">
            <span class="ms-0 me-4">
                <jsp:include page="sortSelect.jsp">
                    <jsp:param name="sort" value="${sort}"/>
                </jsp:include>
            </span>

            <span>
                <jsp:include page="../util/orderSelect.jsp">
                    <jsp:param name="order" value="${order}"/>
                </jsp:include>
            </span>
        </div>

        <div>
            <jsp:include page="reportTable.jsp"/>

            <jsp:include page="pagination.jsp"/>

            <jsp:include page="../util/showPerPageSelect.jsp">
                <jsp:param name="perPage" value="${perPage}"/>
            </jsp:include>
        </div>

        <c:if test="${param.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.senior-cashier.createXReport"/></p>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
