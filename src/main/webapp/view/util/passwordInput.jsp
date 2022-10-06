<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="password"><fmt:message key="label.password"/></label>
        <input type="password" name="password" required minlength="8" maxlength="48" class="form-control"
               placeholder="<fmt:message key="placeHolder.password"/>" id="password"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>
    </div>
</fmt:bundle>