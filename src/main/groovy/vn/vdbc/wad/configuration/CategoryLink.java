package vn.vdbc.wad.configuration;

import org.springframework.data.rest.core.config.Projection;
import vn.vdbc.wad.model.Category;

@Projection(name = "categoryLink", types = { Category.class })
public interface CategoryLink {
    Long getId();
    default String getHref() {
        return "/wad-category/" + getId();
    }
}


