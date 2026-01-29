package br.edu.infnet.karlaapi.auth.config;


import br.edu.infnet.karlaapi.auth.JwtAuthenticationFilter;
import br.edu.infnet.karlaapi.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromJwt);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromJwt(Jwt jwt) {
        Object realmAccessObj = jwt.getClaim("realm_access");
        if (!(realmAccessObj instanceof Map<?, ?> realmAccess)) {
            return List.of();
        }

        Object rolesObj = realmAccess.get("roles");
        if (!(rolesObj instanceof List<?> roles)) {
            return List.of();
        }

        return roles.stream()
                .filter(r -> r instanceof String)
                .map(r -> (String) r)
                // aqui criamos uma List<GrantedAuthority>
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userComum = User.withUsername("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//        UserDetails userAdm = User.withUsername("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userComum,userAdm);
//    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        return new  JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,  JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // liberado geral
                        .requestMatchers("/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/ativos").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "api/ativos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/ativos/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/ativos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/ativos/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/tecnicos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/tecnicos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/tecnicos/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/tecnicos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/tecnicos/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/ocorrencias").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "api/ocorrencias/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/ocorrencias/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/ocorrencias/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/ocorrencias/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/ordem-servico").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/ordem-servico/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/ordem-servico/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/ordem-servico/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/ordem-servico/{id}").hasAnyRole("ADMIN")

                        // qualquer outra URL → autenticado
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                ).build();
        return http.build();
    }
}
