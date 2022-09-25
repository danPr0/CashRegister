package controller.senior_cashier;

import entity.ReportElement;
import jxl.LabelCell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/download-xlsx-report")
public class DownloadXlsxReportServlet extends HttpServlet {
    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Report");
//        XSSFRow header = sheet.createRow(0);
//
//        XSSFCell headerCell;
//        headerCell = header.createCell(0);
//        headerCell.setCellValue("User");
//        headerCell = header.createCell(1);
//        headerCell.setCellValue("Date");
//        headerCell = header.createCell(2);
//        headerCell.setCellValue("Items");
//        headerCell = header.createCell(3);
//        headerCell.setCellValue("Price");
//
//        List<ReportElement> report = reportService.getAll();
//        report.forEach((r) -> {
//            XSSFRow row = sheet.createRow(report.indexOf(r) + 1);
//            XSSFCell cell;
//            cell = row.createCell(0);
//            cell.setCellValue(r.getUsername());
//            cell = row.createCell(1);
//            cell.setCellValue(r.getClosed_at());
//            cell = row.createCell(2);
//            cell.setCellValue(r.getItems_quantity());
//            cell = row.createCell(3);
//            cell.setCellValue(r.getTotal_price());
//        });
//
//        FileOutputStream outputStream = new FileOutputStream(new File(req.getServletContext().getRealPath("") + "/WEB-INF/reports/report.xlsx"));
//        workbook.write(outputStream);
//        workbook.write(new File(req.getServletContext().getRealPath("") + "/WEB-INF/reports/report.xlsx"));
//        workbook.close();
//        outputStream.flush();
//        outputStream.close();
//
        WritableWorkbook workbook = Workbook.createWorkbook(new File(req.getServletContext().getRealPath("") + "/WEB-INF/reports/report.xls"));
        WritableSheet sheet = workbook.createSheet("Report", 0);
        try {
            sheet.addCell(new Label(0, 0, "User"));
            sheet.addCell(new Label(1, 0, "Date"));
            sheet.addCell(new Label(2, 0, "Items"));
            sheet.addCell(new Label(3, 0, "Price"));
        } catch (WriteException e) {
            e.printStackTrace();
        }

        List<ReportElement> report = reportService.getAll();
        report.forEach((r) -> {
            int index = report.indexOf(r);
            try {
                sheet.addCell(new Label(0, index + 1, r.getUsername()));
                sheet.addCell(new Label(1, index + 1, r.getClosed_at().toString()));
                sheet.addCell(new Number(2, index + 1, r.getItems_quantity()));
                sheet.addCell(new Number(3, index + 1, r.getTotal_price()));
            } catch (WriteException e) {
                e.printStackTrace();
            }
        });

        workbook.write();
        try {
            workbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        resp.setHeader("Content-disposition", "attachment; filename=report.xls");
        try (InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/reports/report.xls");
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }
}
