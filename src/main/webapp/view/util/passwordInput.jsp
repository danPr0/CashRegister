<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="password" class="form-label"><fmt:message key="label.password"/></label>
        <div class="input-group">
            <input type="password" name="password" required minlength="8" maxlength="48" class="form-control"
                   placeholder="<fmt:message key="placeHolder.password"/>" id="password" value="${param.password}"/>
            <button class="btn btn-link px-0 py-0" type="button"
                    style="background-color: white; border-radius: 4px; margin-left: 1px" id="togglePassword">
                <img src="<c:url value="/images?file=password_eye.png"/>" style="height: 2rem; width: 2rem" alt="sp"/>
            </button>
            <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>
        </div>

            <%--        <input type="checkbox" id="togglePassword"/>Show password--%>
    </div>
</fmt:bundle>