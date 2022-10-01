<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<c:set var="total" value="2"/>

<c:set var="page" value="${param.page}"/>
<c:if test="${param.page == null}">
    <c:set var="page" value="1"/>
</c:if>

<c:set var="sort" value="${requestScope.sort}"/>
<c:if test="${requestScope.sort == null}">
    <c:set var="sort" value="default"/>
</c:if>

<html lang="${sessionScope.lang}">
<head>
    <title>Cashier</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
</head>
<body>
<div class="container p-3 my-3 bg-dark text-white">
    <jsp:include page="../menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <form action="<c:url value="/cashier/add-product-to-check?page=1&total=${total}"/>" method="post" class="was-validated col-4">
<%--            <c:choose>--%>
<%--                <c:when test="${sessionScope.searchType == 'id'}">--%>
<%--                    <jsp:include page="../util/productIdInput.jsp"/>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <jsp:include page="../util/productNameInput.jsp"/>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
            <jsp:include page="../util/productInput.jsp"/>

            <div class="form-group">
                <label for="prodQuantity"><fmt:message key="label.productQuantity"/></label>
                <input type="text" name="quantity" required pattern="[0-9]+" min="1" max="10000" class="form-control"
                       placeholder="<fmt:message key="placeHolder.productQuantity"/>" id="prodQuantity"/>
                <div class="invalid-feedback">Invalid number(max 10000)</div>
            </div>

            <input type="submit" value="<fmt:message key="button.add"/>" class="btn btn-primary"/>

            <c:if test="${sessionScope.searchType == 'id'}">
                <a type="button" href="?searchType=name" class="mt-0">Or add/specify product by name</a>
            </c:if>
            <c:if test="${sessionScope.searchType == 'name'}">
                <a type="button" href="?searchType=id" class="mt-0">Or add/specify product by id</a>
            </c:if>

        </form>

        <p>${param.error}</p>
        <p>${param.success}</p>

        <c:if test="${requestScope.nOfPages != 0}">
            <div>
                <a class="dropdown-toggle" href="#" id="sortDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Sort by
                </a>
                <div class="dropdown-menu py-1" style="min-width: 3rem">
                    <a class="dropdown-item <c:if test="${sort == 'default'}">disabled</c:if>"
                       <c:if test="${sort == 'default'}">style="pointer-events: none"</c:if>
                       href="<c:url value="/cashier/add-product-to-check?sortBy=default"/>">By adding</a>
                    <a class="dropdown-item <c:if test="${sort == 'productId'}">disabled</c:if>"
                       <c:if test="${sort == 'productId'}">style="pointer-events: none"</c:if>
                       href="<c:url value="/cashier/add-product-to-check?sortBy=productId"/>">Product id</a>
                    <a class="dropdown-item <c:if test="${sort == 'quantity'}">disabled</c:if>"
                       <c:if test="${sort == 'quantity'}">style="pointer-events: none"</c:if>
                       href="<c:url value="/cashier/add-product-to-check?sortBy=quantity"/>">Quantity</a>
                </div>
            </div>
            <table class="table-light table-striped table-hover col-8">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total price</th>
                </tr>
                <c:forEach var="i" items="${requestScope.check}">
                    <tr>
                        <td>${i.product.id}</td>
                        <td>${i.product.name}</td>
                        <td>${i.quantity}</td>
                        <td>${i.product.price}</td>
                        <td>${i.product.price * i.quantity}</td>
                    </tr>
                </c:forEach>
            </table>

            <ul class="pagination">
                <li class="page-item <c:if test="${page == 1}">disabled</c:if>"><a
                        href="<c:url value="/cashier/add-product-to-check?page=${page - 1}&total=${total}&sortBy=${sort}"/>" class="page-link">Previous</a>
                </li>
                <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
                    <%--                <c:set var="pageClass" value="page-item"/>--%>
                    <%--                <c:if test="${param.page == i}"><c:set var="pageClass" value="${pageClass} active"/></c:if>--%>
                    <li class="page-item <c:if test="${page == i}">active</c:if>"><a
                            href="<c:url value="/cashier/add-product-to-check?page=${i}&total=${total}&sortBy=${sort}"/>" class="page-link">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test="${page == requestScope.nOfPages}">disabled</c:if>"><a
                        href="<c:url value="/cashier/add-product-to-check?page=${page + 1}&total=${total}&sortBy=${sort}"/>" class="page-link">Next</a>
                </li>
            </ul>
        </c:if>

    </fmt:bundle>
</div>
</body>
</html>
