package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsTagMapper;
import com.example.community.mapper.BmsTopicMapper;
import com.example.community.mapper.UmsUserMapper;
import com.example.community.model.dto.CreateTopicDTO;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.entity.BmsTopicTag;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.PostVO;
import com.example.community.service.BmsPostService;
import com.example.community.service.BmsTagService;
import com.example.community.service.UmsUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BmsPostServiceImpl extends ServiceImpl<BmsTopicMapper, BmsPost> implements BmsPostService {
    @Resource
    private BmsTagMapper bmsTagMapper;
    @Resource
    private UmsUserMapper umsUserMapper;

    @Autowired
    @Lazy
    private BmsTagService bmsTagService;

    @Autowired
    private UmsUserService umsUserService;

    @Autowired
    private com.example.community.service.BmsTopicTagService bmsTopicTagService;
    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        setTopicTags(iPage);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BmsPost create(CreateTopicDTO dto, UmsUser user) {
        BmsPost topic1 = this.baseMapper.selectOne(new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getTitle, dto.getTitle()));
        if(topic1 !=null)
        {
         topic1 =null;
         return topic1;
        }
        SimpleDateFormat s=new SimpleDateFormat();
        s.applyPattern("yyyy-MM-dd");
        BmsPost topic = BmsPost.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .echarTime(s.format(new Date()))
                .build();
        this.baseMapper.insert(topic);

        user.setScore(user.getScore() + 1);
        user.setTopiccount(user.getTopiccount()+1);
        umsUserMapper.updateById(user);

        if (!ObjectUtils.isEmpty(dto.getTags())) {
            List<BmsTag> tags = bmsTagService.insertTags(dto.getTags());
            bmsTopicTagService.createTopicTag(topic.getId(), tags);
        }

        return topic;
    }

    @Override
    public Map<String, Object> viewTopic(String id) {
        Map<String, Object> map = new HashMap<>();
        BmsPost topic = this.baseMapper.selectById(id);
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);
        // 标签
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();
        for (BmsTopicTag articleTag : bmsTopicTagService.list(wrapper)) {
            set.add(articleTag.getTagId());
        }
        List<BmsTag> tags = bmsTagService.listByIds(set);
        map.put("tags", tags);

        // 作者
        UmsUser user = umsUserService.getUserById(topic.getUserId());
        map.put("user", user);
        return map;
    }

    @Override
    public List<BmsPost> getRecommend(String id) {
        return this.baseMapper.selectRecommend(id);
    }


    @Override
    public Page<PostVO> searchByKey(String keyword, Page<PostVO> page) {
        Page<PostVO> iPage = this.baseMapper.searchByKey(page, keyword);
        setTopicTags(iPage);
        return iPage;
    }

    private void setTopicTags(Page<PostVO> iPage) {
        iPage.getRecords().forEach(topic -> {
            List<BmsTopicTag> topicTags = bmsTopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(BmsTopicTag::getTagId).collect(Collectors.toList());
                List<BmsTag> tags = bmsTagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }
}
