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
    <form action="<c:url value="/commodity-expert/add-product"/>" method="post">
        <label>
            <input type="text" name="productName" minlength="2" maxlength="30"
                   placeholder="<fmt:message key="placeHolder.productName"/>"/>
        </label><fmt:message key="label.productName"/><br/>
        <label>
            <input type="text" name="quantity" required pattern="[0-9]+"
                   placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
        </label><fmt:message key="label.productQuantity"/><br/>
        <label>
            <input type="text" name="price" required pattern="[0-9]+(.)?[0-9]+"
                   placeholder="<fmt:message key="placeholder.productPrice"/>"/>
        </label><fmt:message key="label.productPrice"/><br/>
        <input type="submit" value="<fmt:message key="button.add"/>"/>
    </form>

    <c:if test="${param.error != null}">
        <p>${param.error}</p>
    </c:if>

    <c:if test="${param.success == 'true'}">
        <p>Product ${param.productName} was successfully added!</p>
    </c:if>

    <a href="<c:url value="/commodity-expert"/>">Back to main</a>

    <jsp:include page="/view/menu/languageInterface.jsp"/>

</fmt:bundle>

</body>
</html>
