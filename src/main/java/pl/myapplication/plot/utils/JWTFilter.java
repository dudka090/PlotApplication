package pl.myapplication.plot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JWTFilter implements javax.servlet.Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("Authorization");
        String login = httpServletRequest.getHeader("Login");
        String password = httpServletRequest.getHeader("Password");
        if(header == null || !header.startsWith("Bearer ")){
            throw  new ServletException("Not authorised. Lack of tocken");
        }else{
            try{
            String token = header.substring(7);
            Claims claims = Jwts.parser().setSigningKey(login+password).parseClaimsJws(token).getBody();
            servletRequest.setAttribute("claims", claims);}
            catch(Exception e) {
                throw new ServletException("Not authoriserd. Wrong tocken");
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
