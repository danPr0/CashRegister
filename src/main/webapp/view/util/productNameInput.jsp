<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="prodName" class="form-label"><fmt:message key="label.productName"/></label>
        <input type="text" name="productName" required maxlength="60" class="form-control"
               placeholder="<fmt:message key="placeHolder.productName"/>" id="prodName" value="${param.productName}"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
    </div>
</fmt:bundle>