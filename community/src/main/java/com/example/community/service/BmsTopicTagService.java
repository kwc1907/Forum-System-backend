package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.entity.BmsTopicTag;

import java.util.List;
import java.util.Set;

public interface BmsTopicTagService extends IService<BmsTopicTag> {

    List<BmsTopicTag> selectByTopicId(String topicId);
    void createTopicTag(String id, List<BmsTag> tags);
    Set<String> selectTopicIdsByTagId(String id);

}
