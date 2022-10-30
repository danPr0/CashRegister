<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input/user" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.user.changePassword"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
        <script src="<c:url value="/js?file=checkPasswordsMatch.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <e:menu url="/"/>

        <div class="mb-4">
            <form action="<c:url value="/change-password"/>" method="post" class="needs-validation col-4" novalidate>
                <input:passwordInput passwordType="oldPassword"/>
                <input:passwordInput passwordType="newPassword"/>
                <input:passwordInput passwordType="newPasswordConfirm"/>
                <script>checkPasswordsMatch("newPassword", "newPasswordConfirm")</script>

                <input type="submit" value="<fmt:message key="submit.change"/>" class="btn btn-primary"/>
            </form>

            <p class="text-danger"><c:out value="${param.error}"/></p>
            <c:if test="${param.success == true}">
                <p class="text-success"><fmt:message key="msg.success.user.changePassword"/></p>
            </c:if>
        </div>

        <div>
            <p>
                Forgot your password? Click
                <a href="<c:url value="/reset-password"/>" class="card-link">
                    here
                </a>
                to reset it
            </p>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
