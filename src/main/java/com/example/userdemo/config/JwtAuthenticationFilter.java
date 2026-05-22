package com.example.userdemo.config;

import com.example.userdemo.util.JwtUtil;
import com.example.userdemo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(header);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(prefix)) {
            String token = authHeader.substring(prefix.length());

            Boolean isBlacklisted = redisUtil.hasKey("token:blacklist:" + token);
            if (Boolean.TRUE.equals(isBlacklisted)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                List<String> permissions = (List<String>) redisUtil.get("permission:" + username);

                if (permissions == null) {
                    permissions = Arrays.asList("ROLE_USER");
                }

                List<SimpleGrantedAuthority> authorities = permissions.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
