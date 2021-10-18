package com.example.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsPromotion;
import com.example.community.model.entity.BmsTip;
import com.example.community.service.BmsPromotionSevice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/managePromotion")
public class AdminPromotionController {
    @Resource
    private BmsPromotionSevice bmsPromotionSevice;

    @RequestMapping("/getAll")
    public ApiResult<Page<BmsPromotion>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                                @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<BmsPromotion> bmsPromotionPage=bmsPromotionSevice.page(new Page<>(pageNo, pageSize));
        return ApiResult.success(bmsPromotionPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") Integer id)
    {
        bmsPromotionSevice.removeById(id);
        return ApiResult.success(null,"删除成功");
    }
    @PostMapping("/insertOne")
    public ApiResult<String> insertOne(@RequestBody BmsPromotion bmsPromotion)
    {
        bmsPromotionSevice.save(bmsPromotion);
        return ApiResult.success(null,"添加成功");
    }
}
