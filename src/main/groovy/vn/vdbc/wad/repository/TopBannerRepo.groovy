package vn.vdbc.wad.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import vn.vdbc.wad.model.Post
import vn.vdbc.wad.model.TopBanner

@Repository
@RepositoryRestResource(path = "wad-top-banner")
interface TopBannerRepo extends JpaRepository<TopBanner, Long> {
}
