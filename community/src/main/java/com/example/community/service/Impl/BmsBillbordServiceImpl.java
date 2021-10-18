package com.example.community.service.Impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsBillbordMapper;
import com.example.community.model.entity.BmsBillboard;
import com.example.community.model.vo.PostVO;
import com.example.community.service.BmsBillbordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BmsBillbordServiceImpl extends ServiceImpl<BmsBillbordMapper,BmsBillboard> implements BmsBillbordService {

    @Override
    public void insertOne(String c) {
        BmsBillboard bmsBillboard=BmsBillboard.builder().content(c).createTime(new Date()).show(true).build();
        this.baseMapper.insert(bmsBillboard);
    }

}
