package vn.vdbc.wad.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import vn.vdbc.wad.model.TopBanner
import vn.vdbc.wad.model.Video

@Repository
@RepositoryRestResource(path = "wad-video")
interface VideoRepo extends JpaRepository<Video, Long> {
}
