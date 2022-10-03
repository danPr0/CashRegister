<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prodName"><fmt:message key="label.productName"/></label>
<%--        pattern="(([a-z][A-Z])+\s*)+|([а-я][А-Я]+\s*)+"--%>
        <input type="text" name="productName" required maxlength="50" class="form-control"
               placeholder="<fmt:message key="placeHolder.productName"/>" id="prodName"/>
        <div class="invalid-feedback">Product name required</div>

    </div>
</fmt:bundle>