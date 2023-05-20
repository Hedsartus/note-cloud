package ru.filenko.security;

import com.oracle.svm.core.annotate.Inject;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.security.Principal;

@PreMatching
public class SecurityFilter implements ContainerRequestFilter {
    //@Inject AuthenticationContextImpl authenticationContext;
    @Context SecurityContext securityContext;
    private static final String HEADER_LOGIN = "HD-login";
    private static final String HEADER_PASSWORD = "HD-password";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String login = requestContext.getHeaders().getFirst(HEADER_LOGIN);
        String password = requestContext.getHeaders().getFirst(HEADER_PASSWORD);

        if(login == null || password == null) {
            throw new AuthenticationFailedException("Нет пользователя с таким логин/паролем!");
        }

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }
}
