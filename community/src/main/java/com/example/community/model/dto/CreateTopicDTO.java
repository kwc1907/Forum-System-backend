package com.example.community.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateTopicDTO implements Serializable {
    private static final long serialVersionUID = -5957433707110390852L;

    private String title;
    private String content;
    private List<String> tags;

}
