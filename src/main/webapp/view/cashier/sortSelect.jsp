<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<script src="<c:url value="/js?file=tableSortSelect.js"/>"></script>

<fmt:bundle basename="messages">
    <div>
        <select id="sortSelect">
            <option value="default" <c:if test="${param.sort == 'default'}">selected disabled</c:if>>
                <fmt:message key="table.check.sort.default"/>
            </option>
            <option value="productId" <c:if test="${param.sort == 'productId'}">selected disabled</c:if>>
                <fmt:message key="table.check.sort.productId"/>
            </option>
            <option value="quantity" <c:if test="${param.sort == 'quantity'}">selected disabled</c:if>>
                <fmt:message key="table.check.sort.quantity"/>
            </option>
        </select>

        <script>sort("/cashier/add-product-to-check?sortBy=")</script>
    </div>
</fmt:bundle>