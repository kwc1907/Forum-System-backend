package com.example.community.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment1VO implements Serializable {
    private static final long serialVersionUID = -261082150965211545L;
    private String id;

    private String content;

    private String topicname;

    private String username;

    private Date createTime;
}
