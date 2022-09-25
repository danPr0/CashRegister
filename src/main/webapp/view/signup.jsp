<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Sign up</title>
</head>
<body>
<fmt:bundle basename="messages">
    <form action="<c:url value="/auth/signup"/>" method="post">
        <label>
            <input type="text" name="username" minlength="3" maxlength="16" placeholder="<fmt:message key="placeHolder.username"/>"/>
        </label><fmt:message key="label.username"/><br/>
        <label>
            <input type="text" name="firstName" maxlength="30" placeholder="<fmt:message key="placeHolder.firstName"/>"/>
        </label><fmt:message key="label.firstName"/><br/>
        <label>
            <input type="text" name="secondName" maxlength="30" placeholder="<fmt:message key="placeHolder.secondName"/>"/>
        </label><fmt:message key="label.secondName"/><br/>
        <label>
            <input type="password" name="password" minlength="8" maxlength="24" placeholder="<fmt:message key="placeHolder.password"/>"/>
        </label><fmt:message key="label.password"/><br/>
        <label>
            <input type="password" name="passwordConfirm" minlength="8" maxlength="24" placeholder="<fmt:message key="placeHolder.passwordConfirm"/>"/>
        </label><fmt:message key="label.passwordConfirm"/><br/>
        <input type="submit" value="<fmt:message key="button.signup"/>"/>
    </form>

    <c:if test="${param.error != null}">
        <p>${param.error}</p>
    </c:if>

    <p>Already have an account? <a href="<c:url value="/auth/login"/>">Log in!</a></p>

    <jsp:include page="/view/menu/languageInterface.jsp"/>

</fmt:bundle>

</body>
</html>
