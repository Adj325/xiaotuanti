package com.adj.xiaotuanti.util;

import com.adj.xiaotuanti.pojo.Token;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    /*
    ss：Issuer，发行者
    sub：Subject，主题
    aud：Audience，观众
    exp：Expiration time，过期时间
    nbf：Not before
    iat：Issued at，发行时间
    jti：JWT ID
    */
    private static final String Issuer = "ss";

    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow39934595fdf>?N<:{LWPW";

    private static final String EXP = "exp";

    private static final String UserAgent = "UserAgent";
    private static final String Host = "Host";
    private static final String OpenId = "OpenId";

    //加密，传入一个对象和有效期
    public String sign(Token token, long maxAge) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            claims.put(Issuer, "Adj325");
            claims.put(Host, token.getHost());
            claims.put(OpenId, token.getOpenId());
            claims.put(UserAgent, token.getUserAgent());

            // 设置过期时间，单位毫秒
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);

            // 存入 redis
            //redisTemplate.boundValueOps(openid).set(jwtToken, maxAge, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            return null;
        }
    }

    //解密，传入一个加密后的token字符串和解密后的类型
    public Token  unSign(String jwtToken) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> claims = verifier.verify(jwtToken);
            if (claims.containsKey(EXP)) {
                long exp = (Long) claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String userAgent = (String) claims.get(UserAgent);
                    String openId = (String) claims.get(OpenId);
                    String host = (String) claims.get(Host);
                    return new Token(openId, host, userAgent);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}