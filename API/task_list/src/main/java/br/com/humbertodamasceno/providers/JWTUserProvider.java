package br.com.humbertodamasceno.providers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTUserProvider {

    @Value("${security.token.secret.user}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        try {
            var tokenDecoded = JWT.require(algorithm).build().verify(token);

            return tokenDecoded;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

}
