<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="http://tomcat.apache.org/my-taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="${param.name}" class="form-label"><fmt:message key="label.${param.name}"/></label>
        <input type="text" name="${param.name}" required maxlength="60" class="form-control"
               placeholder="<fmt:message key="placeHolder.productName"/>" id="${param.name}"
               value="<ex:param paramName="${param.name}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
    </div>
</fmt:bundle>
