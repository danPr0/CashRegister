<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Cashier</title>
</head>
<body>
<%--<c:set var="formType" scope="session"/>--%>
<fmt:bundle basename="messages">
    <c:choose>
        <c:when test="${param.formType == null || param.formType == 'id'}">
            <jsp:include page="addProductByIdForm.jsp"/>
            <a type="button" href="?formType=name">Add/specify product by name</a>
        </c:when>
        <c:otherwise>
            <jsp:include page="addProductByNameForm.jsp"/>
            <a type="button" href="?formType=id">Add/specify product by id</a>
        </c:otherwise>
    </c:choose>


    <c:if test="${param.error != null}">
        <p>${param.error}</p>
    </c:if>

    <c:if test="${param.success == 'true'}">
        <p>Check was successfully updated!</p>
    </c:if>

    <div>
        <a href="<c:url value="/cashier"/>">Back to main</a>
    </div>

    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total price</th>
        </tr>
        <c:forEach var="i" items="${requestScope.check}">
            <tr>
                <td>${i.id}</td>
                <td>${i.product.name}</td>
                <td>${i.quantity}</td>
                <td>${i.product.price}</td>
                <td>${i.product.price * i.quantity}</td>
            </tr>
        </c:forEach>
    </table>

    <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
        <a href="<c:url value="/cashier/add-product-to-check?page=${i}"/>">${i}</a>
    </c:forEach>

    <jsp:include page="../menu/languageInterface.jsp"/>

</fmt:bundle>
</body>
</html>
