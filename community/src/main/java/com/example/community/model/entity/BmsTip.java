package com.example.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@TableName("bms_tip")
@AllArgsConstructor
@Accessors(chain = true)
public class BmsTip implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("`content`")
    private String content;

    @TableField("author")
    private String author;

    @Builder.Default
    @TableField("type")
    private boolean type=true;

}
