package bookclip.server.jwt;

import bookclip.server.global.security.jwt.JwtTokenizer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
    private static JwtTokenizer jwtTokenizer;
    private String secretKey;
    private String base64EncodedKey;

    @BeforeAll
    public void init() {
        jwtTokenizer = new JwtTokenizer();
        secretKey = "testSecretKey12341234123412345678567856785678";
        base64EncodedKey = jwtTokenizer.encodedBase64SecretKey(secretKey);
    }

    @Test
    @DisplayName("비밀키 인코딩 테스트")
    void encodedBase63SecretKeyTest() {
        assertThat(secretKey, is(new String(Decoders.BASE64URL.decode(base64EncodedKey))));
    }

    @Test
    @DisplayName("AccessToekn 생성 테스트")
    void generateAccessTokenTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1L);
        claims.put("roles", List.of("USER"));

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();

        String accessToken =
                jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedKey);

        assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("RefreshToken 생성 테스트")
    void generateRefreshToken() {
        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();

        String refreshToken =
                jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedKey);

        assertThat(refreshToken, notNullValue());
    }

    @Test
    @DisplayName("JWT 검증 테스트")
    void verifySignatureTest() throws InterruptedException {
        String accessToken = getAccessToken(Calendar.SECOND, 1);

        assertDoesNotThrow(() -> jwtTokenizer.verifySignature(accessToken, base64EncodedKey));
        TimeUnit.MILLISECONDS.sleep(1500);

        assertThrows(ExpiredJwtException.class, () -> jwtTokenizer.verifySignature(accessToken, base64EncodedKey));
    }

    private String getAccessToken(int timeUnit, int timeAmount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1L);
        claims.put("roles", List.of("USER"));

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeUnit, timeAmount);
        Date expiration = calendar.getTime();

        String accessToken =
                jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedKey);

        return accessToken;
    }
}
