package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.*;
import com.example.community.model.vo.PostVO;
import com.example.community.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/managePost")
public class AdminPostController {
    @Resource
    private BmsPostService bmsPostService;
    @Resource
    private BmsCommentService bmsCommentService;
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private BmsTopicTagService bmsTopicTagService;
    @Resource
    private BmsTagService bmsTagService;
    @RequestMapping("/getAll")
    public ApiResult<Page<PostVO>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                          @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<PostVO> postVOPage=bmsPostService.getList(new Page<>(pageNo, pageSize),"latest");
        return ApiResult.success(postVOPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") String id)
    {
        BmsPost bmsPost=bmsPostService.getById(id);
        UmsUser umsUser=umsUserService.getUserById(bmsPost.getUserId());
        umsUser.setTopiccount(umsUser.getTopiccount()-1);
        umsUserService.updateById(umsUser);
        List<BmsTopicTag> bmsTopicTags=bmsTopicTagService.selectByTopicId(id);
        bmsTopicTags.forEach(topicTag -> {
            BmsTag bmsTag=bmsTagService.selectTagById(topicTag.getTagId());
            bmsTag.setTopicCount(bmsTag.getTopicCount()-1);
            bmsTagService.updateById(bmsTag);
        });
        bmsCommentService.remove(new LambdaQueryWrapper<BmsComment>().eq(BmsComment::getTopicId,id));
        bmsPostService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }
    @GetMapping("/detailOne/{id}")
    public ApiResult<Map<String, Object>> view(@PathVariable("id") String id) {
        Map<String, Object> map = bmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }
    @RequestMapping("/searchOne")
    public ApiResult<Page<PostVO>> searchOne(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                              @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "keyword") String keyword)
    {
        Page<PostVO> bmsPostPage=bmsPostService.searchByKey(keyword, new Page<>(pageNo, pageSize));
        return ApiResult.success(bmsPostPage);
    }
}
