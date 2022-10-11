<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="prodQuantity" class="form-label"><fmt:message key="label.productQuantity"/></label>
        <input type="text" name="quantity" required pattern="([1-9]{1}[0-9]{0,3})|([0-9]{1,4}(.){1}[0-9]{1,3})"
               class="form-control" placeholder="<fmt:message key="placeHolder.productQuantity"/>" id="prodQuantity"
               value="${param.quantity}"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.productQuantity"/></div>
    </div>
</fmt:bundle>
