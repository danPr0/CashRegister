<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Title</title>
</head>
<body>
<fmt:bundle basename="messages">
    <form action="<c:url value="/commodity-expert/update-product"/>" method="get">
        <c:choose>
            <c:when test="${sessionScope.searchType == 'id'}">
                <jsp:include page="../util/productIdInput.jsp"/>
                <a type="button" href="?formType=name">Find product by name</a>
            </c:when>
            <c:otherwise>
                <jsp:include page="../util/productNameInput.jsp"/>
                <a type="button" href="?formType=id">Find product by id</a>
            </c:otherwise>
        </c:choose>
        <input type="submit" value="Find"/>
    </form>

    <p>${requestScope.error}</p>

    <c:if test="${requestScope.product != null}">
        <p>Name : ${requestScope.product.name}    Quantity : ${requestScope.product.quantity}    Price : ${requestScope.product.price}</p>
        <form action="<c:url value="/commodity-expert/update-product?productName=${requestScope.product.name}"/>" method="post">
            <label>
                <input type="text" name="newQuantity" required pattern="[0-9]+" min="1" max="10000"
                       placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
            </label><fmt:message key="label.productQuantity"/><br/>
            <label>
                <input type="text" name="newPrice" required pattern="[0-9]+(.)?[0-9]*"
                       placeholder="<fmt:message key="placeholder.productPrice"/>"/>
            </label><fmt:message key="label.productPrice"/><br/>
            <input type="submit" value="Update product"/>
        </form>
    </c:if>

    <p>${param.error}</p>
    <p>${param.success}</p>

    <jsp:include page="/view/menu/menu.jsp"/>

</fmt:bundle>

</body>
</html>
