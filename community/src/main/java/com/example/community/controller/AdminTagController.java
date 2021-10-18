package com.example.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.entity.BmsTip;
import com.example.community.model.vo.TagVO;
import com.example.community.service.BmsTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/manageTag")
public class AdminTagController {

    @Resource
    private BmsTagService bmsTagService;

    @RequestMapping("/echar")
    public ApiResult<List<TagVO>> getTag(){
        List<TagVO> tag = bmsTagService.getTag();
        return ApiResult.success(tag);
    }
    @RequestMapping("/getAll")
    public ApiResult<Page<BmsTag>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                          @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<BmsTag> bmsTagPage=bmsTagService.page(new Page<>(pageNo, pageSize));
        return ApiResult.success(bmsTagPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") String id)
    {
        bmsTagService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }
}
