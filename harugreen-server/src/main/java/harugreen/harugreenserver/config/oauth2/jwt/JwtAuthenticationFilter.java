package harugreen.harugreenserver.config.oauth2.jwt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //request header 에서 토큰 추출
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("jwt authentication requestUri={}", req.getRequestURI());
        String accessToken = req.getParameter("accessToken");
        log.info("accessToken={}", accessToken);
        String token = extractToken((HttpServletRequest) request);
        log.info("jwt authentication doFilter token={}, req uri={}", token, ((HttpServletRequest) request).getRequestURI());

        //토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //토큰이 유요할 경우 토큰에서 Authentication 객체를 가져와 SecurityContext 에 저장.
            log.info("token validate");
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    //request header 에서 토큰 추출 (추후)
    //request parameter 에서 토큰 추출로 변경.
    private String extractToken(HttpServletRequest request) {
        log.info("jwt request parameter 에서 token 추출");
        String accessToken = request.getParameter("accessToken");
        String grantType = request.getParameter("grant");

        if(StringUtils.hasText(grantType) && grantType.startsWith("Bearer")) {
            return accessToken;
        }
        return null;
    }
}
