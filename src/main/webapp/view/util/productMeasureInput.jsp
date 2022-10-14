<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<script src="<c:url value="/js?file=controlMeasure.js"/>"></script>

<fmt:bundle basename="messages">
    <div class="mb-3">
        <label for="productMeasure" class="form-label"><fmt:message key="label.productMeasure"/></label>
        <select class="form-select" required name="measure" id="productMeasure">
            <option
                    <c:if test="${param.measure == null}">selected</c:if> disabled value="">
                <fmt:message key="select.productMeasure.default"/>
            </option>
            <option
                    <c:if test="${param.measure == 'weight'}">selected</c:if> value="weight">
                <fmt:message key="select.productMeasure.weight"/>
            </option>
            <option
                    <c:if test="${param.measure == 'apiece'}">selected</c:if> value="apiece">
                <fmt:message key="select.productMeasure.apiece"/>
            </option>
        </select>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
    </div>
</fmt:bundle>