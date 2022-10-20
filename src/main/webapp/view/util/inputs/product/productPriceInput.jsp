<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="prodPrice" class="form-label"><fmt:message key="label.productPrice"/></label>
        <input type="text" name="${param.name}" required pattern="([1-9]{1}[0-9]{0,6})|([0-9]{1,7}(.){1}[0-9]{0,2})" class="form-control"
               placeholder="<fmt:message key="placeholder.productPrice"/>" id="prodPrice" value="<c:out value="${param.presetValue}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.productPrice"/></div>
    </div>
</fmt:bundle>