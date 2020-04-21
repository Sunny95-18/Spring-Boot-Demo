package com.sjw.test.controller.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjw.test.common.BaseController;
import com.sjw.test.common.aspect.LogAnnotation;
import com.sjw.test.common.utils.IPUtils;
import com.sjw.test.common.vo.Request;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import com.sjw.test.entity.user.dto.UserLoginDto;
import com.sjw.test.entity.user.vo.UserTokenVo;
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

    @ApiOperation(value = "获取所有用户", notes = "获取所有用户",consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<User>> list(){
         return success(userService.seleceUsers());
    }

    @ApiOperation(value = "修改用户", notes = "修改用户",consumes = "application/json")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<Boolean> update(@RequestBody Request<String> request){
        return success(userService.updateUser(request.getData()));
    }
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> register(@RequestBody Request<UserLoginDto> request ) {
       return success(userService.register(request.getData()));
    }
    @ApiOperation(value = "用户登录", notes = "用户登录",consumes = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LogAnnotation(module = "用户模块",action ="用户登录" )
    public Response<UserTokenVo> login(@RequestBody Request<UserLoginDto> request, HttpServletRequest httpServletRequest){
        String ip= IPUtils.getIpAddr(httpServletRequest);
        log.info("ip:{}",ip);
        return success(userService.login(request.getData()));
    }
}
