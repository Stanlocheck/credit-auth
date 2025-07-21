package service.authorize.components;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        UserDetails userDetails = null;
        UsernamePasswordAuthenticationToken token = null;
        try{
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            }
            if(jwt!=null){
                try{
                    username = jwtUtil.getEmailFromJwt(jwt);
                } catch (ExpiredJwtException e) {
                    throw new RuntimeException(e);
                }
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                userDetails = userDetailsService.loadUserByUsername(username);
                token = new UsernamePasswordAuthenticationToken(userDetails, null);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
}
