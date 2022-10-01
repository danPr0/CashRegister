<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prodName"><fmt:message key="label.productName"/></label>
        <input type="text" name="productName" required pattern="(([a-z][A-Z])+\s*)+|([а-я][А-Я]+\s*)+" minlength="1"
               maxlength="50" class="form-control" id="prodName"
               placeholder="<fmt:message key="placeHolder.productName"/>"/>
        <div class="invalid-feedback">You can use only letters and whitespaces.</div>

    </div>
</fmt:bundle>