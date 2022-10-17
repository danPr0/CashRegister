package controller.resources;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provide client with images
 */
@WebServlet("/images")
public class GetImagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        resp.setContentType("image/png");
        try (InputStream in = req.getServletContext().getResourceAsStream("/images/" + fileName);
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}