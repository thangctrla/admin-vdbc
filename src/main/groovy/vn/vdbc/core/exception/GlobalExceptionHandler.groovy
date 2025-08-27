package vn.vdbc.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import vn.vdbc.core.model.response.BaseResponse

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        BaseResponse obj = new BaseResponse<>();
        obj.setCode(1);
        obj.setMessage(ex.getReason());
        println(ex.getReason());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleException(Exception ex) {
        BaseResponse obj = new BaseResponse<>();
        obj.setCode(1);
        obj.setMessage("Có lỗi xả ra trong quá trình xử lý");
        println(ex.getMessage());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }
}