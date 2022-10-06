<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="prodPrice"><fmt:message key="label.productPrice"/></label>
        <input type="text" name="price" required pattern="[0-9]+(.)?[0-9]*" class="form-control"
               placeholder="<fmt:message key="placeholder.productPrice"/>" id="prodPrice"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.productPrice"/></div>
    </div>
</fmt:bundle>