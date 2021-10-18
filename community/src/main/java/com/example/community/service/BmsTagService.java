package com.example.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.vo.TagVO;

import java.util.List;


public interface BmsTagService extends IService<BmsTag> {

    List<BmsTag> insertTags(List<String> tags);
    BmsTag selectTagById(String id);
    Page<BmsPost> selectTopicsByTagId(Page<BmsPost> topicPage, String id);
    void removeTag();

    List<TagVO> getTag();
}
