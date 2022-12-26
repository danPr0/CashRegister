<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title>400 <fmt:message key="errorPage.error"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <p class="fs-1 fw-bold">400 <fmt:message key="errorPage.error.400"/></p>
        <p class="fs-6"><fmt:message key="errorPage.message"/> : <c:out value="${requestScope['javax.servlet.error.message']}"/></p>
        <p class="fs-6"><fmt:message key="errorPage.askToProceed"/> <a href="<c:url value="/"/>"><fmt:message key="errorPage.link"/></a></p>
    </div>
    </body>
</fmt:bundle>
</html>