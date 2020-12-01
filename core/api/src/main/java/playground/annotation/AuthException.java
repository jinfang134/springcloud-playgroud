package playground.annotation;

public class AuthException extends RuntimeException {
    private String permission;

    public AuthException(String permission) {
        super("No permission: " + permission);
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
