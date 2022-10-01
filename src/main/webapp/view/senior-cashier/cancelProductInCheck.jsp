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
    <form action="<c:url value="/senior-cashier/cancel-product-in-check"/>" method="post">
        <c:choose>
            <c:when test="${sessionScope.searchType == 'id'}">
                <jsp:include page="../util/productIdInput.jsp"/>
                <label>
                    <input type="text" name="productId" required pattern="[0-9]+"
                           placeholder="<fmt:message key="placeHolder.productId"/>"/>
                </label><fmt:message key="label.productId"/><br/>
                <a href="?searchType=name">Cancel product by name</a>
            </c:when>
            <c:otherwise>
                <jsp:include page="../util/productNameInput.jsp"/>
                <label>
                    <input type="text" name="productName" minlength="2" maxlength="30"
                           placeholder="<fmt:message key="placeHolder.productName"/>"/>
                </label><fmt:message key="label.productName"/><br/>
                <a href="?formType=id">Cancel product by id</a>
            </c:otherwise>
        </c:choose>
        <label>
            <input type="text" name="quantity" required pattern="[0-9]+" min="1" max="10000"
                   placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
        </label><fmt:message key="label.productQuantity"/><br/>
        <input type="submit" value="Cancel"/>
    </form>


    <jsp:include page="../menu/menu.jsp"/>
</fmt:bundle>
</body>
</html>
