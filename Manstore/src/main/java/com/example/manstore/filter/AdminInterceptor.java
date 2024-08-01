package com.example.manstore.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.impl.ClaimsHolder;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;


public class AdminInterceptor implements HandlerInterceptor {

    private static final String secret_key = "123";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie[] cookies = request.getCookies();
        Cookie tokenCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElse(null);
        String token = tokenCookie != null ? tokenCookie.getValue() : null;
        System.out.println("Token preHandler : " + token);
        if (request.getRequestURI().startsWith("/admin/")) {
            System.out.println(token);
            if (token == null) {
                String loginPageUrl = "/login";
                String redirectUrl = UriComponentsBuilder.fromUriString(request.getContextPath() + loginPageUrl + "?error=Unauthorized")
                        .build().toUriString();
                response.sendRedirect(redirectUrl);
                return false;
            } else {
                Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                System.out.println("After Decode Token : " + username);
                for (String role : roles
                ) {
                    System.out.println("After Decode Token : " + role);
                    if (!role.equalsIgnoreCase("ADMIN")) {
                        String loginPageUrl = "/login";
                        String redirectUrl = UriComponentsBuilder.fromUriString(request.getContextPath() + loginPageUrl + "?error=Unauthorized")
                                .build().toUriString();
                        response.sendRedirect(redirectUrl);
                        return false;
                    }
                }
                Long expInSeconds = decodedJWT.getClaim("exp").asLong();
                Instant instant = Instant.ofEpochSecond(expInSeconds);
                LocalDateTime expDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                System.out.println("Expired time : " + expDateTime);
                if (!LocalDateTime.now().isBefore(expDateTime)) {
                    String loginPageUrl = "/login";
                    String redirectUrl = UriComponentsBuilder.fromUriString(request.getContextPath() + loginPageUrl + "?error=Expired")
                            .build().toUriString();
                    response.sendRedirect(redirectUrl);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Xử lý sau khi request đã được xử lý bởi controller nhưng trước khi trả về view
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // Xử lý sau khi response đã được gửi đi
    }
}