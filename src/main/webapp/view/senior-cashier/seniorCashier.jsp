<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.main"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp"/>

        <div class="row">
            <div class="col">
                <hr>
            </div>
            <div class="col-auto"><fmt:message key="name.check"/></div>
            <div class="col">
                <hr>
            </div>
        </div>

        <div class="mb-2">
            <c:choose>
                <c:when test="${requestScope.checkSize == 0}">
                    <p><fmt:message key="msg.info.emptyCheck"/></p>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="msg.info.checkSize"/> ${requestScope.checkSize}</p>
                    <a href="<c:url value="/senior-cashier/cancel-product-in-check"/>" style="text-decoration: none">
                        <fmt:message key="button.cancelProductInCheck"/>
                    </a><br/>
                    <a href="<c:url value="/senior-cashier/cancel-check"/>" style="text-decoration: none">
                        <fmt:message key="button.cancelCheck"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

        <div>
            <c:if test="${param.error == 'true'}">
                <p class="text-danger"><fmt:message key="msg.error.senior-cashier.cancelCheck"/></p>
            </c:if>
            <c:if test="${param.success == 'true'}">
                <p class="text-success"><fmt:message key="msg.success.senior-cashier.cancelCheck"/></p>
            </c:if>
        </div>

        <div class="row">
            <div class="col">
                <hr>
            </div>
            <div class="col-auto"><fmt:message key="name.report"/></div>
            <div class="col">
                <hr>
            </div>
        </div>

        <div>
            <c:choose>
                <c:when test="${requestScope.reportSize == 0}">
                    <div>
                    <span>
                        <fmt:message key="msg.info.emptyZReport"/><br/>
                        <fmt:message key="msg.info.downloadZReport"/>
                    </span>
                        <a class="dropdown-toggle" style="text-decoration: none" href="#" role="button" data-bs-toggle="dropdown">
                            <fmt:message key="button.file"/>
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
                    <a href="<c:url value="/senior-cashier/create-x-report"/>" style="text-decoration: none">
                        <fmt:message key="button.createXReport"/>
                    </a><br/>
                    <a href="<c:url value="/senior-cashier/create-z-report"/>" style="text-decoration: none">
                        <fmt:message key="button.createZReport"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
