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
        <label>
            <input type="text" name="productName" minlength="2" maxlength="30"
                   placeholder="<fmt:message key="placeHolder.productName"/>"/>
        </label>Which product you'd like to update?<br/>
        <input type="submit" value="Find"/>
    </form>

    <c:if test="${requestScope.product != null}">
        <p>Name : ${requestScope.product.name}    Quantity : ${requestScope.product.quantity}    Price : ${requestScope.product.price}</p>
        <form action="<c:url value="/commodity-expert/update-product?productName=${requestScope.product.name}"/>" method="post">
            <label>
                <input type="text" name="newQuantity" required pattern="[0-9]+"
                       placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
            </label><fmt:message key="label.productQuantity"/><br/>
            <label>
                <input type="text" name="newPrice" required pattern="[0-9]+(.)?[0-9]*"
                       placeholder="<fmt:message key="placeholder.productPrice"/>"/>
            </label><fmt:message key="label.productPrice"/><br/>
            <input type="submit" value="Update product"/>
        </form>

    </c:if>

    <c:if test="${error != null}">
        <p>${error}</p>
    </c:if>

    <c:if test="${param.success == 'true'}">
        <p>Product ${param.productName} was successfully updated!</p>
    </c:if>

    <a href="<c:url value="/commodity-expert"/>">Back to main</a>

    <jsp:include page="/view/menu/languageInterface.jsp"/>

</fmt:bundle>

</body>
</html>
