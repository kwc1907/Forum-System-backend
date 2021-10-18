package com.example.community.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsTipMapper;
import com.example.community.model.entity.BmsTip;
import com.example.community.service.BmsTipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BmsTipServiceImpl extends ServiceImpl<BmsTipMapper, BmsTip> implements BmsTipService {

    @Override
    public BmsTip getRandomTip() {
        BmsTip todayTip = null;
        try {
            todayTip = this.baseMapper.getRandomTip();
        } catch (Exception e) {
            log.info("tip转化失败");
        }
        return todayTip;
    }

}

