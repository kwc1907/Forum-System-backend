package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.dto.CreateTopicDTO;
import com.example.community.model.entity.*;
import com.example.community.model.vo.PostVO;
import com.example.community.service.*;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.community.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/post")
public class BmsPostController {

    @Resource
    private BmsPostService bmsPostService;
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private BmsCommentService bmsCommentService;
    @Resource
    private BmsTopicTagService bmsTopicTagService;
    @Resource
    private BmsTagService bmsTagService;
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = bmsPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsPost topic = bmsPostService.create(dto, user);
        if(topic ==null)
        {
            return ApiResult.failed("话题已存在，请修改");
        }
        return ApiResult.success(topic);
    }
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = bmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }

    @GetMapping("/recommend")
    public ApiResult<List<BmsPost>> getRecommend(@RequestParam("topicId") String id) {
        List<BmsPost> topics = bmsPostService.getRecommend(id);
        return ApiResult.success(topics);
    }

    @PostMapping("/update")
    public ApiResult<BmsPost> update(@Valid @RequestBody BmsPost post) {
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        bmsPostService.updateById(post);
        return ApiResult.success(post);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName,
                                    @PathVariable("id") String id) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        bmsPostService.removeById(id);
        bmsCommentService.remove(new LambdaQueryWrapper<BmsComment>().eq(BmsComment::getTopicId, id));
        umsUser.setTopiccount(umsUser.getTopiccount()-1);
        umsUserService.updateById(umsUser);
        List<BmsTopicTag> bmsTopicTags=bmsTopicTagService.selectByTopicId(id);
        bmsTopicTags.forEach(topicTag -> {
           BmsTag bmsTag=bmsTagService.selectTagById(topicTag.getTagId());
           bmsTag.setTopicCount(bmsTag.getTopicCount()-1);
           bmsTagService.updateById(bmsTag);
        });
        bmsTopicTagService.remove(new LambdaQueryWrapper<BmsTopicTag>().eq(BmsTopicTag::getTopicId, id));
        return ApiResult.success(null,"删除成功");
    }

}
