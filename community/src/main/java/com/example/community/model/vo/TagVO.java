package com.example.community.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVO implements Serializable {
    private static final long serialVersionUID = -261082150965211545L;
    private String name;
    private String value;

}
