package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Report;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetAllReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetReportInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetUnCheckedReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.List;

public interface ReportService {
    public AjaxResult<Report> getReportInfo(GetReportInfoRequest req, String apiKey);
    public AjaxResult<List<Report>> getAllReports(GetAllReportsRequest req,String apiKey);
    public AjaxResult<List<Report>> getAllUnchecked(GetUnCheckedReportsRequest req,String apiKey);
}
