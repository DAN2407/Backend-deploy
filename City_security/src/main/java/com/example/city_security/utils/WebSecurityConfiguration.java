package com.example.city_security.utils;
import com.example.city_security.models.entities.User;
import com.example.city_security.services.Userservice;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    //USER SERVICE sirve para buscar un usuario por su identificador
    private final Userservice userService;
    //JWT TOKEN FILTER sirve para filtrar las peticiones y verificar si el token es valido
    private final JWTTokenFilter filter;

    public WebSecurityConfiguration(Userservice userService, JWTTokenFilter filter) {
        this.userService = userService;
        this.filter = filter;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        managerBuilder
                .userDetailsService(identifier -> {
                    User user = userService.findByIdentifier(identifier);

                    if (user == null)
                        throw new UsernameNotFoundException("User: " + identifier + ", not found!");

                    return user;
                });

        return managerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Http login and cors disabled
        http.httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf.disable());

        //Route filter
        http.authorizeRequests(auth ->
                auth
                        .requestMatchers("/codigoqr/**").permitAll()
                        .requestMatchers("/historial-entrada/**").permitAll()
                        .requestMatchers("/Hogar/**").permitAll()
                        .requestMatchers("peticiones/**").permitAll()
                        .requestMatchers("/control-pluma/**").permitAll()
                        .requestMatchers("/role/**").permitAll()
                        .requestMatchers("/tipo/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated()

        );
        //Statelessness
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //UnAunthorized handler
        http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
            res.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Auth fail!"
            );
        }));

        //JWT filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}