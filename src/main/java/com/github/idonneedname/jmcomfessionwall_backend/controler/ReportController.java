package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Report;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.ReportMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetAllReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetReportInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetUnCheckedReportsRequest;
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
    @GetMapping("")
    public AjaxResult<Report> getReportInfo(@RequestBody GetReportInfoRequest req,@RequestHeader("X-API-KEY") String apiKey){
        return reportService.getReportInfo(req,apiKey);
    }
    @GetMapping("/all")
    public AjaxResult<List<Report>> getAllReport(@RequestBody GetAllReportsRequest req,@RequestHeader("X-API-KEY") String apiKey){
        return reportService.getAllReports(req,apiKey);
    }
    @GetMapping("/unjudged")
    public AjaxResult<List<Report>> getAllUnjudged(@RequestBody GetUnCheckedReportsRequest req,@RequestHeader("X-API-KEY") String apiKey){
        return reportService.getAllUnchecked(req,apiKey);
    }
}
