package controller.senior_cashier;

import util.ReportFileCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/senior-cashier/download-report")
public class DownloadReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reportType = req.getParameter("reportType");
        String format = req.getParameter("format");
        String fileName;

        if (!(format.equals("csv") || format.equals("pdf") || format.equals("xls"))) {
            resp.sendError(401);
            return;
        }

        if (reportType.equals("z")) {
            fileName = "z-report." + format;
        }
        else {
            fileName = "x-report." + format;
            if (format.equals("csv"))
                ReportFileCreator.createCsv(fileName, req.getServletContext());
            else if (format.equals("pdf"))
                ReportFileCreator.createPdf(fileName, req.getServletContext());
            else
                ReportFileCreator.createXls(fileName, req.getServletContext());
        }

        resp.setHeader("Content-disposition", "attachment; filename=" + fileName);
        try (InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/reports/" + fileName);
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
