<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="firstName" class="form-label"><fmt:message key="label.firstName"/></label>
        <input type="text" name="firstName" required pattern="[-.\sA-Za-z]+|[-.\sА-Яа-яЁёЇїІіЄєҐґ']+" maxlength="48"
               class="form-control" placeholder="<fmt:message key="placeHolder.firstName"/>" id="firstName"
               value="${param.firstName}"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.name"/></div>
    </div>
</fmt:bundle>