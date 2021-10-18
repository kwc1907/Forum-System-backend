package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.BmsTip;
import com.example.community.model.entity.UmsUser;
import com.example.community.service.BmsCommentService;
import com.example.community.service.BmsPostService;
import com.example.community.service.UmsUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manageUser")
public class AdminUserController {
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private BmsPostService bmsPostServicem;
    @Resource
    private BmsCommentService bmsCommentService;
    @RequestMapping("/getAll")
    public ApiResult<Page<UmsUser>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                           @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<UmsUser> umsUserPage=umsUserService.page(new Page<>(pageNo, pageSize));
        return ApiResult.success(umsUserPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") String id)
    {
        umsUserService.removeById(id);
        bmsPostServicem.remove(new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getUserId, id));
        bmsCommentService.remove(new LambdaQueryWrapper<BmsComment>().eq(BmsComment::getUserId,id));
        return ApiResult.success(null,"删除成功");
    }

    @RequestMapping("/searchOne")
    public ApiResult<Page<UmsUser>> searchOne(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                              @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "key") String key)
    {
        Page<UmsUser> umsUserPage=umsUserService.searchKey(key,new Page<>(pageNo, pageSize));
        return ApiResult.success(umsUserPage);
    }
}
