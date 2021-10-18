package com.example.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.entity.BmsBillboard;

public interface BmsBillbordService extends IService<BmsBillboard> {
    void insertOne(String c);
}
