package controller.senior_cashier;

import entity.ReportElement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import service_impl.ReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/download-csv-report")
public class DownloadCsvReportServlet extends HttpServlet {
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File(req.getServletContext().getRealPath("") + "/WEB-INF/reports/report.csv");
        FileWriter fileWriter = new FileWriter(file);

        try (CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder().setHeader("User", "Date", "Items", "Price").build())) {
            List<ReportElement> report = reportServiceImpl.getAll();
            report.forEach((r -> {
                try {
                    printer.printRecord(r.getCreatedBy(), r.getClosed_at(), r.getItems_quantity(), r.getTotal_price());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }

//        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=report.csv");
        try (InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/reports/report.csv");
            OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());

//            byte[] buffer = new byte[512];
//            int numBytesRead;
//            while ((numBytesRead = in.read(buffer)) > 0) {
//                out.write(buffer, 0, numBytesRead);
//            }
        }
    }
}
