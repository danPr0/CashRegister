<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <%--        <label for="orderSelect" class="form-label"><fmt:message key="label.orderBy"/></label>--%>
    <select id="orderSelect" class="form-select form-select-sm w-auto">
        <option value="asc" <c:if test="${param.order == 'asc'}">selected disabled</c:if>>
            <fmt:message key="select.orderBy.asc"/>
        </option>
        <option value="desc" <c:if test="${param.order == 'desc'}">selected disabled</c:if>>
            <fmt:message key="select.orderBy.desc"/>
        </option>
    </select>
</fmt:bundle>