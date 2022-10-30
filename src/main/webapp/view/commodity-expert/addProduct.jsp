<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input/product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.commodity-expert.addProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
<%--        <jsp:include page="/view/menu/menu.jsp">--%>
<%--            <jsp:param name="mainUrl" value="/commodity-expert"/>--%>
<%--        </jsp:include>--%>

        <e:menu url="/commodity-expert"/>

        <div>
            <form action="<c:url value="/commodity-expert/add-product"/>" method="post" class="needs-validation col-4"
                  novalidate>
                <c:forEach var="i" items="${sessionScope.languages}">
                    <input:productNameInput name="productName_${i}"/>
<%--                    <jsp:include page="../../WEB-INF/tags/input/product/productNameInput.tag">--%>
<%--                        <jsp:param name="name" value="productName_${i}"/>--%>
<%--                    </jsp:include>--%>
                </c:forEach>

                <input:productMeasureInput/>
                <input:productQuantityInput name="quantity" presetValue="${param.quantity}"/>
                <input:productPriceInput name="price" presetValue="${param.price}"/>

<%--                <jsp:include page="../../WEB-INF/tags/input/product/productMeasureInput.tag"/>--%>

<%--                <jsp:include page="../../WEB-INF/tags/input/product/productQuantityInput.tag">--%>
<%--                    <jsp:param name="name" value="quantity"/>--%>
<%--                    <jsp:param name="presetValue" value="${param.quantity}"/>--%>
<%--                </jsp:include>--%>

<%--                <jsp:include page="../../WEB-INF/tags/input/product/productPriceInput.tag">--%>
<%--                    <jsp:param name="name" value="price"/>--%>
<%--                    <jsp:param name="presetValue" value="${param.price}"/>--%>
<%--                </jsp:include>--%>
                <input type="submit" value="<fmt:message key="submit.add"/>" class="btn btn-primary"/>
            </form>

            <div>
                <p class="text-danger pl-3"><c:out value="${param.error}"/></p>
                <c:if test="${param.success == 'true'}">
                    <p class="text-success pl-3"><fmt:message key="msg.success.commodity-expert.addProduct"/></p>
                </c:if>
            </div>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
