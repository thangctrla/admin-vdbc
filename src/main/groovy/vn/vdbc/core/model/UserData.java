package vn.vdbc.core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.vdbc.core.model.base.BaseEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_data")
@Data
public class UserData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String avatar;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    private String email;
    @Column(name = "full_name")
    private String fullName;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String status;

    private String title;

    @Column(name = "updated_at")
    private Date updatedAt;

}