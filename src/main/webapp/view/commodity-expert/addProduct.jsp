<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Add product</title>
    <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value="/js?file=formValidation.js"/>"></script>
</head>
<body>
<div class="container p-3 h-100 bg-dark text-white">
    <jsp:include page="/view/menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <form action="<c:url value="/commodity-expert/add-product"/>" method="post" class="needs-validation col-4" novalidate>
            <jsp:include page="../util/productNameInput.jsp"/>
            <jsp:include page="../util/productQuantityInput.jsp"/>
            <jsp:include page="../util/productPriceInput.jsp"/>
                <%--        <label>--%>
                <%--            <input type="text" name="quantity"  required pattern="[0-9]+" min="1" max="10000"--%>
                <%--                   placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>--%>
                <%--        </label><fmt:message key="label.productQuantity"/><br/>--%>
                <%--        <label>--%>
                <%--            <input type="text" name="price" required pattern="[0-9]+(.)?[0-9]+"--%>
                <%--                   placeholder="<fmt:message key="placeholder.productPrice"/>"/>--%>
                <%--        </label><fmt:message key="label.productPrice"/><br/>--%>
            <input type="submit" value="<fmt:message key="button.add"/>" class="btn btn-primary"/>
        </form>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="text-success pl-3">${param.success}</p>
    </fmt:bundle>
</div>
</body>
</html>
