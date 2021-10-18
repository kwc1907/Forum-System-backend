package com.example.community.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsTip;
import com.example.community.service.BmsTipService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manageTip")
public class AdminTipController {
    @Resource
    private BmsTipService bmsTipService;
    @RequestMapping("/getAll")
    public ApiResult<Page<BmsTip>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                          @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<BmsTip> bmsTipPage=bmsTipService.page(new Page<>(pageNo, pageSize));
        return ApiResult.success(bmsTipPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") Integer id)
    {
        bmsTipService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }

    @PostMapping("/insertOne")
    public ApiResult<String> insertOne(@RequestBody BmsTip bmsTip)
    {
        bmsTipService.save(bmsTip);
        return ApiResult.success(null,"添加成功");
    }
}
