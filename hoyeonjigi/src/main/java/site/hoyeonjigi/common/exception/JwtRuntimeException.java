package site.hoyeonjigi.common.exception;

public class JwtRuntimeException extends RuntimeException{
    public JwtRuntimeException(String message) {
        super(message);
    }
}
