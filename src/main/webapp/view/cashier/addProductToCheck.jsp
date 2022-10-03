<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

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
    <title>Add product to check</title>
    <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value="/js?file=formValidation.js"/>"></script>
</head>
<body>
<div class="container p-3 h-100 bg-dark text-white">
    <jsp:include page="../menu/menu.jsp"/>

    <fmt:bundle basename="messages">
        <form action="<c:url value="/cashier/add-product-to-check?page=1"/>" method="post" class="needs-validation col-4" novalidate>
<%--            <c:choose>--%>
<%--                <c:when test="${sessionScope.searchType == 'id'}">--%>
<%--                    <jsp:include page="../util/productIdInput.jsp"/>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <jsp:include page="../util/productNameInput.jsp"/>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
            <jsp:include page="../util/productInput.jsp"/>
            <jsp:include page="../util/productQuantityInput.jsp"/>

<%--            <div class="form-group">--%>
<%--                <label for="prodQuantity"><fmt:message key="label.productQuantity"/></label>--%>
<%--                <input type="text" name="quantity" required pattern="[0-9]+" minlength="1" maxlength="9" class="form-control"--%>
<%--                       placeholder="<fmt:message key="placeHolder.productQuantity"/>" id="prodQuantity"/>--%>
<%--                <div class="invalid-feedback">Number required</div>--%>
<%--            </div>--%>

            <input type="submit" value="<fmt:message key="button.add"/>" class="btn btn-primary"/>

<%--            <c:if test="${sessionScope.searchType == 'id'}">--%>
<%--                <a type="button" href="?searchType=name" class="mt-0">Or add/specify product by name</a>--%>
<%--            </c:if>--%>
<%--            <c:if test="${sessionScope.searchType == 'name'}">--%>
<%--                <a type="button" href="?searchType=id" class="mt-0">Or add/specify product by id</a>--%>
<%--            </c:if>--%>

        </form>

        <p class="text-danger pl-3">${param.error}</p>
        <p class="text-success pl-3">${param.success}</p>

        <c:if test="${requestScope.nOfPages != 0}">
            <div>
                <a class="card-link dropdown-toggle" href="#" id="sortDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
                        href="<c:url value="/cashier/add-product-to-check?page=${page - 1}&sortBy=${sort}"/>" class="page-link">Previous</a>
                </li>
                <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
                    <%--                <c:set var="pageClass" value="page-item"/>--%>
                    <%--                <c:if test="${param.page == i}"><c:set var="pageClass" value="${pageClass} active"/></c:if>--%>
                    <li class="page-item <c:if test="${page == i}">active</c:if>"><a
                            href="<c:url value="/cashier/add-product-to-check?page=${i}&sortBy=${sort}"/>" class="page-link">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test="${page == requestScope.nOfPages}">disabled</c:if>"><a
                        href="<c:url value="/cashier/add-product-to-check?page=${page + 1}&sortBy=${sort}"/>" class="page-link">Next</a>
                </li>
            </ul>
        </c:if>

    </fmt:bundle>
</div>
</body>
</html>
