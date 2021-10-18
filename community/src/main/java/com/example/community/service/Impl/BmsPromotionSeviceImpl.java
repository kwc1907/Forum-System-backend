package com.example.community.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsPromotionMapper;
import com.example.community.model.entity.BmsPromotion;
import com.example.community.service.BmsPromotionSevice;
import org.springframework.stereotype.Service;

@Service
public class BmsPromotionSeviceImpl extends ServiceImpl<BmsPromotionMapper, BmsPromotion>
        implements BmsPromotionSevice {

}
