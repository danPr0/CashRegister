<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<c:set var="page" value="${param.page}"/>
<c:if test="${param.page == null}">
    <c:set var="page" value="1"/>
</c:if>

<c:set var="sort" value="${requestScope.sort}"/>
<c:if test="${requestScope.sort == null}">
    <c:set var="sort" value="default"/>
</c:if>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.senior-cashier.createXReport"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
        <script src="<c:url value="/js?file=tableSortSelect.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="../menu/menu.jsp"/>
        <jsp:include page="../util/backToMainButton.jsp"/>

        <div class="m-3">
        <span class="mr-4">
            <select id="sortSelect">
                <option value="default" <c:if test="${sort == 'default'}">selected disabled</c:if>>
                    <fmt:message key="table.check.sort.default"/>
                </option>
                <option value="createdBy" <c:if test="${sort == 'createdBy'}">selected disabled</c:if>>
                    <fmt:message key="table.report.sort.createdBy"/>
                </option>
                <option value="quantity" <c:if test="${sort == 'quantity'}">selected disabled</c:if>>
                    <fmt:message key="table.report.sort.quantity"/>
                </option>
                <option value="price" <c:if test="${sort == 'price'}">selected disabled</c:if>>
                    <fmt:message key="table.report.sort.price"/>
                </option>
            </select>
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
        </span>
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
            <div class="mb-3">
                <table class="table-light table-striped table-hover col-7">
                    <tr>
                        <th><fmt:message key="table.report.createdBy"/></th>
                        <th><fmt:message key="table.report.createdAt"/></th>
                        <th><fmt:message key="table.report.quantity"/></th>
                        <th><fmt:message key="table.report.price"/></th>
                    </tr>
                    <c:forEach var="i" items="${requestScope.report}">
                        <tr>
                            <td>${i.createdBy}</td>
                            <td>${i.closed_at}</td>
                            <td>${i.items_quantity}</td>
                            <td>${i.total_price}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="m-3">
                <ul class="pagination">
                    <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                        <a href="<c:url value="/senior-cashier/create-x-report?page=${page - 1}&sortBy=${sort}"/>"
                           class="page-link">
                            <fmt:message key="pagination.previous"/>
                        </a>
                    </li>
                    <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
                        <li class="page-item <c:if test="${page == i}">active</c:if>">
                            <a href="<c:url value="/senior-cashier/create-x-report?page=${i}&sortBy=${sort}"/>"
                               class="page-link">
                                    ${i}
                            </a>
                        </li>
                    </c:forEach>
                    <li class="page-item <c:if test="${page == requestScope.nOfPages}">disabled</c:if>">
                        <a href="<c:url value="/senior-cashier/create-x-report?page=${page + 1}&sortBy=${sort}"/>"
                           class="page-link">
                            <fmt:message key="pagination.next"/>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <c:if test="${param.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.senior-cashier.createXReport"/></p>
        </c:if>
    </div>

    <script>sort("/senior-cashier/create-x-report?sortBy=")</script>
    </body>
</fmt:bundle>
</html>
