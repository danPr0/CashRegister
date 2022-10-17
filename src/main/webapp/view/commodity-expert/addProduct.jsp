<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.commodity-expert.addProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp">
            <jsp:param name="mainUrl" value="/commodity-expert"/>
        </jsp:include>

        <div>
            <form action="<c:url value="/commodity-expert/add-product"/>" method="post" class="needs-validation col-4"
                  novalidate>
                <jsp:include page="../util/inputs/product/productNameInput.jsp"/>
                <jsp:include page="../util/inputs/product/productMeasureInput.jsp"/>

                <jsp:include page="../util/inputs/product/productQuantityInput.jsp">
                    <jsp:param name="name" value="quantity"/>
                    <jsp:param name="presetValue" value="${param.quantity}"/>
                </jsp:include>

                <jsp:include page="../util/inputs/product/productPriceInput.jsp">
                    <jsp:param name="name" value="price"/>
                    <jsp:param name="presetValue" value="${param.price}"/>
                </jsp:include>
                <input type="submit" value="<fmt:message key="submit.add"/>" class="btn btn-primary"/>
            </form>

            <div>
                <c:if test="${param.error == 'true'}">
                    <p class="text-danger pl-3"><fmt:message key="msg.error.commodity-expert.addProduct"/></p>
                </c:if>
                <c:if test="${param.success == 'true'}">
                    <p class="text-success pl-3"><fmt:message key="msg.success.commodity-expert.addProduct"/></p>
                </c:if>
            </div>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
