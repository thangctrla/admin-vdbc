package vn.vdbc.wad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "top_banner")
public class TopBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(insertable = false, updatable = false)
    private String image;
    @Column(name = "image")
    private String thumbnail;
    private String status;
}
