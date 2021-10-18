package com.example.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.dto.CommentDTO;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.entity.UmsUser;
import com.example.community.model.vo.Comment1VO;
import com.example.community.model.vo.CommentVO;

import java.util.List;


public interface BmsCommentService extends IService<BmsComment> {

    List<CommentVO> getCommentsByTopicID(String topicid);

    BmsComment create(CommentDTO dto, UmsUser principal);
    public BmsComment selectById(String id);

    Page<Comment1VO> getList(Page<Comment1VO> p);
}
