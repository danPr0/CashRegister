package listener;

import util.db.ConnectionFactory;
import util.enums.Language;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("languages", Language.values());

        if (ConnectionFactory.getInstance().getConnection() == null)
            throw new RuntimeException();
    }
}
