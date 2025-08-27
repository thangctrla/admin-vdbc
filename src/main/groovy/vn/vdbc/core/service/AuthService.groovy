package vn.vdbc.core.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import vn.vdbc.core.model.request.LoginRequest
import vn.vdbc.core.security.JwtProvider
import vn.vdbc.core.utils.Utils

@Service
class AuthService {

    @Autowired
    JwtProvider jwtProvider

    @Autowired
    DatabaseService dbs

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    Utils utils


    def login(LoginRequest user) {
        def rows = dbs.rows("xcore", "select id, email, password from user_data where email = ? and status = 'Active'", user.username)
        if (rows.isEmpty()) {
            throw new RuntimeException("Thông tin đăng nhập không hợp lệ")
        }

        def result = rows[0]
        if (user.getPassword() && !bCryptPasswordEncoder.matches(user.getPassword(), result.password.toString())) {
            throw new RuntimeException("Thông tin đăng nhập không hợp lệ")
        }

        def roles = dbs.rows("xcore",
                "SELECT r.name FROM roles r LEFT JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?", [result.id])
                .collect { it.name }

        result.password = ""
        result.token = jwtProvider.generateToken(result.id as String, roles as List<String>)
        result.roles = roles
        return result
    }

    def me(HttpServletRequest httpServletRequest) {
        def auth = SecurityContextHolder.getContext().getAuthentication()
        def userId = utils.getUserId(httpServletRequest)

        if (!userId) {
            throw new RuntimeException("Unauthorized")
        }

        def user = dbs.rows("xcore", """
            SELECT id, avatar, email, full_name as "fullName", phone_number as "phoneNumber", status, title, organizationId, accountType
            FROM user_data WHERE id = ?
        """, userId)[0]

        if (user.organizationId) {
            user.organization = dbs.rows("xcore", "select * from organizations where id = ?", user.organizationId)[0]
        }

        return user
    }


}
