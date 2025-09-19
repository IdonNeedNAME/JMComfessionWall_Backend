package com.github.idonneedname.jmcomfessionwall_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class BaseListResponse<T> {
    private List<T> list;
}
