package com.example.community.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@TableName("ums_user")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UmsUser implements Serializable {
    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String alias;

    @JsonIgnore()
    @TableField("password")
    private String password;

    @Builder.Default
    @TableField("avatar")
    private String avatar = "@/assets/image/default.jpeg";

    private String email;

    private String mobile;

    @Builder.Default
    @TableField("job")
    private String job = "未知";

    @Builder.Default
    @TableField("score")
    private Integer score = 0;

    @Builder.Default
    @TableField("topiccount")
    private Integer topiccount=0;


    @Builder.Default
    @TableField("followercount")
    private Integer followercount = 0;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
