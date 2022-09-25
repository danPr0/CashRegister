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
    <table>
        <tr>
            <th>Created by</th>
            <th>Created at</th>
            <th>Items quantity</th>
            <th>Total price</th>
        </tr>
        <c:forEach var="i" items="${requestScope.report}">
            <tr>
                <td>${i.username}</td>
                <td>${i.closed_at}</td>
                <td>${i.items_quantity}</td>
                <td>${i.total_price}</td>
            </tr>
        </c:forEach>
    </table>

    <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
        <a href="<c:url value="/cashier/add-product-to-check?page=${i}"/>">${i}</a>
    </c:forEach>

    <div>
        <a href="<c:url value="/senior-cashier"/>">Back to main</a>
    </div>

    <jsp:include page="../menu/languageInterface.jsp"/>
</fmt:bundle>
</body>
</html>
