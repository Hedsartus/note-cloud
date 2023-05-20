package ru.filenko.security;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.security.credential.PasswordCredential;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;

@Provider
@Priority(1)
public class BasicAuthenticationMechanism implements HttpAuthenticationMechanism {
    @Inject
    AuthIdentityProvider identityProvider;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String login = "admin";//context.request().getFormAttribute("login");
        String password = "admin";//context.request().getFormAttribute("password");

        var request = new UsernamePasswordAuthenticationRequest(
                login, new PasswordCredential(password.toCharArray()));

        return identityProvider.authenticate(request, null);
    }



    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        ChallengeData challengeData = new ChallengeData(
                HttpResponseStatus.UNAUTHORIZED.code(),
                HttpHeaders.WWW_AUTHENTICATE,
                "Authentication Required");
        return Uni.createFrom().item(challengeData);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Set.of(UsernamePasswordAuthenticationRequest.class);
    }
}
