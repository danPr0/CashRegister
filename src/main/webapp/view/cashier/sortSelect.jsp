<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <select id="sortSelect" class="form-select form-select-sm w-auto">
        <option value="defaultSort" <c:if test="${param.sort == 'defaultSort'}">selected disabled</c:if>>
            <fmt:message key="table.check.sort.default"/>
        </option>
        <option value="productId" <c:if test="${param.sort == 'productId'}">selected disabled</c:if>>
            <fmt:message key="table.check.sort.productId"/>
        </option>
        <option value="quantity" <c:if test="${param.sort == 'quantity'}">selected disabled</c:if>>
            <fmt:message key="table.check.sort.quantity"/>
        </option>
    </select>
</fmt:bundle>