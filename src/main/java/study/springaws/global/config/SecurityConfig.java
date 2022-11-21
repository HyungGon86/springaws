package study.springaws.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {

//    private final CustomOAuth2UserService customOauth2UserService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http.headers().frameOptions().disable()
//                .and()
//                .authorizeRequests(authorize -> authorize
//                        .antMatchers("/api/v1/**").hasRole(Role.MANAGER.name())
//                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()
//                        .anyRequest().authenticated())
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/"))
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .userInfoEndpoint()
//                        .userService(customOauth2UserService));
//
//        return http.build();
//    }
}
