package service.authorize.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import service.authorize.components.JwtFilter;
import service.authorize.services.UserDetailsServiceImp;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtFilter tokenFilter;

    public SecurityConfig() {}

    @Autowired
    public void setTokenFilter(JwtFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Бин AuthenticationManager нужен для использования в AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request ->
                                new CorsConfiguration().applyPermitDefaultValues())
                )
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/secured/user").fullyAuthenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
