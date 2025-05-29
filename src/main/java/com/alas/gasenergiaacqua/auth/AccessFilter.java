package com.alas.gasenergiaacqua.auth;

import com.alas.gasenergiaacqua.dto.ResponseMessage;
import com.alas.gasenergiaacqua.exception.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

@Component
@Order(1)
public class AccessFilter extends OncePerRequestFilter {
    private static final String AUTH_ENDPOINT = "/api/auth";
    public static final int API_LENGTH = "/api".length();
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public AccessFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    private boolean isAuthenticated(final String jwtToken, HttpServletResponse response) throws IOException {
        if (jwtTokenProvider.isTokenEmpty(jwtToken) || !jwtTokenProvider.isValid(jwtToken) || jwtTokenProvider.isTokenExpired(jwtToken)) {
            handleException(new AuthenticationException("User is not authenticated"), "User is not authenticated", HttpStatus.UNAUTHORIZED, response);
            return false;
        }
        return true;
    }

    private void handleException(Throwable ex, String message, HttpStatus status, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .timestamp(Instant.now())
                .message(message)
                .build();
        System.err.println("AccessFilterError: " + ex.getMessage());
        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * Is called by default each time a new request arrives.
     *
     * @param request            Request received from the user
     * @param response           Response returned to the user
     * @param filterChain Object provided by the servlet container to the developer giving a view into the invocation chain of a filtered request for a resource. Filters use the FilterChain to invoke the next filter in the chain.
     * @throws ServletException  Possible exception
     * @throws IOException       Possible exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        String method = request.getMethod();

        if (!url.contains("/api")) return; //if the url doesn't have '/api' in it, it will be ignored

        /*
         * Used by front-ent to make sure that backend is working (operation of pre-flight).
         * A preflight request is an automatic browser-initiated OPTIONS request that takes
         * occurs before certain cors-origin requests to ensure that backend/server is working,
         * so that the server accepts the upcoming request method, header and credentials.
         */
        if (method.equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        //won't ask for a token to log in or register
        if (method.equalsIgnoreCase("POST") && url.startsWith(AUTH_ENDPOINT) &&
                url.endsWith("/login") ||
                url.endsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = request.getHeader("Authorization");
            if (!url.startsWith(AUTH_ENDPOINT)) {
                if (Objects.isNull(token) || !isAuthenticated(token, response)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            handleException(e, "Token expired", HttpStatus.UNAUTHORIZED, response);
            return;
        } catch (SignatureException e) {
            handleException(e, "Invalid token signature", HttpStatus.UNAUTHORIZED, response);
            return;
        } catch (MalformedJwtException e) {
            handleException(e, "Malformed token", HttpStatus.BAD_REQUEST, response);
            return;
        } catch (IllegalArgumentException e) {
            handleException(e, "Invalid argument", HttpStatus.BAD_REQUEST, response);
            return;
        } catch (AuthenticationException e) {
            handleException(e, e.getMessage(), HttpStatus.UNAUTHORIZED, response);
            return;
        }

        final String token = request.getHeader("Authorization");
        final Integer userType = jwtTokenProvider.extractUserTypeFromClaims(token);
//        final UUID myId = jwtTokenProvider.extractIdFromClaims(token);

        switch (userType) {
            case 2: //admin
                System.out.println("ADMIN called method: " + method + " url: " + url);
                filterChain.doFilter(request, response);
                break;
            case 1: //normal user
                System.out.println("NORMAL called method: " + method + " url: " + url);
                switch (method) {
                    case "GET":
                        System.out.println("GET: " + url);
                        filterChain.doFilter(request, response);
                        break;
                    case "POST":
                        System.out.println("POST: " + url);
                        filterChain.doFilter(request, response);
                        break;
                    case "PUT":
                        System.out.println("PUT: " + url);
                        filterChain.doFilter(request, response);
                        break;
                    case "DELETE":
                        System.out.println("DELETE: " + url);
                        filterChain.doFilter(request, response);
                        break;
                    default:
                        handleException(
                                new AuthenticationException("Requested action cannot be performed with this userType: " + userType),
                                "Requested action cannot be performed with this userType: " + userType,
                                HttpStatus.FORBIDDEN,
                                response);
                        break;
                }
                filterChain.doFilter(request, response);
                break;
            default:
                handleException(
                        new AuthenticationException("User type is not valid"),
                        "User type is not valid",
                        HttpStatus.FORBIDDEN,
                        response);
        }
    }
}