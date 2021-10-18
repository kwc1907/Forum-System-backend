package com.example.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.dto.RegisterDTO;
import com.example.community.model.entity.UmsUser;

public interface UmsUserService extends IService<UmsUser> {
    UmsUser executeRegister(RegisterDTO dto);
    UmsUser getUserById(String id);
    UmsUser getUserByUsername(String username);
    String executeLogin(LoginDTO dto);

    Page<UmsUser> searchKey(String key, Page<UmsUser> page);
}
