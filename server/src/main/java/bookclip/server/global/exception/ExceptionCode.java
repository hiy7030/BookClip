package bookclip.server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    MEMBER_EXISTS(HttpStatus.CONFLICT.value(), "Member Exists"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found");
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
