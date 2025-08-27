package vn.vdbc.core.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGrantRequest {
    private String userId;
    private Boolean isGranted;
}
