package vn.vdbc.wad.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import vn.vdbc.wad.model.Category

@Repository
@RepositoryRestResource(path = "wad-category")
interface CategoryRepo extends JpaRepository<Category, String> {
}
