<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"--%>
<%--        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"--%>
<%--        crossorigin="anonymous"></script>--%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <a href="<c:url value="/"/>" class="navbar-brand">Logo</a>
    <%--    <ul class="navbar-nav">--%>
    <%--        <li class="nav-item">--%>
    <%--            <a href="<c:url value="/"/>" class="nav-link">Main</a>--%>
    <%--        </li>--%>
    <%--    </ul>--%>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarLangDropdown" role="button" data-bs-toggle="dropdown"
               aria-expanded="false">
                ${sessionScope.lang}
            </a>
            <div class="dropdown-menu py-1" style="min-width: 3rem">
                <c:choose>
                    <c:when test="${sessionScope.lang == 'en'}">
                        <a href="?sessionLocale=ua" class="dropdown-item">Ua</a>
                    </c:when>
                    <c:otherwise>
                        <a href="?sessionLocale=en" class="dropdown-item">En</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <c:if test="${requestScope.username != null}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarNameDropdown" role="button"
                   data-bs-toggle="dropdown" aria-expanded="false">
                        ${requestScope.username}
                </a>
                <div class="dropdown-menu py-1" style="min-width: 5rem">
                    <a href="<c:url value="/logout"/>" class="dropdown-item">Logout</a>
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
