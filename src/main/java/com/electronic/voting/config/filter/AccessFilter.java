package com.electronic.voting.config.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isStaticResource(requestURI)) {
            // It's a static resource, so just continue the chain without redirecting
            chain.doFilter(request, response);
            return;
        }
        // Check if the requested URI is the login page
        if (!requestURI.endsWith("login.xhtml")) {
            HttpSession session = request.getSession(false);
            boolean isLoggedIn = (session != null) && (session.getAttribute("USER_ID") != null);

            if (!isLoggedIn) {
                // Not logged in, redirect to login page
                response.sendRedirect(request.getContextPath() + "/login.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String requestURI) {
        return requestURI.contains("/resources/") ||
                requestURI.contains("/static/") ||
                requestURI.contains("/js/") ||
                requestURI.contains("/css/") ||
                requestURI.contains("/images/") ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".css") ||
                requestURI.endsWith(".png") ||
                requestURI.endsWith(".jpg") ||
                requestURI.endsWith("javax.faces.resource") ||
                requestURI.endsWith(".gif");
    }

}
