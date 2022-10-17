package controller.resources;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provide client with .css files
 */
@WebServlet("/css")
public class GetCssFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        try (InputStream in = req.getServletContext().getResourceAsStream("/css/" + fileName);
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
