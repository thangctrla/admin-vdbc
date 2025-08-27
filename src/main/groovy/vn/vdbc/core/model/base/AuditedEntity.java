package vn.vdbc.core.model.base;

import java.util.Date;

public interface AuditedEntity extends OwnedEntity {
    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);
}
