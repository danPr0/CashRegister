package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter every request
 */
@WebFilter("/*")
public class EveryRequestFilter implements Filter {
    /**
     * Set request and response encoding. Change some session attributes if they're in the params
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (httpRequest.getParameter("sessionLocale") != null) {
            httpRequest.getSession().setAttribute("lang", httpRequest.getParameter("sessionLocale"));
        }
        if (httpRequest.getParameter("searchType") != null) {
            httpRequest.getSession().setAttribute("searchType", httpRequest.getParameter("searchType"));
        }

        chain.doFilter(request, response);
    }
}
