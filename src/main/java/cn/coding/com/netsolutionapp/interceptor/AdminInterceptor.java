package cn.coding.com.netsolutionapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null || username.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            } else if ( username.equals("admin")) {
                return true;
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Execute the postHandle method of Interceptor . . .");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Execute the afterCompletion method of Interceptor. . .");
    }
}
