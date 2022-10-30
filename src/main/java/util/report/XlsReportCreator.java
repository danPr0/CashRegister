package util.report;

import dto.ReportDTO;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ReportService;
import service_impl.ReportServiceImpl;
import util.enums.Language;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static util.GetProperties.getMessageByLang;

public class XlsReportCreator implements ReportCreator {
    private final ReportService reportService = ReportServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(XlsReportCreator.class);

    @Override
    public void createReport(String filepath, Language lang) {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filepath));
            WritableSheet sheet = workbook.createSheet("Report", 0);

            sheet.addCell(new Label(0, 0, "#"));
            sheet.addCell(new Label(1, 0, getMessageByLang("table.report.createdBy", lang)));
            sheet.addCell(new Label(2, 0, getMessageByLang("table.report.createdAt", lang)));
            sheet.addCell(new Label(3, 0, getMessageByLang("table.report.quantity", lang)));
            sheet.addCell(new Label(4, 0, getMessageByLang("table.report.price", lang)));

            List<ReportDTO> report = reportService.convertToDTO(reportService.getAll(), lang);
            for (ReportDTO r : report) {
                int index = report.indexOf(r);
                sheet.addCell(new Number(0, index + 1, r.getIndex()));
                sheet.addCell(new Label(1, index + 1, r.getCreatedBy()));
                sheet.addCell(new Label(2, index + 1, r.getClosedAt()));
                sheet.addCell(new Number(3, index + 1, r.getItemsQuantity()));
                sheet.addCell(new Label(4, index + 1, r.getTotalPrice()));
            }

            workbook.write();
            workbook.close();
        } catch (WriteException | IOException e) {
            logger.error("Cannot create xls report", e.getCause());
        }
    }
}
