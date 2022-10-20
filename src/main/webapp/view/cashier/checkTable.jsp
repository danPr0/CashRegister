<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div>
        <table class="table table-light table-striped table-hover">
            <tr>
                <th>#</th>
                <th><fmt:message key="table.check.id"/></th>
                <th><fmt:message key="table.check.name"/></th>
                <th><fmt:message key="table.check.quantity"/></th>
                <th><fmt:message key="table.check.price"/></th>
                <th><fmt:message key="table.check.totalPrice"/></th>
            </tr>
            <c:forEach var="i" items="${requestScope.check}">
                <tr>
                    <td><c:out value="${i.index}"/></td>
                    <td><c:out value="${i.productId}"/></td>
                    <td><c:out value="${i.productName}"/></td>
                    <td><c:out value="${i.quantity}"/></td>
                    <td><c:out value="${i.productPrice}"/></td>
                    <td><c:out value="${i.totalPrice}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</fmt:bundle>