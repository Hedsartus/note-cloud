import io.smallrye.mutiny.Uni;

public interface CookieSource {
    Uni<String> createSession(String key, String value);
    void removeSession(String key);
    Uni<String> getValue(String key);
    Uni<Boolean> validateSession(String key);
    Uni<String> getUsernameFromSession(String key);
}
