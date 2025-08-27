package vn.vdbc.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vdbc.core.model.request.LoginRequest
import vn.vdbc.core.model.response.BaseResponse
import vn.vdbc.core.service.AuthService

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private AuthService authService

//    @PostMapping("/signup")
//    ResponseEntity<BaseResponse> signup(@RequestBody SignupRequest request) {
//        try {
//            def result = authService.signUp(request)
//            return ResponseEntity.ok(new BaseResponse(result))
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
//        }
//    }

    @PostMapping("/signin")
    ResponseEntity<BaseResponse> signin(@RequestBody LoginRequest request) {
        try {
            def result = authService.login(request)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }
}