package com.example.community.controller;

import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsPromotion;
import com.example.community.service.BmsPromotionSevice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class BmsPromotionController {
    @Resource
    private BmsPromotionSevice bmsPromotionSevice;

    @GetMapping("/all")
    public ApiResult<List<BmsPromotion>> list()
    {
        List<BmsPromotion> list=bmsPromotionSevice.list();
        return ApiResult.success(list);
    }
}
