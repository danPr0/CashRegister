<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="form-group">
        <label for="username"><fmt:message key="label.username"/></label>
            <input type="text" name="username" required minlength="3" maxlength="16" class="form-control"
                   placeholder="<fmt:message key="placeHolder.username"/>" id="username"/>
        <div class="invalid-feedback">Must be 3-16 characters</div>
    </div>
</fmt:bundle>