<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value="/js?file=formValidation.js"/>"></script>
</head>
<body>
<div  class="container p-3 h-100 bg-dark text-white">
    <jsp:include page="/view/menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <form action="<c:url value="/auth/signup"/>" method="post" class="needs-validation col-4" novalidate>
            <jsp:include page="../util/usernameInput.jsp"/>
            <div class="form-group">
                <label for="firstName"><fmt:message key="label.firstName"/></label>
                <input type="text" name="firstName" required pattern="([a-z][A-Z])+|([а-я][А-Я])+" maxlength="48" class="form-control"
                       placeholder="<fmt:message key="placeHolder.firstName"/>" id="firstName"/>
                <div class="invalid-feedback">Invalid first name</div>
            </div>
            <div class="form-group">
                <label for="secondName"><fmt:message key="label.secondName"/></label>
                <input type="text" name="secondName" required pattern="([a-z][A-Z])+|([а-я][А-Я])+" maxlength="48" class="form-control"
                       placeholder="<fmt:message key="placeHolder.secondName"/>" id="secondName"/>
                <div class="invalid-feedback">Invalid second name</div>
            </div>
            <jsp:include page="../util/passwordInput.jsp"/>
            <div class="form-group">
                <label for="password"><fmt:message key="label.passwordConfirm"/></label>
                <input type="password" name="passwordConfirm" required minlength="8" maxlength="48" class="form-control"
                       placeholder="<fmt:message key="placeHolder.passwordConfirm"/>" id="password"/>
                <div class="invalid-feedback">Must be at least 8 characters</div>
            </div>
            <input type="submit" value="<fmt:message key="button.signup"/>" class="btn btn-primary"/>
        </form>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="pl-3">Already have an account? <a href="<c:url value="/auth/login"/>">Log in</a></p>
    </fmt:bundle>
</div>
</body>
</html>
