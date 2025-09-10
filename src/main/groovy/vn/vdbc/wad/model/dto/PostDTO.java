package vn.vdbc.wad.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private String thumbnail;
    private String webSource;
    private Integer priority;
    private String src;
    private String status;
    private String projectName;
    private String categoryName;
    private String typePost;
    private String shortContent;

    private List<String> categories;
    private List<String> tags;        
    private List<String> tagNews;
}

