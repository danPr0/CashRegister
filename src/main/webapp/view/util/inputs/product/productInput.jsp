<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="prod" class="form-label"><fmt:message key="label.product"/></label>
        <input type="text" name="product" required maxlength="60" class="form-control"
               placeholder="<fmt:message key="placeHolder.product"/>" id="prod" value="<c:out value="${param.product}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
    </div>
</fmt:bundle>