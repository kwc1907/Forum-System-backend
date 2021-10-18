package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.entity.UmsAdmin;


public interface UmsAdminService extends IService<UmsAdmin> {
    String executeLogin(LoginDTO dto);
    UmsAdmin getAdminByAdminname(String username);
}
