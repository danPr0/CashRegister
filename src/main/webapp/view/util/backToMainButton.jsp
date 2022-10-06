<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="d-flex">
        <div class="mb-3 mr-3 ml-auto">
            <a class="card-link" href="<c:url value="/"/>">
                <img src="<c:url value="/images?file=back_arrow.png"/>" style="width: 1.5rem; height: 1rem" alt="back"/>
                <fmt:message key="button.backToMain"/>
            </a>
        </div>
    </div>

</fmt:bundle>
