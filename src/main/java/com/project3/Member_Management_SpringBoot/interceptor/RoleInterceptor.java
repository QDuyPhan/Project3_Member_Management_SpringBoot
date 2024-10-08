package com.project3.Member_Management_SpringBoot.interceptor;

import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleRequire roleRequireAnnotation = handlerMethod.getMethodAnnotation(RoleRequire.class);

            if (roleRequireAnnotation != null) {
                String[] requiredRoles = roleRequireAnnotation.value();
                Member member = (Member) request.getSession().getAttribute("user");
                String role = (String) request.getSession().getAttribute("role");
                if (member == null || !hasRequiredRole(role, requiredRoles)) {
                    response.sendRedirect("/login");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasRequiredRole(String userRole, String[] requiredRoles) {
        for (String role : requiredRoles) {
            if (role.equals(userRole)) {
                return true;
            }
        }
        return false;
    }

}
