<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<script src="<c:url value="/js?file=togglePassword.js"/>"></script>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="${param.passwordType}" class="form-label"><fmt:message key="label.${param.passwordType}"/></label>
        <div class="input-group">
            <input type="password" name="${param.passwordType}" required minlength="8" maxlength="55"
                   class="form-control" id="${param.passwordType}"
                   placeholder="<fmt:message key="placeHolder.${param.passwordType}"/>" value="${param.inputValue}"/>

            <button class="btn btn-link px-0 py-0" type="button" id="toggle${param.passwordType}"
                    style="background-color: white; border-radius: 4px; margin-left: 1px">
                <img src="<c:url value="/images?file=password_eye.png"/>" style="height: 2rem; width: 2rem" alt="sp"/>
            </button>

            <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>

            <script>togglePassword("toggle${param.passwordType}", "${param.passwordType}")</script>
        </div>
    </div>
</fmt:bundle>

<%--<fmt:bundle basename="messages">--%>
<%--    <div class="mb-2">--%>
<%--        <label for="password" class="form-label"><fmt:message key="label.password"/></label>--%>
<%--        <div class="input-group">--%>
<%--            <input type="password" name="password" required minlength="8" maxlength="55" class="form-control"--%>
<%--                   placeholder="<fmt:message key="placeHolder.password"/>" id="password" value="${param.password}"/>--%>
<%--            <button class="btn btn-link px-0 py-0" type="button"--%>
<%--                    style="background-color: white; border-radius: 4px; margin-left: 1px" id="togglePassword">--%>
<%--                <img src="<c:url value="/images?file=password_eye.png"/>" style="height: 2rem; width: 2rem" alt="sp"/>--%>
<%--            </button>--%>
<%--            <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>--%>
<%--            <script>togglePassword("togglePassword", "password");</script>--%>
<%--        </div>--%>

<%--            &lt;%&ndash;        <input type="checkbox" id="togglePassword"/>Show password&ndash;%&gt;--%>
<%--    </div>--%>
<%--</fmt:bundle>--%>