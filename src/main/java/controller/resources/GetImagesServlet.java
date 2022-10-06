package controller.resources;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/images")
public class GetImagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");
        response.setContentType("image/png");
        try (InputStream in = request.getServletContext().getResourceAsStream("/images/" + fileName);
             OutputStream out = response.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}