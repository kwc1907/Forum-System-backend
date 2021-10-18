package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.model.entity.BmsTag;
import com.example.community.model.vo.TagVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BmsTagMapper extends BaseMapper<BmsTag> {
        List<TagVO> getTag();
}
