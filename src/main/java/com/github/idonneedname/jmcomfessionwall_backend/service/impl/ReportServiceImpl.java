package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Report;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.ReportMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetUnCheckedReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetAllReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.Report.GetReportInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.ReportService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.PERMISSION_NOT_ALLOWED;
import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.REPORT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private ApiKeyHelper apiKeyHelper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public AjaxResult<Report> getReportInfo(GetReportInfoRequest req, String apiKey)
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", req.user_id);
        User user=userMapper.selectOne(wrapper);
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.report_id);
        Report report=reportMapper.selectOne(queryWrapper);
        if(report==null)
            throw new ApiException(REPORT_NOT_FOUND);
        if(user!=null)
        {
            if(user.type==2)
                return AjaxResult.success(report);
        }
        else
        if(report.host==req.user_id)
            return AjaxResult.success(report);
        else
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        return AjaxResult.success(null);
    }
    @Override
    public AjaxResult<List<Report>> getAllReports(GetAllReportsRequest req,String apiKey)
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", req.user_id);
        User user=userMapper.selectOne(wrapper);
        if(user.type!=2)
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        return AjaxResult.success(reportMapper.selectList(null));
    }
    @Override
    public AjaxResult<List<Report>> getAllUnchecked(GetUnCheckedReportsRequest req,String apiKey)
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", req.user_id);
        User user=userMapper.selectOne(wrapper);
        if(user.type!=2)
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        List<Report> reports=reportMapper.selectList(queryWrapper);
        return AjaxResult.success(reports);
    }
}
