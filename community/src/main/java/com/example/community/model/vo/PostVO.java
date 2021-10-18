package com.example.community.model.vo;

import com.example.community.model.entity.BmsTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVO implements Serializable {
    private static final long serialVersionUID = -261082150965211545L;


    private String id;

    private String userId;

    private String avatar;

    private String alias;

    private String username;

    private String title;

    private Integer comments;

    private List<BmsTag> tags;

    private Integer view;

    private Date createTime;

    private Date modifyTime;
}
