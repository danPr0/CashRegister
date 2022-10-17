<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="sort" value="${sessionScope.checkSortBy}"/>
<c:set var="order" value="${sessionScope.checkOrderBy}"/>
<c:set var="perPage" value="${sessionScope.checkTotalPerPage}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.cashier.addProduct"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.10.2.js"
                type="text/javascript"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
        <script src="<c:url value="/js?file=tableParams.js"/>"></script>
        <script>addTableParamListener("/cashier/add-product-to-check")</script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <jsp:include page="../menu/menu.jsp">
            <jsp:param name="mainUrl" value="/cashier"/>
        </jsp:include>

        <div class="mb-5">
            <form action="<c:url value="/cashier/add-product-to-check"/>" method="post"
                  class="needs-validation col-4" novalidate>
                <jsp:include page="../util/inputs/product/productInput.jsp"/>

                <jsp:include page="../util/inputs/product/productQuantityInput.jsp">
                    <jsp:param name="name" value="quantity"/>
                    <jsp:param name="presetValue" value="${param.quantity}"/>
                </jsp:include>

                <input type="submit" value="<fmt:message key="submit.add"/>" class="btn btn-primary"/>
            </form>

            <div>
                <p class="text-danger">${param.error}</p>
                <c:if test="${param.success == 'true'}">
                    <p class="text-success"><fmt:message key="msg.success.cashier.addProductToCheck"/></p>
                </c:if>
            </div>
        </div>

        <c:if test="${requestScope.nOfPages != 0}">
            <div class="d-flex mb-2">
                <span class="ms-0 me-4">
                    <jsp:include page="sortSelect.jsp">
                        <jsp:param name="sort" value="${sort}"/>
                    </jsp:include>
                </span>

                <span>
                    <jsp:include page="../util/orderSelect.jsp">
                        <jsp:param name="order" value="${order}"/>
                    </jsp:include>
                </span>
            </div>

            <div>
                <jsp:include page="checkTable.jsp"/>

                <jsp:include page="pagination.jsp"/>

                <jsp:include page="../util/showPerPageSelect.jsp">
                    <jsp:param name="perPage" value="${perPage}"/>
                </jsp:include>
            </div>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>
