package vn.vdbc.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.stereotype.Repository
import vn.vdbc.core.model.UserData

@Repository
@RestResource(path = "UserDatas", rel = "UserData")
interface UserRepo extends JpaRepository<UserData, String> {
    Optional<UserData> findByEmail(String email)
}
