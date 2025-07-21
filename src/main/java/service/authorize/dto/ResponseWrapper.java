package service.authorize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private T result;
    private Status status;

    public static <T> ResponseWrapper<T> success(T result) {
        return new ResponseWrapper<>(result, new Status(0, "Ok"));
    }

    public static <T> ResponseWrapper<T> error(T result) {
        return new ResponseWrapper<>(result, new Status(1, "Error"));
    }

    public static <T> ResponseWrapper<T> error(T result, String message, Integer code) {
        return new ResponseWrapper<>(result, new Status(code, message));
    }

    public static <T> ResponseWrapper<T> fatal(T result) {
        return new ResponseWrapper<>(result, new Status(2, "Fatal"));
    }

    public static <T> ResponseWrapper<T> unauthorized() {
        return new ResponseWrapper<>(null, new Status(401, HttpStatus.UNAUTHORIZED.getReasonPhrase()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private Integer code;
        private String message;
    }
}
