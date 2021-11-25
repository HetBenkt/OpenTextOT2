package nl.bos.ot2.authentication;

public class AuthenticationException extends Throwable {
    private final String message;

    public AuthenticationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
