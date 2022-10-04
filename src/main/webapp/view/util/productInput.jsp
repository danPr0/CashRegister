<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prod"><fmt:message key="label.product"/></label>
        <input type="text" name="product" required maxlength="50" class="form-control"
               placeholder="<fmt:message key="placeHolder.product"/>" id="prod"/>
        <div class="invalid-feedback">Must be 1-50 characters</div>
    </div>
</fmt:bundle>