package com.github.idonneedname.jmcomfessionwall_backend.request.Report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReportInfoRequest {
    public int user_id;
    public int report_id;
}
