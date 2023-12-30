package it.live.educationtest.config;


import it.live.educationtest.entity.User;
import it.live.educationtest.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    @Autowired
    private final JwtFilter jwtFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfiguration(@Lazy JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    public static User getOwnSecurityInformation() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.addAllowedHeader("*");
            return corsConfiguration;
        })).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/auth/**",
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest()
                        .authenticated());
        httpSecurity.sessionManagement(httpsecure -> httpsecure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}