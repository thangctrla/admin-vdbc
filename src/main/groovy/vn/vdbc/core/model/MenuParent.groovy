package vn.vdbc.core.model

import jakarta.persistence.*

@Entity
@Table(name = "menu_parent")
class MenuParent {

    @Id
    String id

    String name

    String icon
    @Column(name = "order_index")
    Integer orderIndex

    @Column(name = "is_visible")
    Boolean isVisible

    String status
}

