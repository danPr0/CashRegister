package controller.resources;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provide client with .js files
 */
@WebServlet("/js")
public class GetJsFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        try (InputStream in = req.getServletContext().getResourceAsStream("/js/" + fileName);
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
