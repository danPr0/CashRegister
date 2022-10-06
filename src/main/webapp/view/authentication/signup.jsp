<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.auth.signup"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp"/>

        <form action="<c:url value="/auth/signup"/>" method="post" class="needs-validation col-4" novalidate>
            <jsp:include page="../util/usernameInput.jsp"/>

            <div class="form-group">
                <label for="firstName"><fmt:message key="label.firstName"/></label>
                <input type="text" name="firstName" required pattern="([A-Za-z]+|[А-Яа-яЁёЇїІіЄєҐґ']+" maxlength="48"
                       class="form-control" placeholder="<fmt:message key="placeHolder.firstName"/>" id="firstName"/>
                <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
            </div>

            <div class="form-group">
                <label for="secondName"><fmt:message key="label.secondName"/></label>
                <input type="text" name="secondName" required pattern="[A-Za-z]+|[А-Яа-яЁёЇїІіЄєҐґ']+" maxlength="48"
                       class="form-control" placeholder="<fmt:message key="placeHolder.secondName"/>" id="secondName"/>
                <div class="invalid-feedback"><fmt:message key="msg.invalidInput.required"/></div>
            </div>

            <jsp:include page="../util/passwordInput.jsp"/>

            <div class="form-group">
                <label for="password"><fmt:message key="label.passwordConfirm"/></label>
                <input type="password" name="passwordConfirm" required minlength="8" maxlength="48" class="form-control"
                       placeholder="<fmt:message key="placeHolder.passwordConfirm"/>" id="password"/>
                <div class="invalid-feedback"><fmt:message key="msg.invalidInput.password"/></div>
            </div>

            <input type="submit" value="<fmt:message key="submit.signup"/>" class="btn btn-primary"/>
        </form>

        <c:if test="${param.error != null}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.auth.signup.${param.error}"/></p>
        </c:if>

        <p class="pl-3">
            <fmt:message key="msg.info.askToLogin"/>
            <a href="<c:url value="/auth/login"/>" class="card-link">
                <fmt:message key="button.login"/>
            </a>
        </p>
    </div>
    </body>
</fmt:bundle>
</html>
