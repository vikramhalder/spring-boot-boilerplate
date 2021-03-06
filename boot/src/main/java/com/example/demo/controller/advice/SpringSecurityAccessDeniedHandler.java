package com.example.demo.controller.advice;

import com.example.demo.core.helper.RequestHelper;
import com.example.demo.core.iservice.ApiController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SpringSecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Value("${security.oauth.redirect.path}")
    private String redirectPath;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        if ((request.getHeader("Accept") + "").contains("text/html")) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", redirectPath);
        } else {
            response.setContentType("application/json");
            response.getWriter().print(ApiController.json(false, null,  RequestHelper.getExceptionMessage(exception)));
        }
    }

}