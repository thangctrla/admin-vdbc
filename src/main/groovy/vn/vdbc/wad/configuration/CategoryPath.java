package vn.vdbc.wad.configuration;

import org.springframework.data.rest.core.config.Projection;
import vn.vdbc.wad.model.Category;

@Projection(name = "categoryPath", types = { Category.class })
public interface CategoryPath {
    Long getId();

    default String getCategoryPath() {
        return "/wad-category/" + getId();
    }
}

