<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prodId"><fmt:message key="label.productId"/></label>
        <input type="text" name="productId" required pattern="[0-9]+" class="form-control"
               placeholder="<fmt:message key="placeHolder.productId"/>" id="prodId"/>
        <div class="invalid-feedback">You can use only 0-9 symbols</div>
    </div>
</fmt:bundle>