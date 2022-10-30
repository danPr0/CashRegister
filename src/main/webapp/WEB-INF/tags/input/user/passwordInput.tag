<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="http://my-taglib/get-param-value" %>
<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="passwordType" required="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<script src="<c:url value="/js?file=togglePassword.js"/>"></script>
<script>togglePassword("toggle${passwordType}", "${passwordType}")</script>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="${passwordType}" class="form-label"><fmt:message key="label.${passwordType}"/></label>
        <div class="input-group">
            <input type="password" name="${passwordType}" required minlength="8" maxlength="55"
                   class="form-control" id="${passwordType}"
                   placeholder="<fmt:message key="placeHolder.${passwordType}"/>" value="<ex:param paramName="${passwordType}"/>"/>

            <button class="btn btn-link px-0 py-0" type="button" id="toggle${passwordType}"
                    style="background-color: white; border-radius: 4px; margin-left: 1px">
                <img src="<c:url value="/images?file=password_eye.png"/>" style="height: 2rem; width: 2rem" alt="sp"/>
            </button>
            <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>
        </div>

        <div id="${passwordType}Mismatch" class="text-danger visually-hidden"><fmt:message key="msg.invalidInput.passwordMismatch"/></div>
    </div>
</fmt:bundle>