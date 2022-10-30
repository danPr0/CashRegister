<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="url" required="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<c:set var="page" value="${param.page == null ? '1' : param.page}"/>

<fmt:bundle basename="messages">
    <div>
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${page == 1}">disabled</c:if>">
                <a href="<c:url value="${url}?page=${page - 1}"/>"
                   class="page-link">
                    <fmt:message key="pagination.previous"/>
                </a>
            </li>
            <c:forEach var="i" begin="1" end="${requestScope.nOfPages}">
                <li class="page-item <c:if test="${page == i}">active</c:if>">
                    <a href="<c:url value="${url}?page=${i}"/>"
                       class="page-link">
                        <c:out value="${i}"/>
                    </a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${page == requestScope.nOfPages}">disabled</c:if>">
                <a href="<c:url value="${url}?page=${page + 1}"/>"
                   class="page-link">
                    <fmt:message key="pagination.next"/>
                </a>
            </li>
        </ul>
    </div>
</fmt:bundle>