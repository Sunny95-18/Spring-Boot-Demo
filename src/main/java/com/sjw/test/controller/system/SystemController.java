package com.sjw.test.controller.system;

import com.sjw.test.common.BaseController;
import com.sjw.test.common.aspect.LogAnnotation;
import com.sjw.test.common.utils.OSUtils;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/28 11:30
 */
@RestController
@Slf4j
@RequestMapping("/sys")
@Api("用戶管理")
public class SystemController extends BaseController {



    @ApiOperation(value = "获取系统信息", notes = "获取系统信息",consumes = "application/json")
    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.GET)
    public Response<Map<String,Integer>> getSystemInfo(){
        Map<String,Integer> map=new HashMap<>();
               map.put("cpu", OSUtils.cpuUsage());
               map.put("memory",OSUtils.memoryUsage());
        return success(map);
    }
}
