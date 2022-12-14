<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input/user" %>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://www.google.com/recaptcha/api.js?render=6Lcn7NwiAAAAAAwYtAsRIMOAyuRz4lXz20JGzqVO"></script>
        <script src="<c:url value="/js?file=getRecaptchaToken.js"/>"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
        <script src="<c:url value="/js?file=checkPasswordsMatch.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <e:menu/>

        <c:choose>
            <c:when test="${param.success == 'true'}">
                <div>
                    <p><fmt:message key="msg.success.auth.signup"/></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="mb-4">
                    <form action="<c:url value="/auth/signup"/>" method="post" class="needs-validation col-4"
                          novalidate>
                        <input:emailInput/>
                        <input:firstNameInput/>
                        <input:secondNameInput/>
                        <input:passwordInput passwordType="password"/>
                        <input:passwordInput passwordType="passwordConfirm"/>
                        <input:captchaInput/>
                        <input type="hidden" id="g-recaptcha-response" name="g-recaptcha-response"/>

                        <script>checkPasswordsMatch("password", "passwordConfirm")</script>

                        <input type="submit" value="<fmt:message key="submit.signup"/>" class="btn btn-primary"/>
                    </form>

                    <p class="text-danger"><c:out value="${param.error}"/></p>
                </div>

                <div>
                    <p>
                        <fmt:message key="msg.info.askToLogin"/>
                        <a href="<c:url value="/auth/login"/>" class="card-link">
                            <fmt:message key="button.login"/>
                        </a>
                    </p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    </body>
</fmt:bundle>
</html>
