package vn.vdbc.wad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "type_post")
    private String typePost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_category",

            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> categories;

    @Column(columnDefinition = "text", name = "short_content")
    private String shortContent;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Comment> comments;


    @ManyToMany(fetch = FetchType.EAGER)
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


    @Column(name = "created_at")
    private Date createdAt = new Date();
    @Column(name = "updated_at")
    private Date updatedAt = new Date();


    private String country;


//    @Transient
//    @JsonProperty("categories")
//    public List<String> getCategoryLinks() {
//        if (lsCategories == null) {
//            return new ArrayList<>();
//        }
//        return lsCategories.stream()
//                .map(c -> "/wad-category/" + c.getId())
//                .toList();
//    }

}
