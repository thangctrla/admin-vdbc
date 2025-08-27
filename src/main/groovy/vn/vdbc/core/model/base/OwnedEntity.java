package vn.vdbc.core.model.base;


import vn.vdbc.core.model.UserData;

public interface OwnedEntity {
    UserData getCreatedBy();

    void setCreatedBy(UserData user);
}
