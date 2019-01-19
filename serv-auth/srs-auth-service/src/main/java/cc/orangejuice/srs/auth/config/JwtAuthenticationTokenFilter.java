package cc.orangejuice.srs.auth.config;

import cc.orangejuice.srs.auth.exception.JwtAuthenticationException;
import cc.orangejuice.srs.auth.util.Response;
import cc.orangejuice.srs.auth.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String authHeader = request.getHeader("Authorization");
                String tokenHead = "Bearer ";

                if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(tokenHead)) {
                    String token = authHeader.substring(tokenHead.length());
                    Claims claims = JwtHelper.parseJWT(token);
                    if (claims != null) {
                        String username = claims.get("username").toString();
                        String role = claims.get("role").toString();
                        String[] rolesArray = role.split(",");
                        Collection<? extends GrantedAuthority> roles = Stream.of(rolesArray)
                                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                        // 1、构建UserDetails
                        UserDetails userDetails = new User(username, "N/A", roles);
                        // 2、构建已经认证的令牌
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, "N/A", userDetails.getAuthorities());
                        // 3、设置令牌的Details
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 4、把令牌写入到当前安全线程上下文中：告知Spring Security过滤器链，当前线程已经认证，无需再认证
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtAuthenticationException exception) {
                request.setAttribute("jwterror", exception.getMessage());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
