package com.example.manstore.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String secret_key = "123";

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        System.out.println("Header : "+authorizationHeader);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try {
//                String token = authorizationHeader.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(token);
//                String username = decodedJWT.getSubject();
//                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                System.out.println(username);
//                for (String role: roles
//                ) {
//                    System.out.println(role);
//                }
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request,response);
//            } catch (Exception exception) {
//                response.setHeader("error",exception.getMessage());
//                response.setStatus(FORBIDDEN.value());
//                Map<String,String> error = new HashMap<>();
//                error.put("error message",exception.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(),error);
//            }
//        } else {
//            filterChain.doFilter(request,response);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Bỏ qua xác thực cho các endpoint được cấu hình là "permitAll"
        if (path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v2/auth") ||
                path.startsWith("/index/home") ||
                path.startsWith("/admin") ||
                path.startsWith("/assets") ||
                path.startsWith("/client/assets") ||
                path.startsWith("/templates") ||
                path.startsWith("/api/register") ||
                path.startsWith("/shop-cart") ||
                path.startsWith("/order") ||
                path.startsWith("/api/client/cart_detail/findAll")||
                path.startsWith("api/client/order/invoice/buy-now/**")||
                path.startsWith(" /api/client/cart_detail/add-to-invoice/**")
        )
        {
            filterChain.doFilter(request, response);
            return;
        }

        // Tiến hành xác thực JWT cho các endpoint còn lại
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}