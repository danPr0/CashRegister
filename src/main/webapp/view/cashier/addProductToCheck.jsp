<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="sort" value="${requestScope.sort == null ? 'default' : requestScope.sort}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.cashier.addProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.10.2.js"
                type="text/javascript"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 h-100 bg-dark text-white">
        <jsp:include page="../menu/menu.jsp"/>
        <jsp:include page="../util/backToMainButton.jsp"/>

        <div>
            <form action="<c:url value="/cashier/add-product-to-check?page=1"/>" method="post"
                  class="needs-validation col-4" novalidate>
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

                <input type="submit" value="<fmt:message key="submit.add"/>" class="btn btn-primary"/>

                    <%--            <c:if test="${sessionScope.searchType == 'id'}">--%>
                    <%--                <a type="button" href="?searchType=name" class="mt-0">Or add/specify product by name</a>--%>
                    <%--            </c:if>--%>
                    <%--            <c:if test="${sessionScope.searchType == 'name'}">--%>
                    <%--                <a type="button" href="?searchType=id" class="mt-0">Or add/specify product by id</a>--%>
                    <%--            </c:if>--%>

            </form>
        </div>

        <div>
            <c:if test="${param.error != null}">
                <p class="text-danger pl-3"><fmt:message key="msg.error.cashier.${param.error}"/></p>
            </c:if>
            <c:if test="${param.success == 'true'}">
                <p class="text-success pl-3"><fmt:message key="msg.success.cashier.addProductToCheck"/></p>
            </c:if>
        </div>

        <div>
            <c:if test="${requestScope.nOfPages != 0}">
                <jsp:include page="sortSelect.jsp">
                    <jsp:param name="sort" value="${sort}"/>
                </jsp:include>

                <jsp:include page="checkTable.jsp"/>

                <jsp:include page="pagination.jsp">
                    <jsp:param name="sort" value="${sort}"/>
                </jsp:include>

                <%--            <div>--%>
                <%--                <a class="card-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">--%>
                <%--                    <fmt:message key="table.check.sort.dropdown"/>--%>
                <%--                </a>--%>
                <%--                <div class="dropdown-menu py-1" style="min-width: 3rem">--%>
                <%--                    <a class="dropdown-item" href="<c:url value="/cashier/add-product-to-check?sortBy=default"/>"--%>
                <%--                       <c:if test="${sort == 'default'}">style="pointer-events: none; color: gray"</c:if>>--%>
                <%--                        <fmt:message key="table.check.sort.default"/>--%>
                <%--                    </a>--%>

                <%--                    <a class="dropdown-item" href="<c:url value="/cashier/add-product-to-check?sortBy=productId"/>"--%>
                <%--                       <c:if test="${sort == 'productId'}">style="pointer-events: none; color: gray"</c:if>>--%>
                <%--                        <fmt:message key="table.check.sort.productId"/>--%>
                <%--                    </a>--%>

                <%--                    <a class="dropdown-item" href="<c:url value="/cashier/add-product-to-check?sortBy=quantity"/>"--%>
                <%--                       <c:if test="${sort == 'quantity'}">style="pointer-events: none; color: gray"</c:if>>--%>
                <%--                        <fmt:message key="table.check.sort.quantity"/>--%>
                <%--                    </a>--%>
                <%--                </div>--%>
                <%--            </div>--%>


            </c:if>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
