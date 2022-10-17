<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="d-flex">
        <label for="perPageSelect" class="me-2"><fmt:message key="label.showPerPage"/></label>
        <select id="perPageSelect" class="form-select form-select-sm w-auto me-0">
            <option value="1" <c:if test="${param.perPage == '1'}">selected disabled</c:if>>
                1
            </option>
            <option value="2" <c:if test="${param.perPage == '2'}">selected disabled</c:if>>
                2
            </option>
            <option value="3" <c:if test="${param.perPage == '3'}">selected disabled</c:if>>
                3
            </option>
            <option value="5" <c:if test="${param.perPage == '5'}">selected disabled</c:if>>
                5
            </option>
        </select>
    </div>
</fmt:bundle>