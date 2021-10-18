package com.example.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsBillboard;
import com.example.community.service.BmsBillbordService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/manageBillboard")
public class AdminBillboardController {
    @Resource
    private BmsBillbordService bmsBillbordService;
    @RequestMapping("/getAll")
    public ApiResult<Page<BmsBillboard>> getAll( @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
       Page<BmsBillboard> bmsBillboards=bmsBillbordService.page(new Page<>(pageNo, pageSize));
        return ApiResult.success(bmsBillboards);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") Integer id)
    {
        bmsBillbordService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }

    @RequestMapping("/disableOne/{id}")
    public ApiResult<String> disableOne(@PathVariable("id") Integer id)
    {
        BmsBillboard bmsBillboard=bmsBillbordService.getById(id);
        bmsBillboard.setShow(false);
        bmsBillbordService.updateById(bmsBillboard);
        return ApiResult.success(null,"禁用成功");
    }
    @PostMapping("/ableOne/{id}")
    public ApiResult<String> ableOne(@PathVariable("id") Integer id)
    {
        BmsBillboard bmsBillboard=bmsBillbordService.getById(id);
        bmsBillboard.setShow(true);
        bmsBillbordService.updateById(bmsBillboard);
        return ApiResult.success(null,"已启用");
    }

    @PostMapping("/insertOne/{data}")
    public ApiResult<String> insertOne(@PathVariable("data") String content)
    {
        bmsBillbordService.insertOne(content);
        return ApiResult.success(null,"添加成功");
    }


}
