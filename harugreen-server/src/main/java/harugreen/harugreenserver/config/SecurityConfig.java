package harugreen.harugreenserver.config;

import harugreen.harugreenserver.config.oauth2.CustomOAuth2UserService;
import harugreen.harugreenserver.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import harugreen.harugreenserver.config.oauth2.OAuth2AuthenticationFailureHandler;
import harugreen.harugreenserver.config.oauth2.OAuth2AuthenticationSuccessHandler;
import harugreen.harugreenserver.config.oauth2.jwt.JwtAuthenticationFilter;
import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * OAuth2 로그인 관련 설정을 처리한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("==========OAuth2 로그인 관련 설정 시작===========");
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt를 사용할 것이기 때문에 세션사용 x
                .and()
                .formLogin().disable() //spring security 에서 기본적으로 제공하는 로그인 폼 안씀
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                //.anyRequest().authenticated() //위의 uri 빼고는 인증을 거쳐야 함.
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth/authorize/**") //인가 코드를 요청하는 엔드포인트.
                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
                .and()
                .redirectionEndpoint().baseUri("/oauth/login/**")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService) //OAuth2 로그인 성공 시, 후 작업을 넘긴다.
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
