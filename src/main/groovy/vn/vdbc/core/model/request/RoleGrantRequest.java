package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleGrantRequest {
    private Integer roleId;
    private Boolean isGranted;
}
