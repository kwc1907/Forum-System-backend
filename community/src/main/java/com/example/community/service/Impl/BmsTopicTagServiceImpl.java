package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsTopicTagMapper;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.entity.BmsTopicTag;
import com.example.community.service.BmsTopicTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional(rollbackFor = Exception.class)
public class BmsTopicTagServiceImpl extends ServiceImpl<BmsTopicTagMapper, BmsTopicTag> implements BmsTopicTagService {

    @Override
    public List<BmsTopicTag> selectByTopicId(String topicId) {
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }
    @Override
    public void createTopicTag(String id, List<BmsTag> tags) {

        tags.forEach(tag -> {
            BmsTopicTag topicTag = new BmsTopicTag();
            topicTag.setTopicId(id);
            topicTag.setTagId(tag.getId());
            this.baseMapper.insert(topicTag);
        });
    }
    @Override
    public Set<String> selectTopicIdsByTagId(String id) {
        return this.baseMapper.getTopicIdsByTagId(id);
    }

}
