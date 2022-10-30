package util.report;

import java.util.function.Supplier;

public enum ReportEnumFactory {
    csv(CsvReportCreator::new),
    pdf(PdfReportCreator::new),
    xls(XlsReportCreator::new);

    private Supplier<ReportCreator> instantiator;

    ReportEnumFactory(Supplier<ReportCreator> instantiator) {
        this.instantiator = instantiator;
    }

    public ReportCreator getInstance() {
        return instantiator.get();
    }
}
