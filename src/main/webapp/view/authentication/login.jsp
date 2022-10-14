<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="re" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<%--        <re:menu lang="${sessionScope.lang}" email="${sessionScope.email}"/>--%>

        <div>
            <form action="<c:url value="/auth/login"/>" method="post" class="needs-validation col-4" novalidate>
                <jsp:include page="../util/emailInput.jsp"/>
<%--                <re:emailInput lang="${sessionScope.lang}" email="${param.email}"/>--%>
                <jsp:include page="../util/passwordInput.jsp">
                    <jsp:param name="passwordType" value="password"/>
                    <jsp:param name="inputValue" value="${param.password}"/>
                </jsp:include>
                <input type="submit" value="<fmt:message key="submit.login"/>" class="btn btn-primary"/>
            </form>
        </div>

        <div>
            <c:if test="${param.error != null}">
                <p class="text-danger pl-3"><fmt:message key="msg.error.auth.login.${param.error}"/></p>
            </c:if>
        </div>

        <div>
            <p class="pl-3">
                <fmt:message key="msg.info.askToSignUp"/>
                <a href="<c:url value="/auth/signup"/>" class="card-link">
                    <fmt:message key="button.signUp"/>
                </a>
            </p>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
