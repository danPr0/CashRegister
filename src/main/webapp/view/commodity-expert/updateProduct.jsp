<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.commodity-expert.updateProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp">
            <jsp:param name="mainUrl" value="/commodity-expert"/>
        </jsp:include>

        <div class="mb-4">
            <form action="<c:url value="/commodity-expert/update-product"/>" method="get" class="needs-validation col-4"
                  novalidate>
                <jsp:include page="../util/inputs/product/productInput.jsp"/>
                <input type="submit" value="<fmt:message key="submit.find"/>" class="btn btn-secondary"/>
            </form>

            <c:if test="${requestScope.error == 'true'}">
                <p class="text-danger pl-3"><fmt:message key="msg.error.commodity-expert.findProduct"/></p>
            </c:if>
        </div>

        <c:if test="${requestScope.product != null}">
            <div class="pl-3 text-info">
                <p class="mb-0"><fmt:message key="table.check.id"/> : <c:out value="${requestScope.product.id}"/></p>
                <p class="mb-0"><fmt:message key="table.check.name"/> : <c:out value="${requestScope.product.name}"/></p>
                <p class="mb-0"><fmt:message key="table.check.quantity"/> : <c:out value="${requestScope.product.quantity}"/></p>
                <p class="mb-3"><fmt:message key="table.check.price"/> : <c:out value="${requestScope.product.price}"/></p>
            </div>

            <div>
                <form action="<c:url value="/commodity-expert/update-product?productId=${requestScope.product.id}"/>"
                      method="post" class="needs-validation col-4" novalidate>
                    <jsp:include page="../util/inputs/product/productQuantityInput.jsp">
                        <jsp:param name="name" value="newQuantity"/>
                        <jsp:param name="presetValue" value="${requestScope.product.quantity}"/>
                    </jsp:include>

                    <jsp:include page="../util/inputs/product/productPriceInput.jsp">
                        <jsp:param name="name" value="newPrice"/>
                        <jsp:param name="presetValue" value="${requestScope.product.price}"/>
                    </jsp:include>
                    <input type="submit" value="<fmt:message key="submit.update"/>" class="btn btn-primary"/>
                </form>

                <div>
                    <c:if test="${param.error == 'true'}">
                        <p class="text-danger pl-3"><fmt:message key="msg.error.commodity-expert.updateProduct"/></p>
                    </c:if>
                    <c:if test="${param.success == 'true'}">
                        <p class="text-success pl-3"><fmt:message key="msg.success.commodity-expert.updateProduct"/></p>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
