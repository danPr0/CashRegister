<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.senior-cashier.cancelProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 h-100 bg-dark text-white">
        <jsp:include page="../menu/menu.jsp"/>

        <form action="<c:url value="/senior-cashier/cancel-product-in-check"/>" method="post"
              class="needs-validation col-4" novalidate>
            <jsp:include page="../util/productInput.jsp"/>
            <jsp:include page="../util/productQuantityInput.jsp"/>
            <input type="submit" value="<fmt:message key="submit.cancel"/>" class="btn btn-primary"/>
        </form>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="text-success pl-3">${param.success}</p>
    </div>
    </body>
</fmt:bundle>
</html>
