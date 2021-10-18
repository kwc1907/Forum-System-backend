package com.example.community.controller;

import com.example.community.common.api.ApiResult;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.entity.UmsAdmin;
import com.example.community.model.entity.UmsUser;
import com.example.community.service.UmsAdminService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.example.community.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/ums/admin")
public class UmsAdminController {

    @Resource
    private UmsAdminService umsAdminService;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = umsAdminService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("adminname",dto.getName());
        return ApiResult.success(map, "登录成功");
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<UmsAdmin> getUser(@RequestHeader(value = USER_NAME) String userName) {
        UmsAdmin admin = umsAdminService.getAdminByAdminname(userName);
        return ApiResult.success(admin);
    }
}
