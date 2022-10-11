<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="page" value="${param.page == null ? '1' : param.page}"/>

<fmt:bundle basename="messages">
    <div class="m-3">
        <ul class="pagination">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a href="<c:url value="/senior-cashier/create-x-report?page=${page - 1}&sortBy=${param.sort}"/>"
                   class="page-link">
                    <fmt:message key="pagination.previous"/>
                </a>
            </li>
            <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
                <li class="page-item <c:if test="${page == i}">active</c:if>">
                    <a href="<c:url value="/senior-cashier/create-x-report?page=${i}&sortBy=${param.sort}"/>"
                       class="page-link">
                            ${i}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${page == requestScope.nOfPages}">disabled</c:if>">
                <a href="<c:url value="/senior-cashier/create-x-report?page=${page + 1}&sortBy=${param.sort}"/>"
                   class="page-link">
                    <fmt:message key="pagination.next"/>
                </a>
            </li>
        </ul>
    </div>
</fmt:bundle>