package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItem {
    private String id;
    private String path;
    private String icon;
    private String parentId;
    private Integer orderIndex;
    private Boolean isVisible;
}
