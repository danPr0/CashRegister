package util.table;

import javax.servlet.http.HttpServletRequest;

public class TableService {
    public static String getTableSortParam(HttpServletRequest req, String param, String attrInSession) {
        String result;
        if (req.getParameter(param) != null) {
            result = req.getParameter(param);
            req.getSession().setAttribute(attrInSession, result);
        }
        else result = req.getSession().getAttribute(attrInSession).toString();

        return result;
    }

    public static int getTotalPerPage(HttpServletRequest req, String param, String attrInSession ) {
        int result;
        try {
            result = Integer.parseInt(req.getParameter(param));
            req.getSession().setAttribute(attrInSession, result);
        }
        catch (NumberFormatException e) {
            result = Integer.parseInt(req.getSession().getAttribute(attrInSession).toString());
        }

        return result;
    }
}
