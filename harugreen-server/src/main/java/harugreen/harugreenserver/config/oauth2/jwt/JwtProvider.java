package harugreen.harugreenserver.config.oauth2.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpiredIn;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiredIn;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    //이메일 기반으로 jwt 생성하기
    public JwtToken createToken(String email) {
        Claims claim = Jwts.claims().setSubject(email);
        Date date = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + accessTokenExpiredIn))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(date.getTime() + refreshTokenExpiredIn))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //access token 복호화를 통한 유저 이메일 파싱
    public String getUserEmailByDecodedJwt(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        return claims.getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-REFRESH");
    }


    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //TODO: access token 이 만료되었으면, refresh token 을 통해 재발급.
            if(!claims.getBody().getExpiration().before(new Date())) {
                //access token이 만료되었다면 재발급.

            }
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
