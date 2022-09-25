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
<fmt:bundle basename="messages">
    <a href="<c:url value="/commodity-expert/add-product"/>">Add product</a><br/>
    <a href="<c:url value="/commodity-expert/update-product"/>">Update product</a>

    <jsp:include page="/view/menu/languageInterface.jsp"/>

</fmt:bundle>
</body>
</html>
