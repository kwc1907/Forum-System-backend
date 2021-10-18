package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsBillboard;
import com.example.community.service.BmsBillbordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/billboard")
public class BmsBillboardController {
    @Resource
    private BmsBillbordService bmsBillbordService;

    @GetMapping("/show")
    public ApiResult<List<BmsBillboard>> getNotices(){
        List<BmsBillboard> list=bmsBillbordService.list(new
                LambdaQueryWrapper<BmsBillboard>().eq(BmsBillboard::isShow,true));
        return ApiResult.success(list);
    }
}
