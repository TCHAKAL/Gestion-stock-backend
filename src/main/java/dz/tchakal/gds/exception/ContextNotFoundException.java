package dz.tchakal.gds.exception;


import lombok.Getter;

public class ContextNotFoundException extends RuntimeException{
    @Getter
    private ErrorCode errorCode;

    public ContextNotFoundException(String message){
        super(message);
    }

    public ContextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ContextNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
