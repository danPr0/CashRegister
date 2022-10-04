<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.auth.login"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 h-100 bg-dark text-white">
        <jsp:include page="/view/menu/menu.jsp"/>

        <form action="<c:url value="/auth/login"/>" method="post" class="needs-validation col-4" novalidate>
            <jsp:include page="../util/usernameInput.jsp"/>
            <jsp:include page="../util/passwordInput.jsp"/>
            <input type="submit" value="<fmt:message key="button.login"/>" class="btn btn-primary"/>
        </form>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="pl-3">
            <fmt:message key="msg.info.askToSignUp"/>
            <a href="<c:url value="/auth/signup"/>">
                <fmt:message key="button.signUp"/>
            </a>
        </p>
    </div>
    </body>
</fmt:bundle>
</html>