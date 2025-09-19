package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.service.PostService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
}





