package com.example.community.controller;

import com.example.community.common.api.ApiResult;
import com.example.community.model.dto.CommentDTO;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.CommentVO;
import com.example.community.service.BmsCommentService;
import com.example.community.service.BmsPostService;
import com.example.community.service.UmsUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.example.community.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/comment")
public class BmsCommentController {

    @Resource
    private BmsCommentService bmsCommentService;
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private BmsPostService bmsPostService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = bmsCommentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }
    @PostMapping("/add_comment")
    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                             @RequestBody CommentDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsComment comment = bmsCommentService.create(dto, user);
        BmsPost bmsPost=bmsPostService.getById(dto.getTopic_id());
        bmsPost.setComments(bmsPost.getComments()+1);
        bmsPostService.updateById(bmsPost);
        return ApiResult.success(comment);
    }
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete_comment(@PathVariable("id") String id)
    {
        BmsComment comment=bmsCommentService.getById(id);
        BmsPost bmsPost=bmsPostService.getById(comment.getTopicId());
        bmsPost.setComments(bmsPost.getComments()-1);
        bmsPostService.updateById(bmsPost);
        bmsCommentService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }
}
