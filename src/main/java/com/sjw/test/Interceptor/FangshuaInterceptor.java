package com.sjw.test.Interceptor;

import com.alibaba.fastjson.JSON;
import com.sjw.test.common.annotation.AccessLimit;
import com.sjw.test.common.exceptions.SysExceptionEnum;
import com.sjw.test.common.utils.JwtTokenUtil;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import com.sjw.test.service.redis.RedisService;
import com.sjw.test.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author Sunny
 * @version 1.0
 * @date 2021/7/28 9:35
 */
@Component
public class FangshuaInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("request:"+request.getHeader("Authorization"));
      String token=request.getHeader("Authorization");

        //判断请求是否属于方法的请求
        if(handler instanceof HandlerMethod){

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if(login){
                //获取登录的session进行判断

//                key+=user.getName()+"_"+user.getId();  //这里假设用户是1,项目中是动态获取的userId
            }

            //从redis中获取用户访问的次数
            Integer count =(Integer) redisService.get(key);
            System.out.println("count:"+count);
            if(count == null){
                //第一次访问
                redisService.set(key,1);
            }else if(count < maxCount){
                //加1
                redisService.set(key,count+1);
            }else{
                //超出访问次数
                render(response, SysExceptionEnum.ACCESS_LIMIT_REACHED.getMessage()); //这里的CodeMsg是一个返回参数
                return false;
            }
        }

        return true;

    }
    private void render(HttpServletResponse response, String message)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(Response.failed(message));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
