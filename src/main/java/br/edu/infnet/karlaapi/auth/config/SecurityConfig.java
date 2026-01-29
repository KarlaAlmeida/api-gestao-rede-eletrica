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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
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
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // liberado geral
                        .requestMatchers("/login").permitAll()

                                //.requestMatchers("/api/**").permitAll()

                        .requestMatchers("/api/dashboard").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/ativos").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/ativos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/ativos/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/ativos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/ativos/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/tecnicos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tecnicos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/tecnicos/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tecnicos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/tecnicos/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/ocorrencias").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/ocorrencias/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/ocorrencias/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/ocorrencias/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/ocorrencias/{id}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/ordem-servico").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/ordem-servico/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/ordem-servico/{id}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/ordem-servico/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/ordem-servico/{id}").hasAnyRole("ADMIN")


                        // qualquer outra URL → autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
