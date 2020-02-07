package org.chris.demo.actuator.config;

import java.util.stream.Stream;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Order(10)
    @Configuration(proxyBeanMethods = false)
    static class ActuatorSecurity extends WebSecurityConfigurerAdapter {

        public static final String ENDPOINT_ADMIN = "ENDPOINT_ADMIN";

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
                .requestMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeRequests(requests -> requests
                    .requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                    .anyRequest().hasRole(ENDPOINT_ADMIN))
                .httpBasic();
        }

    }

    // no @Order defaults to last
    @Configuration(proxyBeanMethods = false)
    static class ApiSecurity extends WebSecurityConfigurerAdapter {

        public static final String API_USER = "API_USER";
        public static final String API_ADMIN = "API_ADMIN";

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
                .authorizeRequests(requests -> requests
                    .antMatchers("/admin/api/**").hasRole(API_ADMIN)
                    .antMatchers("/api/**").hasRole(API_USER)
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated())
                .httpBasic();
        }

    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {

        UserBuilder userBuilder = User.builder()
            .passwordEncoder(noOpPassword()::encode);

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1 = userBuilder
            .username("admin")
            .password("admin")
            .roles(ApiSecurity.API_USER, ApiSecurity.API_ADMIN)
            .build();

        UserDetails user2 = userBuilder
            .username("user")
            .password("user")
            .roles(ApiSecurity.API_USER)
            .build();

        UserDetails user3 = userBuilder
            .username("ops")
            .password("ops")
            .roles(ActuatorSecurity.ENDPOINT_ADMIN)
            .build();

        Stream.of(user1, user2, user3).forEach(manager::createUser);

        return manager;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder noOpPassword() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

}
