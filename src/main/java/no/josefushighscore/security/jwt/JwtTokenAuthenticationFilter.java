package no.josefushighscore.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.service.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
public class JwtTokenAuthenticationFilter implements Filter {

    private JwtTokenProvider jwtTokenProvider;

    private Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        log.debug("TOKEN fra JwtTokenAuthenticationFilter: " + token);
        log.debug("Requesturl fra JwtTokenAuthenticationFilter: " + ((HttpServletRequest) req).getRequestURI());

        Authentication rolle = SecurityContextHolder.getContext().getAuthentication();
        log.debug("doFilter fra JwtTokenAuthenticationFilter");

        if(exception!=null && exception instanceof BadRequestException){
            APIResponse errorResponse = new APIResponse(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED),exception.getMessage(),"Authetication Failed!");
            errorResponse.setStatus(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(convertObjectToJson(errorResponse));
            writer.flush();
            return;
        }
        // If exception instance cannot be determined, then throw a nice exception and desired response code.
        else if(exception!=null){
            APIResponse errorResponse = new APIResponse(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED),exception.getMessage(),"Authetication Failed!");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(convertObjectToJson(errorResponse));
            writer.flush();
            return;
        }
        else {
            // proceed with the initial request if no exception is thrown.
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(req,res);
        }
    }

    /*private Authentication TokenFreeAuthentication (ServletRequest req) {
        if (((HttpServletRequest) req).getRequestURI().equals("/registervisit")) {
            return new AnonymousAuthenticationToken();
        }
    }*/

    private boolean TokenFreeRequests (ServletRequest req) {
        if (((HttpServletRequest) req).getRequestURI().equals("/registervisit"))
            return true;
        else
            return false;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


}

