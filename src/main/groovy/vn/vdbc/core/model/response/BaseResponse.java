package vn.vdbc.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    @JsonProperty("code")
    protected Integer code = 0;
    @JsonProperty("message")
    protected String message = "";
    @JsonProperty("request_id")
    protected String request_id = "";
    @JsonProperty("result")
    private T result;
    @JsonProperty("extra")
    private Map<String, Object> extra;

    public BaseResponse(T result) {
        this.code = 0;
        this.result = result;
    }
}
