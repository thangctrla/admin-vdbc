package vn.vdbc.wad.configuration;

import org.springframework.data.rest.core.config.Projection;
import vn.vdbc.wad.model.Category;
import vn.vdbc.wad.model.Comment;
import vn.vdbc.wad.model.Post;
import vn.vdbc.wad.model.Tag;

import java.util.List;

@Projection(name = "postWithCategories", types = { Post.class })
public interface PostWithCategories {
    Long getId();
    String getTitle();
    String getBody();
    String getThumbnail();
    String getWebSource();
    Integer getPriority();
    String getSrc();
    String getStatus();
    String getProjectName();
    String getCategoryName();
    String getTypePost();
    String getShortContent();
    List<CategoryLink> getCategories();
    List<Comment> getComments();
    List<Tag> getTags();
    List<String> getTagNews();
}

