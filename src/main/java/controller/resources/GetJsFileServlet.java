package controller.resources;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/js")
public class GetJsFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");
        try (InputStream in = request.getServletContext().getResourceAsStream("/js/" + fileName);
             OutputStream out = response.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
