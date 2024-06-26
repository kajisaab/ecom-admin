package com.kajisaab.ecommerce.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajisaab.ecommerce.core.exception.ApiException;
import com.kajisaab.ecommerce.core.exception.GenerateErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This entry point is invoked when an unauthenticated user tries to access a
 * protected resource. It's responsible for initiating the authentication process.
 * If authentication fails, it determines how to respond to the client.
 * The authenticationEntryPoint method configures the entry point to be used.
 * return 401 error response to frontend.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        {
            // Customize the response for unauthorized access
            logger.error("This is the auth Exception " + authException.getMessage());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            GenerateErrorMessage message = new GenerateErrorMessage("User don't have permission to access this url");

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("data", message);


            try (OutputStream out = response.getOutputStream()) {
                ApiException apiException = new ApiException(403, HttpStatus.FORBIDDEN, responseBody.get("data"));
                objectMapper.writeValue(out, apiException);
                out.flush();
            }
        }
    }
}
