package com.example.GTC.utils;

import com.example.GTC.config.BaseException;
import com.example.GTC.config.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.GTC.config.BaseResponseStatus.*;


@Service
public class JwtService {
    public String creatJwt(Long user_id) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type,", "jwt")
                .claim("user_id", user_id)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public int getUserId() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        System.out.println("accessToken = " + accessToken);
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        //2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        return claims.getBody().get("user_id", Integer.class);
    }
}
