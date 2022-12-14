<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input/user" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<fmt:bundle basename="messages">
    <head>
        <title><fmt:message key="title.admin.authorizeUser"/></title>

        <link rel="stylesheet" href="<c:url value="/css?file=bootstrap.min.css"/>"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="<c:url value="/js?file=formValidation.js"/>"></script>
    </head>
    <body>
    <div class="container p-3 px-5 bg-dark text-white" style="min-height: 100%">
        <e:menu/>

        <div class="row">
            <div class="col">
                <hr>
            </div>
            <div class="col-auto"><fmt:message key="title.admin.authorizeUser"/></div>
            <div class="col">
                <hr>
            </div>
        </div>

        <div class="mb-4">
            <form action="<c:url value="/admin/authorize-user"/>" method="get" class="needs-validation col-4"
                  novalidate>
                <input:emailInput/>

                <input type="submit" value="<fmt:message key="submit.find"/>" class="btn btn-primary"/>
            </form>

            <p class="text-danger"><c:out value="${requestScope.error}"/></p>
        </div>

        <c:if test="${requestScope.user != null}">
            <div>
                <p class="mb-0 text-info"><fmt:message key="msg.info.user.email"/> : <c:out value="${requestScope.user.email}"/></p>
                <p class="mb-0 text-info"><fmt:message key="msg.info.user.firstName"/>: <c:out value="${requestScope.user.firstName}"/></p>
                <p class="mb-0 text-info"><fmt:message key="msg.info.user.secondName"/>: <c:out value="${requestScope.user.secondName}"/></p>
                <p class="mb-3 text-info"><fmt:message key="msg.info.user.role"/> : <fmt:message key="select.userRole.${requestScope.user.role}"/></p>
            </div>

            <div>
                <form action="<c:url value="/admin/authorize-user?email=${requestScope.user.email}"/>" method="post"
                      class="needs-validation col-4" novalidate>
                    <label for="roleSelect" class="form-label"><fmt:message key="label.newUserRole"/></label>
                    <select class="form-select mb-2" required name="role" id="roleSelect">
                        <c:forEach var="i" items="${requestScope.roles}">
                            <option value="${i}"
                                    <c:if test="${i == requestScope.user.role}">selected disabled</c:if>>
                                <fmt:message key="select.userRole.${i}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="<fmt:message key="submit.ok"/>" class="btn btn-primary"/>
                </form>
            </div>

            <div>
                <p class="text-danger"><c:out value="${param.error}"/></p>
                <c:if test="${param.success == 'true'}">
                    <p class="text-success"><fmt:message key="msg.success.admin.changeRole"/></p>
                </c:if>
            </div>
        </c:if>
    </div>
    </body>
</fmt:bundle>
</html>

