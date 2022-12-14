<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.main"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <e:menu/>

        <div>
            <a href="<c:url value="/commodity-expert/add-product"/>" class="btn btn-primary mb-2">
                <fmt:message key="button.addProduct"/>
            </a><br/>
            <a href="<c:url value="/commodity-expert/update-product"/>" class="btn btn-secondary">
                <fmt:message key="button.updateProduct"/>
            </a>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
