<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<%--pattern="([A-Za-zА-Яа-яЁёЇїІіЄєҐґ'0-9_]+)"--%>
<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="email"  class="form-label"><fmt:message key="label.email"/></label>
            <input type="email" name="email" required minlength="3" maxlength="254" class="form-control"
                   placeholder="<fmt:message key="placeHolder.email"/>" id="email" value="<c:out value="${param.email}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.email"/></div>
    </div>
</fmt:bundle>