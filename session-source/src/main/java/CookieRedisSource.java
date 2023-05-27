import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CookieRedisSource implements CookieSource {
    @ConfigProperty(name = "authentication.session.expiration")
    Long sessionExpiration;
    private final ReactiveValueCommands<String, String> valueCommands;
    private final ReactiveKeyCommands<String> keyCommands;

    public CookieRedisSource(ReactiveRedisDataSource reactive) {
        this.valueCommands = reactive.value(String.class, String.class);
        this.keyCommands = reactive.key();
    }

    @Override
    public Uni<String> createSession(String key, String value) {
        valueCommands.setex(key, sessionExpiration, value)
                .subscribeAsCompletionStage().thenApply(status->"effer");

        return Uni.createFrom().item(key);
    }

    @Override
    public void removeSession(String key) {
        this.keyCommands.del(key).replaceWithVoid();
    }

    @Override
    public Uni<String> getValue(String key) {
        return valueCommands.get(key);
    }

    @Override
    public Uni<Boolean> validateSession(String key) {
        return keyCommands.exists(key) .flatMap((exists) -> {
            if (!exists) { // ключ не найден, сессия недействительна
                return Uni.createFrom().item(false);
            }
            // ключ найден, проверяем значение ключа
            return valueCommands.get(key).map((value) -> {
                String[] val = value.split(":");
                return val[0].equals(key);
            });
        });
    }

    @Override
    public Uni<String> getUsernameFromSession(String key) {
        return keyCommands.exists(key) .flatMap((exists) -> {
            if (!exists) { // ключ не найден, сессия недействительна
                return Uni.createFrom().nullItem();
            }
            // ключ найден, проверяем значение ключа
            return valueCommands.get(key).map((value) -> {
                String[] val = value.split(":");
                return val[1];
            });
        });
    }
}
