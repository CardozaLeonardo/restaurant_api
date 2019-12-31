package com.leocode.securityapi.filters;

import com.leocode.securityapi.services.MyUserDetailsService;
import com.leocode.securityapi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if("/authenticate".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }


        //final String authorizationHeader = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();


        String authorizationHeader = null;
        String refreshToken = "";

        if(cookies != null){


            authorizationHeader = "Bearer " + cookies[0].getValue();
            refreshToken = cookies[1].getValue();
            //logger.info("cookie 1: " + cookies[0].getName() + ", cookie2: "+ cookies[1].getName());
        }



        String username = null;
        String jwt = null;

        try{
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);


                username = jwtUtil.extractUsername(jwt);



                //System.out.println("Content of username: " + username);
            }else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                //response.getWriter().write("{\"message\" : \"There is not token...\"}");

            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            }
        }catch(ExpiredJwtException ejp){

            try {

                username = jwtUtil.extractUsername(refreshToken);
                UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
                if(refreshToken != null && jwtUtil.validateToken(refreshToken, userDetails)) {



                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);



                    String newAccessToken = jwtUtil.generateToken(userDetails);
                    String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

                    Cookie newAccessCookie = new Cookie("access-token", newAccessToken);
                    Cookie newRefreshCookie = new Cookie("refresh-token", newRefreshToken);

                    response.addCookie(newAccessCookie);
                    response.addCookie(newRefreshCookie);
                }
            }catch(ExpiredJwtException ejp2) {
                ejp2.getMessage();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                chain.doFilter(request, response);
                return;

            }
        }



        chain.doFilter(request, response);
    }
}
