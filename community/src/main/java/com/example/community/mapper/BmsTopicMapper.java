package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.model.entity.BmsPost;
import com.example.community.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BmsTopicMapper extends BaseMapper<BmsPost> {

    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab);


    List<BmsPost> selectRecommend(@Param("id") String id);

    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);
}
