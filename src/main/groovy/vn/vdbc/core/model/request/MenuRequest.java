package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuRequest {
    private String path;
    private String icon;
    private String parentId;
    private Integer orderIndex;
    private Boolean isVisible;
    private String name;
}
