package harugreen.harugreenserver.config.oauth2;

import static harugreen.harugreenserver.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static harugreen.harugreenserver.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import harugreen.harugreenserver.config.oauth2.jwt.JwtToken;
import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider,
            HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.tokenProvider = tokenProvider;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("======================OAuth2 Authorization SUCCESS====================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("Principle 에서 꺼낸 oAuth2User={}", oAuth2User);

        String targetUrl = determineTargetUrl(request, response, authentication);

        JwtToken token = tokenProvider.generateToken(authentication);
        log.info("token={}", token);

        log.info("requestUri={}", request.getRequestURI());

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//        response.addHeader("X-jwt-access", token.getAccessToken());
//        response.addHeader("X-jwt-refresh", token.getRefreshToken());
//        response.addHeader("X-jwt-grant", token.getGrantType());

        log.info("sendRedirect={}", targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("request.cookie.value (redirect uri 값이 나올 것으로 예상) = {}",
                CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME));
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        log.info("determineTargetUrl redirect uri={}, target uri={}", redirectUri, targetUrl);

        JwtToken token = tokenProvider.generateToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .queryParam("grant", token.getGrantType())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
