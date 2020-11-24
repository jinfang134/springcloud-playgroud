package playground.annotation;

public class AuthException extends RuntimeException {
    private String permission;

    public AuthException(String permission) {
        super();
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
