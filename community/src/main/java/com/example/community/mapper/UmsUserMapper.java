package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.model.entity.UmsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UmsUserMapper extends BaseMapper<UmsUser> {
    Page<UmsUser> searchkey(@Param("page") Page<UmsUser> page, @Param("key") String key);

}
