<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <a href="<c:url value="/"/>" class="navbar-brand">
            <img src="<c:url value="/images?file=logo.png"/>" style="width: 5.5rem; height: 4.5rem;" alt="Logo"/>
        </a>
            <%--    <ul class="navbar-nav">--%>
            <%--        <li class="nav-item">--%>
            <%--            <a href="<c:url value="/"/>" class="nav-link">Main</a>--%>
            <%--        </li>--%>
            <%--    </ul>--%>
        <ul class="navbar-nav ml-auto">
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
                <div class="dropdown-menu py-1" style="min-width: 3rem">
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'en'}">
                            <a href="?sessionLocale=ua" class="dropdown-item px-1 py-0">
                                <img src="<c:url value="/images?file=ukr_flag.png"/>" class="rounded"
                                     style="width: 3rem; height: 2rem" alt="UK flag"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="?sessionLocale=en" class="dropdown-item px-1 py-0">
                                <img src="<c:url value="/images?file=uk_flag.png"/>" class="rounded"
                                     style="width: 3rem; height: 2rem" alt="UK flag"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </li>
            <c:if test="${sessionScope.username != null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            ${sessionScope.username}
                    </a>
                    <div class="dropdown-menu py-1" style="min-width: 5rem">
                        <a href="<c:url value="/logout"/>" class="dropdown-item">
                            <fmt:message key="button.logout"/>
                        </a>
                    </div>
                </li>
            </c:if>
                <%--        <c:choose>--%>
                <%--            <c:when test="${requestScope.username != null}">--%>
                <%--                <li class="nav-item dropdown">--%>
                <%--                    <a class="nav-link dropdown-toggle" href="#" id="navbarNameDropdown" role="button" data-bs-toggle="dropdown">--%>
                <%--                            ${applicationScope.username}--%>
                <%--                    </a>--%>
                <%--                    <div class="dropdown-menu py-1" style="min-width: 5rem">--%>
                <%--                        <a href="<c:url value="/logout"/>" class="dropdown-item">Logout</a>--%>
                <%--                    </div>--%>
                <%--                </li>--%>
                <%--            </c:when>--%>
                <%--            <c:otherwise>--%>
                <%--                <a href="<c:url value="/auth/login"/>" class="nav-item nav-link">Login</a>--%>
                <%--            </c:otherwise>--%>
                <%--        </c:choose>--%>
        </ul>
            <%--    <ul>--%>
            <%--        <li><a href="?sessionLocale=en">English</a></li>--%>
            <%--        <li><a href="?sessionLocale=ua">Ukrainian</a></li>--%>
            <%--    </ul>--%>
    </nav>
</fmt:bundle>