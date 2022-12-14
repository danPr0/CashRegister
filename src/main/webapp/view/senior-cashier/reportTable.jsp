<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="mb-3">
        <table class="table table-light table-striped table-hover">
            <tr>
                <th>#</th>
                <th><fmt:message key="table.report.createdBy"/></th>
                <th><fmt:message key="table.report.createdAt"/></th>
                <th><fmt:message key="table.report.quantity"/></th>
                <th><fmt:message key="table.report.price"/></th>
            </tr>
            <c:forEach var="i" items="${requestScope.report}">
                <tr>
                    <td><c:out value="${i.index}"/></td>
                    <td><c:out value="${i.createdBy}"/></td>
                    <td><c:out value="${i.closedAt}"/></td>
                    <td><c:out value="${i.itemsQuantity}"/></td>
                    <td><c:out value="${i.totalPrice}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</fmt:bundle>