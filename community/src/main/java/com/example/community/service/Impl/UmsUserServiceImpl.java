package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.jwt.JwtUtil;
import com.example.community.mapper.BmsFollowMapper;
import com.example.community.mapper.BmsTopicMapper;
import com.example.community.mapper.UmsUserMapper;
import com.example.community.model.dto.LoginDTO;
import com.example.community.model.dto.RegisterDTO;
import com.example.community.model.entity.UmsAdmin;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.PostVO;
import com.example.community.service.UmsAdminService;
import com.example.community.service.UmsUserService;
import com.example.community.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;



@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {

    @Autowired
    private BmsTopicMapper bmsTopicMapper;
    @Autowired
    private BmsFollowMapper bmsFollowMapper;
    @Override
    public UmsUser executeRegister(RegisterDTO dto) {

        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, dto.getName()).or().eq(UmsUser::getEmail, dto.getEmail());
        UmsUser umsUser=null;
        umsUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(umsUser)) {
            log.warn("账号或邮箱已存在！");
            umsUser=null;
            return umsUser;
        }
        UmsUser addUser = UmsUser.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password( MD5Utils.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .build();
        baseMapper.insert(addUser);

        return addUser;
    }

    @Override
    public UmsUser getUserById(String id) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getId,id));
    }

    @Override
    public UmsUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUsername, username));
    }
    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            UmsUser user = getUserByUsername(dto.getName());
            String encodePwd = MD5Utils.getPwd(dto.getPass());
            if(!encodePwd.equals(user.getPassword()))
            {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getName());
        }
        return token;
    }

    @Override
    public Page<UmsUser> searchKey(String key, Page<UmsUser> page) {
        Page<UmsUser> ipage = this.baseMapper.searchkey(page, key);
        return ipage;
    }

}
