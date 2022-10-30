<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input/product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.commodity-expert.updateProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
<%--        <jsp:include page="/view/menu/menu.jsp">--%>
<%--            <jsp:param name="mainUrl" value="/commodity-expert"/>--%>
<%--        </jsp:include>--%>

        <e:menu url="/commodity-expert"/>

        <div class="mb-4">
            <form action="<c:url value="/commodity-expert/update-product"/>" method="get" class="needs-validation col-4"
                  novalidate>
                <input:productInput/>
<%--                <jsp:include page="../../WEB-INF/tags/input/product/productInput.tag"/>--%>
                <input type="submit" value="<fmt:message key="submit.find"/>" class="btn btn-secondary"/>
            </form>

            <p class="text-danger"><c:out value="${requestScope.error}"/></p>
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
                    <input:productQuantityInput name="newQuantity" presetValue="${requestScope.product.quantity}"/>
                    <input:productPriceInput name="newPrice"  presetValue="${requestScope.product.price}"/>
<%--                    <jsp:include page="../../WEB-INF/tags/input/product/productQuantityInput.tag">--%>
<%--                        <jsp:param name="name" value="newQuantity"/>--%>
<%--                        <jsp:param name="presetValue" value="${requestScope.product.quantity}"/>--%>
<%--                    </jsp:include>--%>

<%--                    <jsp:include page="../../WEB-INF/tags/input/product/productPriceInput.tag">--%>
<%--                        <jsp:param name="name" value="newPrice"/>--%>
<%--                        <jsp:param name="presetValue" value="${requestScope.product.price}"/>--%>
<%--                    </jsp:include>--%>
                    <input type="submit" value="<fmt:message key="submit.update"/>" class="btn btn-primary"/>
                </form>

                <div>
                    <p class="text-danger"><c:out value="${param.error}"/></p>
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
