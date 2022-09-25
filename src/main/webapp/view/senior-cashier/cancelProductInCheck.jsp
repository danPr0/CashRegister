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
    <c:choose>
        <c:when test="${param.formType == null || param.formType == 'id'}">
            <form action="<c:url value="/senior-cashier/cancel-product-in-check"/>" method="post">
                <label>
                    <input type="text" name="productId" required pattern="[0-9]+"
                           placeholder="<fmt:message key="placeHolder.productId"/>"/>
                </label><fmt:message key="label.productId"/><br/>
                <label>
                    <input type="text" name="quantity" required pattern="[0-9]+"
                           placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
                </label><fmt:message key="label.productQuantity"/><br/>
                <input type="submit" value="Cancel"/>
            </form>

            <a href="?formType=name">Cancel product by name</a>
        </c:when>
        <c:otherwise>
            <form action="<c:url value="/senior-cashier/cancel-product-in-check"/>" method="post">
                <label>
                    <input type="text" name="productName" minlength="2" maxlength="30"
                           placeholder="<fmt:message key="placeHolder.productName"/>"/>
                </label><fmt:message key="label.productName"/><br/>
                <label>
                    <input type="text" name="quantity" required pattern="[0-9]+"
                           placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
                </label><fmt:message key="label.productQuantity"/><br/>
                <input type="submit" value="Cancel"/>
            </form>

            <a href="?formType=id">Cancel product by id</a>
        </c:otherwise>
    </c:choose>

    <div>
        <a href="<c:url value="/senior-cashier"/>">Back to main</a>
    </div>

    <jsp:include page="../menu/languageInterface.jsp"/>
</fmt:bundle>
</body>
</html>
