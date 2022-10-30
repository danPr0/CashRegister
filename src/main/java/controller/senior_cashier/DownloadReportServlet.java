package controller.senior_cashier;

import util.report.ReportEnumFactory;
import util.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Download X or Z reports
 */
@WebServlet("/senior-cashier/download-report")
public class DownloadReportServlet extends HttpServlet {
    /**
     * Create .csv, .pdf or .xls files with X or Z report
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reportType = req.getParameter("reportType");
        ReportEnumFactory reportEnumFactory = ReportEnumFactory.valueOf(req.getParameter("format"));
        Language lang = Language.valueOf(req.getSession().getAttribute("lang").toString());

        String userDir = req.getServletContext().getAttribute("FILES_DIR") + File.separator + req.getSession().getAttribute("email");
//        File userFolder = new File(userDir);
//        if (!userFolder.exists())
//            userFolder.mkdirs();

        String filename = reportType.equals("z") ? "z-report" : "x-report";
        String filepath = userDir + File.separator + filename + "." + reportEnumFactory.name();
        reportEnumFactory.getInstance().createReport(filepath, lang);

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(Date.from(Instant.now()));
        resp.setHeader("Content-disposition", "attachment; filename=" + filename + "_" + cal.get(Calendar.DAY_OF_MONTH) +
                "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR) + "." + reportEnumFactory);
        try (InputStream in = new FileInputStream(filepath);
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
