package vn.vdbc.core.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vn.vdbc.core.security.JwtProvider
import vn.vdbc.core.service.DatabaseService

import java.sql.Timestamp

@Component
class Utils {
    @Autowired
    DatabaseService dbs
    @Autowired
    JwtProvider jwtProvider

    static def timeNow() {
        return new Timestamp(new Date().getTime())
    }

    def getRoles(String userId) {
        return dbs.rows("xcore", """ SELECT r.name FROM roles r
        LEFT JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ? """, [userId]).collect { it.name } as List<String>
    }

     String getUserId(HttpServletRequest httpServletRequest) {
        return jwtProvider.getUserId(httpServletRequest)
    }

}
