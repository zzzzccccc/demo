package com.zcx.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zcx.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private String secret;
    public void setSecret(String secret) {
        this.secret = secret;
    }

    private Long validateTime;
    public void setValidateTime(Long validateTime) {
        this.validateTime = validateTime;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies.length < 1) return false;
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) return false;
        if (StringUtils.isEmpty(token)) return false;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        Double createTime = claims.get("createTime").asDouble();
        if (System.currentTimeMillis() - createTime > validateTime) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("token has expired");
            return false;
        }
        refreshToken(request, response, claims); // 得先 设置cookie 再request.setAttribute
        request.setAttribute("claims", claims);
        return true;
    }

    private void refreshToken(HttpServletRequest request, HttpServletResponse response, Map<String, Claim> claims) throws UnsupportedEncodingException {
        Map<String, Object> headerClaims = TokenUtils.getHeaderClaims();
        Claim nameClaim = claims.get("name");
        String name = null;
        if (nameClaim != null) {
            name = nameClaim.asString();
        }
        String token = JWT.create().withHeader(headerClaims).withClaim("loginname", claims.get("loginname").asString()).
                withClaim("id", claims.get("id").asInt()).withClaim("name", name).
                withClaim("createTime", (double) System.currentTimeMillis()).
                sign(Algorithm.HMAC256(secret));
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }
}
