<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prodQuantity"><fmt:message key="label.productQuantity"/></label>
        <input type="text" name="quantity" required pattern="[0-9]+" minlength="1" maxlength="9" class="form-control"
               placeholder="<fmt:message key="placeHolder.productQuantity"/>" id="prodQuantity"/>
        <div class="invalid-feedback">Number required</div>
    </div>
</fmt:bundle>
