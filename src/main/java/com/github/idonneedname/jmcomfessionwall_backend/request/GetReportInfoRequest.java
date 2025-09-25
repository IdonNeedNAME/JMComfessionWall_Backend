package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReportInfoRequest {
    public int user_id;
    public int report_id;
}
