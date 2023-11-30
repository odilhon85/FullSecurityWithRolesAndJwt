package uz.devior.fullsecuritywithrolesandjwt.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.internal.AbstractDynamicMapInstantiator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.devior.fullsecuritywithrolesandjwt.jwt.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static uz.devior.fullsecuritywithrolesandjwt.user.Permission.*;
import static uz.devior.fullsecuritywithrolesandjwt.user.Role.ADMIN;
import static uz.devior.fullsecuritywithrolesandjwt.user.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request  -> request
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers(GET, "api/v1/user/**").permitAll()

                        .requestMatchers("api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT,"api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name())
                        .requestMatchers(POST,"api/v1/user/**").hasAnyAuthority(USER_CREATE.name())
                        .requestMatchers(PUT,"api/v1/user/**").hasAnyAuthority(USER_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/user/**").hasAnyAuthority(USER_DELETE.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
