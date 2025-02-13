package de.thb.webbaki.controller;

import com.lowagie.text.DocumentException;
import de.thb.webbaki.entity.snapshot.Snapshot;
import de.thb.webbaki.enums.ReportFocus;
import de.thb.webbaki.service.*;
import de.thb.webbaki.service.Exceptions.UnknownReportFocusException;
import de.thb.webbaki.service.helper.Counter;
import de.thb.webbaki.service.helper.MappingReport;
import de.thb.webbaki.service.snapshot.ReportService;
import de.thb.webbaki.service.snapshot.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private MasterScenarioService masterScenarioService;
    @Autowired
    private SnapshotService snapshotService;

    /**
     * Redirect to url with newest snap-id (because of the Nav elements in the layout.html).
     * @param reportFocusString
     */
    @GetMapping("report/{reportFocus}")
    public String showReport(@PathVariable("reportFocus") String reportFocusString){
        long snapId = snapshotService.getNewestSnapshot().getId();
        return "redirect:/report/"+reportFocusString+"/"+String.valueOf(snapId);
    }

    @GetMapping("report/{reportFocus}/{snapId}")
    public String showReport(@PathVariable("reportFocus") String reportFocusString, @PathVariable("snapId") long snapId,
                             Model model, Authentication authentication) throws UnknownReportFocusException {
        final var masterScenarioList = masterScenarioService.getAllOrderByPositionInRow();
        model.addAttribute("masterScenarioList",masterScenarioList);
        ReportFocus reportFocus = ReportFocus.getReportFocusByEnglishRepresentation(reportFocusString);
        model.addAttribute("reportFocus", reportFocus);

        Snapshot currentSnapshot = snapshotService.getSnapshotByID(snapId).get();
        model.addAttribute("currentSnapshot", currentSnapshot);

        MappingReport report = reportService.getMappingReportByReportFocus(reportFocus, authentication.getName(), currentSnapshot);
        model.addAttribute("report", report);

        final List<Snapshot> snapshotList = snapshotService.getAllSnapshotOrderByDESC();
        model.addAttribute("snapshotList", snapshotList);

        model.addAttribute("counter", new Counter());

        return "report/report_container";
    }

    @GetMapping("report/{reportFocus}/{snapId}/download")
    public void downloadReportPdf(@PathVariable("reportFocus") String reportFocusString, @PathVariable("snapId") long snapId,
                                  HttpServletResponse response, Authentication authentication, HttpServletRequest request) throws UnknownReportFocusException, IOException, DocumentException {

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.pdf";
        response.setHeader(headerKey, headerValue);
        Context context = new Context();
        final var masterScenarioList = masterScenarioService.getAllOrderByPositionInRow();
        context.setVariable("masterScenarioList",masterScenarioList);
        ReportFocus reportFocus = ReportFocus.getReportFocusByEnglishRepresentation(reportFocusString);
        context.setVariable("reportFocus", reportFocus);

        Snapshot currentSnapshot = snapshotService.getSnapshotByID(snapId).get();
        context.setVariable("currentSnapshot", currentSnapshot);

        MappingReport report = reportService.getMappingReportByReportFocus(reportFocus, authentication.getName(), currentSnapshot);
        context.setVariable("report", report);

        context.setVariable("counter", new Counter());

        reportService.generatePdfFromHtml(reportService.parseThymeleafTemplateToHtml("report/report", context),
                request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort(), response.getOutputStream());

    }

}