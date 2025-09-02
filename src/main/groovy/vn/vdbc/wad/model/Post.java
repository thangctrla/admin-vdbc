    package vn.vdbc.wad.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

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

        @Column(name = "type_post")
        private String typePost;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "post_category",

                joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
        )
        private List<Category> categories;

        @Column(columnDefinition = "text",name = "short_content")
        private String shortContent;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Comment> comments;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "post_tag",

                joinColumns = @JoinColumn(name = "post_id",
                        referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id",
                        referencedColumnName = "id"))
        private List<Tag> tags;

        @ElementCollection
        @CollectionTable(
                name = "post_tag_news",
                joinColumns = @JoinColumn(name = "post_id")
        )
        @Column(name = "tag_news")
        private List<String> tagNews = new ArrayList<>();

    }
