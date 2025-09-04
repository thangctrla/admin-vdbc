package vn.vdbc.wad.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.stereotype.Repository
import vn.vdbc.wad.configuration.PostWithCategories
import vn.vdbc.wad.model.Post

@Repository
@RepositoryRestResource(path = "wad-post")
interface PostRepo extends JpaRepository<Post, Long> {
}
