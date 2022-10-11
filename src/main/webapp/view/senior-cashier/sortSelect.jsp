<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<script src="<c:url value="/js?file=tableSortSelect.js"/>"></script>

<fmt:bundle basename="messages">
    <span class="mr-4">
        <select id="sortSelect">
            <option value="default" <c:if test="${param.sort == 'default'}">selected disabled</c:if>>
                <fmt:message key="table.check.sort.default"/>
            </option>
            <option value="createdBy" <c:if test="${param.sort == 'createdBy'}">selected disabled</c:if>>
                <fmt:message key="table.report.sort.createdBy"/>
            </option>
            <option value="quantity" <c:if test="${param.sort == 'quantity'}">selected disabled</c:if>>
                <fmt:message key="table.report.sort.quantity"/>
            </option>
            <option value="price" <c:if test="${param.sort == 'price'}">selected disabled</c:if>>
                <fmt:message key="table.report.sort.price"/>
            </option>
        </select>

        <script>sort("/senior-cashier/create-x-report?sortBy=")</script>
    </span>
</fmt:bundle>