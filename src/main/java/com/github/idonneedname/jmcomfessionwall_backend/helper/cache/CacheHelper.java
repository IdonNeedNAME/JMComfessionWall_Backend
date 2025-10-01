package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
 public class  CacheHelper<T> {
    public HashMap<Integer, T> map;
    public CacheHelper()
    {
        map = new HashMap<>();
    }
}

