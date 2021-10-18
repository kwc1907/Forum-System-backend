package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.jwt.JwtUtil;
import com.example.community.mapper.UmsAdminMapper;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.entity.UmsAdmin;
import com.example.community.service.UmsAdminService;
import com.example.community.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            UmsAdmin admin = getAdminByAdminname(dto.getName());
            String encodePwd = MD5Utils.getPwd(dto.getPass());

            if(!encodePwd.equals(admin.getPassword()))
            {
                log.warn(encodePwd);
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(admin.getUsername()));
        } catch (Exception e) {
            log.warn("管理员不存在or密码验证失败=======>{}", dto.getName());
        }
        return token;
    }

    @Override
    public UmsAdmin getAdminByAdminname(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsAdmin>().eq(UmsAdmin::getUsername, username));
    }
}

