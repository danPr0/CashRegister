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
    <div class="container p-3 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="/view/menu/menu.jsp"/>
        <jsp:include page="../util/backToMainButton.jsp"/>

        <form action="<c:url value="/commodity-expert/update-product"/>" method="get" class="needs-validation col-4"
              novalidate>
                <%--        <c:choose>--%>
                <%--            <c:when test="${sessionScope.searchType == 'id'}">--%>
                <%--                <jsp:include page="../util/productIdInput.jsp"/>--%>
                <%--                <a type="button" href="?formType=name">Find product by name</a>--%>
                <%--            </c:when>--%>
                <%--            <c:otherwise>--%>
                <%--                <jsp:include page="../util/productNameInput.jsp"/>--%>
                <%--                <a type="button" href="?formType=id">Find product by id</a>--%>
                <%--            </c:otherwise>--%>
                <%--        </c:choose>--%>
            <jsp:include page="../util/productInput.jsp"/>
            <input type="submit" value="<fmt:message key="submit.find"/>" class="btn btn-secondary"/>
        </form>

        <c:if test="${requestScope.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.commodity-expert.findProduct"/></p>
        </c:if>

        <c:if test="${requestScope.product != null}">
            <div class="pl-3 text-info">
                <p class="mb-0"><fmt:message key="table.check.id"/> : ${requestScope.product.id}</p>
                <p class="mb-0"><fmt:message key="table.check.name"/> : ${requestScope.product.name}</p>
                <p class="mb-0"><fmt:message key="table.check.quantity"/> : ${requestScope.product.quantity}</p>
                <p><fmt:message key="table.check.price"/> : ${requestScope.product.price}</p>
            </div>

            <form action="<c:url value="/commodity-expert/update-product?productId=${requestScope.product.id}"/>"
                  method="post" class="needs-validation col-4" novalidate>
                    <%--            <label>--%>
                    <%--                <input type="text" name="newQuantity" required pattern="[0-9]+" min="1" max="10000"--%>
                    <%--                       placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>--%>
                    <%--            </label><fmt:message key="label.productQuantity"/><br/>--%>
                    <%--            <label>--%>
                    <%--                <input type="text" name="newPrice" required pattern="[0-9]+(.)?[0-9]*"--%>
                    <%--                       placeholder="<fmt:message key="placeholder.productPrice"/>"/>--%>
                    <%--            </label><fmt:message key="label.productPrice"/><br/>--%>
                <jsp:include page="../util/productQuantityInput.jsp"/>
                <jsp:include page="../util/productPriceInput.jsp"/>
                <input type="submit" value="<fmt:message key="submit.update"/>" class="btn btn-primary"/>
            </form>
        </c:if>

        <c:if test="${param.error == 'true'}">
            <p class="text-danger pl-3"><fmt:message key="msg.error.commodity-expert.updateProduct"/></p>
        </c:if>
        <c:if test="${param.success == 'true'}">
            <p class="text-success pl-3"><fmt:message key="msg.success.commodity-expert.updateProduct"/></p>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
