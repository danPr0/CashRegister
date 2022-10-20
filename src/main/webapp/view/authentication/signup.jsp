<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="passVisible" value="false"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.auth.signup"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp"/>

        <div class="mb-4">
            <form action="<c:url value="/auth/signup"/>" method="post" class="needs-validation ps-3 col-4" novalidate>
                <jsp:include page="../util/inputs/user/emailInput.jsp"/>
                <jsp:include page="../util/inputs/user/firstNameInput.jsp"/>
                <jsp:include page="../util/inputs/user/secondNameInput.jsp"/>

                <jsp:include page="../util/inputs/user/passwordInput.jsp">
                    <jsp:param name="passwordType" value="password"/>
                    <jsp:param name="inputValue" value="${param.password}"/>
                </jsp:include>

                <jsp:include page="../util/inputs/user/passwordInput.jsp">
                    <jsp:param name="passwordType" value="passwordConfirm"/>
                    <jsp:param name="inputValue" value="${param.passwordConfirm}"/>
                </jsp:include>
                <input type="submit" value="<fmt:message key="submit.signup"/>" class="btn btn-primary"/>
            </form>

            <p class="text-danger pl-3"><c:out value="${param.error}"/></p>
        </div>

        <div>
            <p>
                <fmt:message key="msg.info.askToLogin"/>
                <a href="<c:url value="/auth/login"/>" class="card-link">
                    <fmt:message key="button.login"/>
                </a>
            </p>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
