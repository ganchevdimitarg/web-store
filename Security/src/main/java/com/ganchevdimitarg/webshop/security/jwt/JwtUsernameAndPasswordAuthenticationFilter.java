package com.ganchevdimitarg.webshop.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganchevdimitarg.webshop.security.service.service.UserDao;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    /**
     * Attempt Authentication by username and password
     * Authentication are:
     * - principal = username
     * - credential = password
     * Authentication manager would make sure that username exist and
     * if exist it will check whether the password is correct or not
     * In that the case then this request will be authenticated
     * @param request sent by the client and containing a username and password
     * @param response contains JWT token
     * @return authenticated request
     * @throws AuthenticationException is IOException
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            log.info("Successful authenticated");
            return authenticate;

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Create token and send to the client.
     * This method will be invoke after the attemptAuthentication is successful
     * if the attemptAuthentication failed the successfulAuthentications method never be executed
     * @param request sent by the client and containing a username and password
     * @param response contains JWT token
     * @param chain terminates the filter and sends the request and response
     * @param authResult getting the name of authentication result
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
        log.info(String.format("Create JWT token and add to the response header %s", token));
    }


}
