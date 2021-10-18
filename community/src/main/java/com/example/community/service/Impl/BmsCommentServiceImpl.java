package com.example.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.mapper.BmsCommentMapper;
import com.example.community.model.dto.CommentDTO;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.Comment1VO;
import com.example.community.model.vo.CommentVO;
import com.example.community.model.vo.PostVO;
import com.example.community.service.BmsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class BmsCommentServiceImpl extends ServiceImpl<BmsCommentMapper, BmsComment> implements BmsCommentService {
    @Override
    public List<CommentVO> getCommentsByTopicID(String topicid) {
        List<CommentVO> lstBmsComment = new ArrayList<CommentVO>();
        try {
            lstBmsComment = this.baseMapper.getCommentsByTopicID(topicid);
        } catch (Exception e) {
            log.info("lstBmsComment失败");
        }
        return lstBmsComment;
    }

    @Override
    public BmsComment create(CommentDTO dto, UmsUser user) {
        BmsComment comment = BmsComment.builder()
                .userId(user.getId())
                .content(dto.getContent())
                .topicId(dto.getTopic_id())
                .createTime(new Date())
                .build();
        this.baseMapper.insert(comment);
        return comment;
    }

    @Override
    public BmsComment selectById(String id)
    {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<BmsComment>().eq(BmsComment::getId, id));
    }

    @Override
    public Page<Comment1VO> getList(Page<Comment1VO> p) {
        Page<Comment1VO> iPage = this.baseMapper.getComments(p);
        return iPage;
    }
}
