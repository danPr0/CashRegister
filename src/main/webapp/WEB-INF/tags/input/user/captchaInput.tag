<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" isELIgnored="false"%>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:bundle basename="messages">
    <div class="my-3">
        <div class="input-group">
            <img src="data:image/png;base64,${requestScope.captchaImage}" style="border-radius: 6px; height: 2.4rem" class="me-1"  alt="captcha"/>
            <input type="text" name="captcha" required class="form-control" placeholder="Enter code from picture"/>
        </div>
    </div>
</fmt:bundle>
