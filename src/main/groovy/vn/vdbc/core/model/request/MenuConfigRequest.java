package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuConfigRequest {
    private String id;
    private String menuItemId;
    private String configKey;
    private String configValue;
}
