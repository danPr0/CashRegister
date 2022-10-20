<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="secondName" class="form-label"><fmt:message key="label.secondName"/></label>
        <input type="text" name="secondName" required pattern="[-.sA-Za-z]+|[-.sА-Яа-яЁёЇїІіЄєҐґ']+" maxlength="48"
               class="form-control" placeholder="<fmt:message key="placeHolder.secondName"/>" id="secondName"
               value="<c:out value="${param.secondName}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.name"/></div>
    </div>
</fmt:bundle>