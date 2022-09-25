<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <form action="<c:url value="/cashier/add-product-to-check"/>" method="post">
        <label>
            <input type="text" name="productName" minlength="2" maxlength="30"
                   placeholder="<fmt:message key="placeHolder.productName"/>"/>
        </label><fmt:message key="label.productName"/><br/>
        <label>
            <input type="text" name="quantity" required pattern="[0-9]+"
                   placeholder="<fmt:message key="placeHolder.productQuantity"/>"/>
        </label><fmt:message key="label.productQuantity"/><br/>
        <input type="submit" value="<fmt:message key="button.add"/>"/>
    </form>
</fmt:bundle>