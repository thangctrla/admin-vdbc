package vn.vdbc.core.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vdbc.core.model.response.BaseResponse
import vn.vdbc.core.service.AuthService


@RestController
@RequestMapping("/user")
class AccountController {

    @Autowired
    private AuthService authService

    @GetMapping("/me")
    ResponseEntity<BaseResponse> me(HttpServletRequest httpServletRequest) {
        try {
            def user = authService.me(httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(user))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(e.message))
        }
    }


}