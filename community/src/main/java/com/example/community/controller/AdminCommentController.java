package com.example.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.vo.Comment1VO;
import com.example.community.service.BmsCommentService;
import com.example.community.service.BmsPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manageComment")
public class AdminCommentController {
    @Resource
    private BmsCommentService bmsCommentService;
    @Resource
    private BmsPostService bmsPostService;

    @RequestMapping("/getAll")
    public ApiResult<Page<Comment1VO>> getAll(@RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                              @RequestParam(value = "size", defaultValue = "10") Integer pageSize)
    {
        Page<Comment1VO> comment1VOPage=bmsCommentService.getList(new Page<>(pageNo, pageSize));
        return ApiResult.success(comment1VOPage);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ApiResult<String> deleteOne(@PathVariable("id") String id)
    {
        BmsComment bmsComment=bmsCommentService.selectById(id);
        BmsPost bmsPost=bmsPostService.getById(bmsComment.getTopicId());
        bmsPost.setComments(bmsPost.getComments()-1);
        bmsPostService.updateById(bmsPost);
        bmsCommentService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }

}
