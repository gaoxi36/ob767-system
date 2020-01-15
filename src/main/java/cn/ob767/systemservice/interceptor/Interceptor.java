package cn.ob767.systemservice.interceptor;

import cn.ob767.systemservice.utils.RedisUtils;
import cn.ob767.systemservice.utils.enumerations.ResponseStatus;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Cookie[] cookies = request.getCookies();
        String user_token = null;
        String user = null;
        for (Cookie cookie : cookies
        ) {
            if (cookie.getName().equals("user_token")) {
                user_token = cookie.getValue();
            } else if (cookie.getName().equals("user")) {
                user = cookie.getValue();
            }
        }
        if (null != user_token && null != user) {
            if (redisUtils.redisGet(user + "_token") != null && redisUtils.redisGet(user + "_token").equals(user_token)) {
                return true;
            } else {
                returnJson(response);
                return false;
            }
        } else {
            returnJson(response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    private void returnJson(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "用户登录失效，请重新登录!");
        try (PrintWriter writer = response.getWriter()) {
            ResponseInterceptor<JSONObject> responseInterceptor = new ResponseInterceptor<>(ResponseStatus.FAILED);
            responseInterceptor.setData(jsonObject);
            writer.print(responseInterceptor.toString());
        } catch (IOException e) {
            log.error("拦截器输出流异常" + e);
        }
    }
}
