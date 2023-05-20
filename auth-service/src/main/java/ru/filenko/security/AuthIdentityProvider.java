package ru.filenko.security;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AuthIdentityProvider implements IdentityProvider<UsernamePasswordAuthenticationRequest> {
    private Map<String, String> users = new HashMap<>();

    public AuthIdentityProvider() {
        this.users.put("admin", "admin");
        this.users.put("user", "user");
    }

    @Override
    public Class<UsernamePasswordAuthenticationRequest> getRequestType() {
        return UsernamePasswordAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(UsernamePasswordAuthenticationRequest request,
                                              AuthenticationRequestContext context) {
        String login = request.getUsername();
        String password = new String(request.getPassword().getPassword());

        if(isValidUser(login, password)) {
            SecurityIdentity identity = createSecurityIdentity(login);
            return Uni.createFrom().item(identity);
        }

        return Uni.createFrom().failure(new AuthenticationFailedException("Your session not found"));
    }

    private SecurityIdentity createSecurityIdentity(String login) {
        return QuarkusSecurityIdentity.builder()
                .setPrincipal(()->login)
                .addRole("user")
                .build();
    }

    private boolean isValidUser(String login, String password) {
        return login.equals("admin") && password.equals("admin");
    }
}
