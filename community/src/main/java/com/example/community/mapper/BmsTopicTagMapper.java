package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.model.entity.BmsTopicTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface BmsTopicTagMapper extends BaseMapper<BmsTopicTag> {

    Set<String> getTopicIdsByTagId(@Param("id") String id);
}
