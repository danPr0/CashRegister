<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prod"><fmt:message key="label.productId"/></label>
        <input type="text" name="product" required maxlength="50" class="form-control"
               placeholder="<fmt:message key="placeHolder.productId"/>" id="prod"/>
        <div class="invalid-feedback">Must be 1-50 characters</div>
    </div>
</fmt:bundle>