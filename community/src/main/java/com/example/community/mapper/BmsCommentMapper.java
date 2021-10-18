package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.model.entity.BmsComment;
import com.example.community.model.vo.Comment1VO;
import com.example.community.model.vo.CommentVO;
import com.example.community.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BmsCommentMapper extends BaseMapper<BmsComment> {
    List<CommentVO> getCommentsByTopicID(@Param("topicid") String topicid);
    Page<Comment1VO> getComments(@Param("page") Page<Comment1VO> page);
}
