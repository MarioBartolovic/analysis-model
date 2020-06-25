package edu.hm.hafner.analysis;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import edu.hm.hafner.analysis.Report.IssuePrinter;
import edu.hm.hafner.analysis.Report.JavaUtilLoggingAdapter;
import edu.hm.hafner.analysis.Report.SLF4JAdapter;
import edu.hm.hafner.analysis.Report.StandardOutputPrinter;
import edu.hm.hafner.analysis.parser.checkstyle.CheckStyleParser;
import edu.hm.hafner.util.ResourceTest;

import static edu.hm.hafner.analysis.assertions.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests printing of reports using the {@link Report#print(Report.IssuePrinter)} method.
 *
 * @author Ullrich Hafner
 */
// TODO: Move implementation to ReportTest
class ReportPrinterTest extends ResourceTest {
    @BeforeAll
    static void beforeAll() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Test
    void shouldPrintAllIssues() {
        Report report = readCheckStyleReport();

        IssuePrinter printer = mock(IssuePrinter.class);
        report.print(printer);

        for (Issue issue : report) {
            verify(printer).print(issue);
        }
    }

    @Test
    void shouldPrintAllIssuesToJavaUtilLoggingAdapter() {
        Report report = readCheckStyleReport();
        Logger logger = mock(Logger.class);
        report.print(new JavaUtilLoggingAdapter(logger));

        for (Issue issue : report) {
            Severity severity = issue.getSeverity();
            if (Severity.ERROR.equals(severity)) {
                verify(logger).log(Level.SEVERE, issue.toString());
            }
            else if (Severity.WARNING_HIGH.equals(severity)) {
                verify(logger).log(Level.WARNING, issue.toString());
            }
            else if (Severity.WARNING_NORMAL.equals(severity)) {
                verify(logger).log(Level.INFO, issue.toString());
            }
            else if (Severity.WARNING_LOW.equals(severity)) {
                verify(logger).log(Level.FINE, issue.toString());
            }
        }
    }

    @Test
    void shouldPrintAllIssuesToSLF4JAdapter() {
        Report report = readCheckStyleReport();
        org.slf4j.Logger logger = mock(org.slf4j.Logger.class);
        report.print(new SLF4JAdapter(logger));

        for (Issue issue : report) {
            Severity severity = issue.getSeverity();

            if (Severity.ERROR.equals(severity)) {
                verify(logger).error(issue.toString());
            }
            else if (Severity.WARNING_HIGH.equals(severity)) {
                verify(logger).warn(issue.toString());
            }
            else if (Severity.WARNING_NORMAL.equals(severity)) {
                verify(logger).info(issue.toString());
            }
            else if (Severity.WARNING_LOW.equals(severity)) {
                verify(logger).trace(issue.toString());
            }
        }
    }

    @Test
    void shouldPrintAllIssuesToPrintStream() {
        Report report = readCheckStyleReport();

        try (PrintStream printStream = mock(PrintStream.class)) {
            report.print(new StandardOutputPrinter(printStream));

            for (Issue issue : report) {
                verify(printStream).println(issue.toString());
            }
        }
    }

    private Report readCheckStyleReport() {
        Report report = new CheckStyleParser().parse(read("parser/checkstyle/all-severities.xml"));
        report.add(new IssueBuilder().setSeverity(Severity.WARNING_HIGH).setMessage("Severity High warning").build());
        assertThat(report).hasSize(4);
        assertThat(report.getSeverities()).hasSize(4);
        return report;
    }

    private ReaderFactory read(final String fileName) {
        return new FileReaderFactory(getResourceAsFile(fileName), StandardCharsets.UTF_8);
    }
}
