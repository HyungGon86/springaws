package study.springaws.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import study.springaws.domain.user.domain.Role;
import study.springaws.global.oauth.PrincipalOauth2UserService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationFailureHandler customFailureHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests(authorize -> authorize
                        .antMatchers("/post/write", "/post/edit", "/post/delete", "/edit/category", "/category/edit").hasRole(Role.MANAGER.name())
                        .antMatchers("/comment/write", "/comment/delete").authenticated()
                        .anyRequest().permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me"))
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .failureHandler(customFailureHandler)
                        .userInfoEndpoint()
                        .userService(principalOauth2UserService));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers(
                "/favicon.ico", "/error", "/img/**"
        ));
    }
}
