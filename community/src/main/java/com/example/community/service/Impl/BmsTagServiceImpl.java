package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsTagMapper;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.entity.BmsTip;
import com.example.community.model.vo.TagVO;
import com.example.community.service.BmsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BmsTagServiceImpl extends ServiceImpl<BmsTagMapper, BmsTag> implements BmsTagService {

    @Autowired
    private com.example.community.service.BmsTopicTagService bmsTopicTagService;

    @Autowired
    private com.example.community.service.BmsPostService bmsPostService;


    @Override
    public List<BmsTag> insertTags(List<String> tagNames) {
        List<BmsTag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            BmsTag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<BmsTag>().eq(BmsTag::getName, tagName));
            if (tag == null) {
                tag = BmsTag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            } else {
                tag.setTopicCount(tag.getTopicCount() + 1);
                this.baseMapper.updateById(tag);
            }
            tagList.add(tag);
        }
        return tagList;
    }
    @Override
    public BmsTag selectTagById(String id)
    {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void removeTag()
    {
        this.baseMapper.delete(new LambdaQueryWrapper<BmsTag>().eq(BmsTag::getTopicCount,0));
    }

    @Override
    public List<TagVO> getTag() {
        return this.baseMapper.getTag();
    }


    @Override
    public Page<BmsPost> selectTopicsByTagId(Page<BmsPost> topicPage, String id) {

        Set<String> ids = bmsTopicTagService.selectTopicIdsByTagId(id);
        LambdaQueryWrapper<BmsPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BmsPost::getId, ids);

        return bmsPostService.page(topicPage, wrapper);
    }

}
