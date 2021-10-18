package com.example.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.api.ApiResult;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.dto.RegisterDTO;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.UmsAdmin;
import com.example.community.model.entity.UmsUser;
import com.example.community.service.BmsPostService;
import com.example.community.service.UmsAdminService;
import com.example.community.service.UmsUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.example.community.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/ums/user")
public class UmsUserController {
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private BmsPostService bmsPostService;
    @Resource
    private UmsAdminService umsAdminService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        UmsUser user = umsUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号或邮箱已存在！账号注册失败。");
        }
        UmsAdmin umsAdmin=umsAdminService.getAdminByAdminname(dto.getName());
        if(!ObjectUtils.isEmpty(umsAdmin))
        {
            return ApiResult.failed("账号或邮箱已存在！账号注册失败。");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = umsUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<UmsUser> getUser(@RequestHeader(value = USER_NAME) String userName) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        return ApiResult.success(user);
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByName(@PathVariable("username") String username,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>();
        UmsUser user = umsUserService.getUserByUsername(username);
        Page<BmsPost> page = bmsPostService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }
    @PostMapping("/update")
    public ApiResult<UmsUser> updateUser(@RequestBody UmsUser umsUser) {
        umsUserService.updateById(umsUser);
        return ApiResult.success(umsUser);
    }
}
