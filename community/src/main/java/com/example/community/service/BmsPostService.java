package com.example.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.dto.CreateTopicDTO;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.PostVO;

import java.util.List;
import java.util.Map;


public interface BmsPostService extends IService<BmsPost> {


    Page<PostVO> getList(Page<PostVO> page, String tab);

    BmsPost create(CreateTopicDTO dto, UmsUser principal);
    Map<String, Object> viewTopic(String id);
    List<BmsPost> getRecommend(String id);
    Page<PostVO> searchByKey(String keyword, Page<PostVO> page);
}
