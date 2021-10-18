package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.community.common.api.ApiResult;
import com.example.community.model.entity.BmsFollow;
import com.example.community.model.entity.UmsUser;
import com.example.community.service.BmsFollowService;
import com.example.community.service.UmsUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.example.community.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/relationship")
public class BmsRelationshipController {

    @Resource
    private BmsFollowService bmsFollowService;

    @Resource
    private UmsUserService umsUserService;


    @GetMapping("/subscribe/{userId}")
    public ApiResult<Object> handleFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        if (parentId.equals(umsUser.getId())) {
            return ApiResult.failed("您脸皮太厚了，怎么可以关注自己呢 😮");
        }
        BmsFollow follow = new BmsFollow();
        follow.setParentId(parentId);
        follow.setFollowerId(umsUser.getId());
        bmsFollowService.save(follow);
        UmsUser umsUser1=umsUserService.getUserById(parentId);
        umsUser1.setFollowercount(umsUser1.getFollowercount()+1);
        umsUserService.updateById(umsUser1);
        return ApiResult.success(null, "关注成功");
    }

    @GetMapping("/unsubscribe/{userId}")
    public ApiResult<Object> handleUnFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        bmsFollowService.remove(new LambdaQueryWrapper<BmsFollow>().eq(BmsFollow::getParentId, parentId)
                .eq(BmsFollow::getFollowerId, umsUser.getId()));
        UmsUser umsUser1=umsUserService.getUserById(parentId);
        umsUser1.setFollowercount(umsUser1.getFollowercount()-1);
        umsUserService.updateById(umsUser1);
        return ApiResult.success(null, "取关成功");
    }

    @GetMapping("/validate/{topicUserId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("topicUserId") String topicUserId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Map<String, Object> map = new HashMap<>();
        map.put("hasFollow", false);
        if (!ObjectUtils.isEmpty(umsUser)) {
            BmsFollow one = bmsFollowService.getOne(new LambdaQueryWrapper<BmsFollow>()
                    .eq(BmsFollow::getParentId, topicUserId)
                    .eq(BmsFollow::getFollowerId, umsUser.getId()));
            if (!ObjectUtils.isEmpty(one)) {
                map.put("hasFollow", true);
            }
        }
        return ApiResult.success(map);
    }
}
