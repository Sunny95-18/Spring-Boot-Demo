package com.sjw.test.controller.order;

import com.sjw.test.common.BaseController;
import com.sjw.test.common.aspect.LogAnnotation;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import com.sjw.test.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获取所有订单", notes = "获取所有订单",consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @LogAnnotation(module = "订单管理",action ="获取所有订单" )
    public Response<String> list(){
        return success("订单列表");
    }

    @ApiOperation(value = "下单", notes = "下单",consumes = "application/json")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    @LogAnnotation(module = "订单管理",action ="下单" )
    public Response<Boolean> createOrder(){
        return success(orderService.createOrder());
    }
}
