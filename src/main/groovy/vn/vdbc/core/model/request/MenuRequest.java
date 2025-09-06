package vn.vdbc.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String path;
    private String icon;
    private String parentId;
    private Integer orderIndex;
    private Boolean isVisible;
    private String name;
}
