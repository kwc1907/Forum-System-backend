package com.example.community.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsFollowMapper;
import com.example.community.model.entity.BmsFollow;
import com.example.community.service.BmsFollowService;
import org.springframework.stereotype.Service;


@Service
public class BmsFollowServiceImpl extends ServiceImpl<BmsFollowMapper, BmsFollow> implements BmsFollowService {
}
