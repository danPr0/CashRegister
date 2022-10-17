<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp">
            <jsp:param name="mainUrl" value="/senior-cashier"/>
        </jsp:include>

        <div>
            <form action="<c:url value="/senior-cashier/cancel-product-in-check"/>" method="post"
                  class="needs-validation col-4" novalidate>
                <jsp:include page="../util/inputs/product/productInput.jsp"/>

                <jsp:include page="../util/inputs/product/productQuantityInput.jsp">
                    <jsp:param name="name" value="quantity"/>
                    <jsp:param name="presetValue" value="${param.quantity}"/>
                </jsp:include>

                <input type="submit" value="<fmt:message key="submit.cancel"/>" class="btn btn-primary"/>
            </form>

            <div>
                <p class="text-danger">${param.error}</p>
                <c:if test="${param.success == 'true'}">
                    <p class="text-success"><fmt:message key="msg.success.senior-cashier.cancelProduct"/></p>
                </c:if>
            </div>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
