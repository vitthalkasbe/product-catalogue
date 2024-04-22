package org.oms.in.productcatalogue.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

// protected void configure(HttpSecurity http) throws Exception
// {
//   http.csrf().disable()
//           .authorizeRequests()
//           .anyRequest().hasAuthority("ROLE_USER")
//           .and()
//           .httpBasic();
// }
//
//protected void configure(AuthenticationManagerBuilder auth) throws Exception
//{
//     auth.inMemoryAuthentication()
//            .withUser("user")
//            .password("password")
//            .roles("USER");
//}
//
//@Bean
//public NoOpPasswordEncoder passwordEncoder()
//{
//    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//}

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                    .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER")
                .build()
        );

        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,BCryptPasswordEncoder bCryptPasswordEncoder,UserDetailsService userDetailsService) throws Exception
    {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
//                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs.yaml")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        return http.build();
    }
}
