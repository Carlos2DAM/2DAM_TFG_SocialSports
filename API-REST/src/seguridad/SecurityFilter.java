package seguridad;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.io.IOException;
import seguridad.JwtProvider;
import seguridad.Secured;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Secured
public class SecurityFilter implements ContainerRequestFilter {
	
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Coge la cabecera "Authorization" 
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Valida la cabecera
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }
        
    	String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
    	
    	JwtProvider jwtProvider = new JwtProvider();
        if(!jwtProvider.validateToken(token)) {
        	abortWithUnauthorized(requestContext);
        	return;
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("El usuario no puede acceder a ese recurso.").build());
    }
}

