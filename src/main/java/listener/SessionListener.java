package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * Session listener
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    /**
     * Set default values for lang, check and report tables parameters
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("lang", "ua");
        se.getSession().setAttribute("languages", List.of("en", "ua"));

        se.getSession().setAttribute("checkTotalPerPage", 1);
        se.getSession().setAttribute("checkSortBy", "default");
        se.getSession().setAttribute("checkOrderBy", "asc");

        se.getSession().setAttribute("reportTotalPerPage", 1);
        se.getSession().setAttribute("reportSortBy", "default");
        se.getSession().setAttribute("reportOrderBy", "asc");
    }
}
