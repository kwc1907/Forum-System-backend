package com.example.community.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@Accessors(chain = true)
@TableName("bms_billboard")
@NoArgsConstructor
@AllArgsConstructor
public class BmsBillboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Builder.Default
    @TableField("`show`")
    private boolean show = true;

}
