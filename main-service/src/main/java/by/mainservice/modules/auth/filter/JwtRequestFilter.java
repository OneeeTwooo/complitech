package by.mainservice.modules.auth.filter;

import by.mainservice.common.exception.ApplicationRuntimeException;
import by.mainservice.modules.auth.service.JwtService;
import by.mainservice.modules.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private UserService userService;
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain chain
    ) throws ServletException, IOException {
        final var requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username;
        String jwtToken;

        if (requestTokenHeader == null || !requestTokenHeader.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        jwtToken = requestTokenHeader.substring(7);
        try {
            username = jwtService.extractUsername(jwtToken);
        } catch (final IllegalArgumentException e) {
            throw new ApplicationRuntimeException("Unable to get JWT Token");
        } catch (final ExpiredJwtException e) {
            throw new ApplicationRuntimeException("JWT Token has expired");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final var userDetails = userService.findUserByLogin(username);

            if (jwtService.validateToken(jwtToken)) {
                final var authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
