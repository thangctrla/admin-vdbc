package vn.vdbc.wad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "posts")
public class Post {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "thumbnail", columnDefinition = "text")
    private String thumbnail;

    @Column(name = "web_source")
    private String webSource;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "src")
    private String src;

    @Column(name = "status")
    private String status;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "category_name")
    private String categoryName;
}
