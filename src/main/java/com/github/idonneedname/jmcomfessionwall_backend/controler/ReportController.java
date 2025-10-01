package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Report;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetUnCheckedReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetAllReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetReportInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.ReportServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@Slf4j
public class ReportController {
    @Resource
    private ReportServiceImpl reportService;
    @Resource
    private ApiKeyHelper apiKeyHelper;

    @GetMapping("/{id}")
    public AjaxResult<Report> getReportInfo(@PathVariable int id,@RequestHeader("X-API-KEY") String apiKey){
        GetReportInfoRequest req=new GetReportInfoRequest();
        req.user_id = apiKeyHelper.getUserId(apiKey);
        req.report_id=id;
        return reportService.getReportInfo(req,apiKey);
    }
    @GetMapping("/all")
    public AjaxResult<List<Report>> getAllReport(@RequestHeader("X-API-KEY") String apiKey){
        GetAllReportsRequest req=new GetAllReportsRequest();
        req.user_id = apiKeyHelper.getUserId(apiKey);
        return reportService.getAllReports(req,apiKey);
    }
    @GetMapping("/unjudged")
    public AjaxResult<List<Report>> getAllUnjudged(@RequestHeader("X-API-KEY") String apiKey){
        GetUnCheckedReportsRequest req=new GetUnCheckedReportsRequest();
        req.user_id = apiKeyHelper.getUserId(apiKey);
        return reportService.getAllUnchecked(req,apiKey);
    }
}
