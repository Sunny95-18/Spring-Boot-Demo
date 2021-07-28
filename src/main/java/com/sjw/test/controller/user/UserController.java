package com.sjw.test.controller.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjw.test.common.BaseController;
import com.sjw.test.common.annotation.AccessLimit;
import com.sjw.test.common.aspect.LogAnnotation;
import com.sjw.test.common.exceptions.SysExceptionEnum;
import com.sjw.test.common.exceptions.SystemException;
import com.sjw.test.common.utils.IPUtils;
import com.sjw.test.common.vo.Request;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import com.sjw.test.entity.user.dto.UserLoginDto;
import com.sjw.test.entity.user.vo.UserTokenVo;
import com.sjw.test.service.redis.RedisService;
import com.sjw.test.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 15:01
 */

@RestController
@Slf4j
@RequestMapping("/user")
@Api("用戶管理")
public class UserController extends BaseController {


    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;


    @ApiOperation(value = "修改用户", notes = "修改用户", consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<Boolean> update(@RequestBody Request<String> request) {
        return success(userService.updateUser(request.getData()));
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> register(@RequestBody Request<UserLoginDto> request) {
        return success(userService.register(request.getData()));
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", consumes = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @LogAnnotation(module = "用户模块",action ="用户登录" )
    public Response<UserTokenVo> login(@RequestBody Request<UserLoginDto> request, HttpServletRequest httpServletRequest) {
        String ip = IPUtils.getIpAddr(httpServletRequest);
        log.info("ip地址{}" + ip);
        System.out.println("getIpAddr:" + IPUtils.getIpAddr(httpServletRequest));
        System.out.println("getRemoteAddr:" + IPUtils.getRemoteAddr(httpServletRequest));
        System.out.println("getClientIpAddr:" + IPUtils.getClientIpAddr(httpServletRequest));
        System.out.println("getClientIpAddress:" + IPUtils.getClientIpAddress(httpServletRequest));

        Object result = redisService.get("LOGIN_" + ip);
        if (result == null) {
            redisService.set("LOGIN_" + ip, 1);
        } else {
            int count = Integer.parseInt(result.toString());
            if (count > 2) {
                throw new SystemException(SysExceptionEnum.LOGIN_ERROR.getCode(), SysExceptionEnum.LOGIN_ERROR.getMessage());
            } else {
                redisService.set("LOGIN_" + ip, ++count, 300L);
            }
        }
        return success(userService.login(request.getData()));
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Response<Boolean> test() {

        System.out.println("test");
        return success();
    }
}
