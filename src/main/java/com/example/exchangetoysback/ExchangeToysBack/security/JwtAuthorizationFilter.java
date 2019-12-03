//package com.example.exchangetoysback.ExchangeToysBack.security;
//
//import com.example.exchangetoysback.ExchangeToysBack.constants.SecurityConstants;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws IOException, ServletException {
//        var authentication = getAuthentication(request);
//        if (authentication == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
//    }
// //dla tego jednogo sztywno zrobionego tokenu działa
////eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6InVzZXIiLCJleHAiOjE1NDgyNDI1ODksInJvbCI6WyJST0xFX1VTRVIiXX0.GzUPUWStRofrWI9Ctfv2h-XofGZwcOog9swtuqg1vSkA8kDWLcY3InVgmct7rq4ZU3lxI6CGupNgSazypHoFOA
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        var token = request.getHeader(SecurityConstants.TOKEN_HEADER);
//        if (!token.isEmpty()&&token!=null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
//            try {
//                var signingKey = SecurityConstants.JWT_SECRET.getBytes();
//
//                var parsedToken = Jwts.parser()
//                        .setSigningKey(signingKey)
//                        .parseClaimsJws(token.replace("Bearer ", ""));
//
//                var username = parsedToken
//                        .getBody()
//                        .getSubject();
//
//                var authorities = ((List<?>) parsedToken.getBody()
//                        .get("rol")).stream()
//                        .map(authority -> new SimpleGrantedAuthority((String) authority))
//                        .collect(Collectors.toList());
//
//                if (!username.isEmpty()&&token!=null) {
//                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
//                }
//            } catch (ExpiredJwtException exception) {
//                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
//            } catch (UnsupportedJwtException exception) {
//                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
//            } catch (MalformedJwtException exception) {
//                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
//            } catch (SignatureException exception) {
//                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
//            } catch (IllegalArgumentException exception) {
//                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
//            }
//        }
//
//        return null;
//    }
//}
