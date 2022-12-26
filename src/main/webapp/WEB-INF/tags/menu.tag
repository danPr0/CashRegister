<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="url"%>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-3">
        <a href="<c:url value="/"/>" class="navbar-brand">
            <img src="<c:url value="/images?file=logo.png"/>" style="width: 5.5rem; height: 4.5rem;" alt="Logo"/>
        </a>

        <c:if test="${url != null && sessionScope.email != null}">
            <ul class="navbar-nav">
                <li class="nav-item ms-3">
                    <a href="<c:url value="${url}"/>" class="nav-link"  style="font-size: 1.3rem">
                        <fmt:message key="button.main"/>
                    </a>
                </li>
            </ul>
        </c:if>

        <ul class="navbar-nav ms-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'en'}">
                            <img src="<c:url value="/images?file=uk_flag.png"/>" class="rounded"
                                 style="width: 3rem; height: 2rem" alt="UK flag"/>
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value="/images?file=ukr_flag.png"/>" class="rounded"
                                 style="width: 3rem; height: 2rem" alt="UK flag"/>
                        </c:otherwise>
                    </c:choose>
                </a>
                <div class="dropdown-menu py-0" style="min-width: 3rem; background-color: gray">
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'en'}">
                            <a href="${pageContext.request.contextPath}?sessionLocale=ua"
                               class="dropdown-item px-1 py-1">
                                <img src="<c:url value="/images?file=ukr_flag.png"/>" class="rounded"
                                     style="width: 3rem; height: 2rem" alt="UK flag"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}?sessionLocale=en"
                               class="dropdown-item px-1 py-1">
                                <img src="<c:url value="/images?file=uk_flag.png"/>" class="rounded"
                                     style="width: 3rem; height: 2rem" alt="UK flag"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </li>
            <c:if test="${sessionScope.firstName != null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        <c:out value="${sessionScope.firstName}"/>
                    </a>
                    <div class="dropdown-menu py-1" style="min-width: auto">
                        <a href="<c:url value="/user/change-password"/>" class="dropdown-item">
                            <fmt:message key="button.changePassword"/>
                        </a>
                        <a href="<c:url value="/logout"/>" class="dropdown-item">
                            <fmt:message key="button.logout"/>
                        </a>
                    </div>
                </li>
            </c:if>
        </ul>
    </nav>
</fmt:bundle>