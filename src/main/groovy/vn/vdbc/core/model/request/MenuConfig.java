package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuConfig {
    public String id;
    public String menuItemId;
    public String configKey;
    public String configValue;
}

