package vn.vdbc.core.controller

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import vn.vdbc.core.model.response.BaseResponse

@RequiredArgsConstructor
class BaseController {
    def <T> ResponseEntity<BaseResponse<T>> ok(T result, Map<String, Object> extra, String message) {
        BaseResponse<T> obj = new BaseResponse<>();
        obj.setResult(result);
        obj.setExtra(extra);
        obj.setCode(0);
        obj.setMessage(message);
        return ResponseEntity.ok(obj);
    }

    def <T> ResponseEntity<BaseResponse<T>> ok(T result, Map<String, Object> extra, String message, Integer code) {
        BaseResponse<T> obj = new BaseResponse<>();
        obj.setResult(result);
        obj.setExtra(extra);
        obj.setCode(code);
        obj.setMessage(message);
        return ResponseEntity.ok(obj);
    }

    def <T> ResponseEntity<BaseResponse<T>> ok(T result, Map<String, Object> extra) {
        return ok(result, extra, "Thành công");
    }

    def <T> ResponseEntity<BaseResponse<T>> ok() {
        return ok(null, null, "Thành công");
    }

    def <T> ResponseEntity<BaseResponse<T>> ok(T result) {
        return ok(result, null, "Thành công");
    }

    def <T> ResponseEntity<BaseResponse<T>> ok(T result, String message) {
        return ok(result, null, message);
    }

    def <T> ResponseEntity<BaseResponse<T>> error(T result, Map<String, Object> extra, String message) {
        BaseResponse<T> obj = new BaseResponse<>();
        obj.setResult(result);
        obj.setExtra(extra);
        obj.setCode(1);
        obj.setMessage(message);
        return ResponseEntity.ok(obj);
    }

    def <T> ResponseEntity<BaseResponse<T>> error(T result, Map<String, Object> extra, String message, Integer code) {
        BaseResponse<T> obj = new BaseResponse<>();
        obj.setResult(result);
        obj.setExtra(extra);
        obj.setCode(code);
        obj.setMessage(message);
        return ResponseEntity.ok(obj);
    }

    def <T> ResponseEntity<BaseResponse<T>> error(T result, Map<String, Object> extra) {
        return error(result, extra, "Thất bại");
    }

    def <T> ResponseEntity<BaseResponse<T>> error() {
        return error(null, null, "Thất bại");
    }

    def <T> ResponseEntity<BaseResponse<T>> error(T result) {
        return error(result, null, "Thất bại");
    }

    def <T> ResponseEntity<BaseResponse<T>> error(T result, String message) {
        return error(result, null, message);
    }
}
