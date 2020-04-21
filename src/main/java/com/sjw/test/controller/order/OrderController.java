package com.sjw.test.controller.order;

import com.sjw.test.common.BaseController;
import com.sjw.test.common.aspect.LogAnnotation;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/21 17:28
 */
@RestController
@Slf4j
@RequestMapping("/order")
@Api("订单管理")
public class OrderController extends BaseController {

    @ApiOperation(value = "获取所有订单", notes = "获取所有订单",consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @LogAnnotation(module = "订单管理",action ="获取所有订单" )
    public Response<String> list(){
        return success("订单列表");
    }
}
