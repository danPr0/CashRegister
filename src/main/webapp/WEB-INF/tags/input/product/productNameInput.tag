<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="http://my-taglib/get-param-value" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="name" required="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-2">
        <label for="${name}" class="form-label"><fmt:message key="label.${name}"/></label>
        <input type="text" name="${name}" required maxlength="60" class="form-control"
               placeholder="<fmt:message key="placeHolder.productName"/>" id="${name}"
               value="<ex:param paramName="${name}"/>"/>
        <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
    </div>
</fmt:bundle>
