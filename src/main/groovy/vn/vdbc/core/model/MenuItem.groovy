package vn.vdbc.core.model

import jakarta.persistence.*

@Entity
@Table(name = "menu_item")
class MenuItem {

    @Id
    String id

    String path

    String icon
    @Column(name = "parent_id")
    String parentId

    @Column(name = "order_index")
    Integer orderIndex

    @Column(name = "is_visible")
    Boolean isVisible

    String name

    String status
}

