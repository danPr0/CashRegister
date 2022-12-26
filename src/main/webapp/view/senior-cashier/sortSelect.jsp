<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mr-4">
        <select id="sortSelect" class="form-select form-select-sm w-auto">
            <option value="defaultSort" <c:if test="${param.sort == 'defaultSort'}">selected disabled</c:if>>
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

<%--        <script>sort("/senior-cashier/create-x-report?sortBy=")</script>--%>
    </div>
</fmt:bundle>