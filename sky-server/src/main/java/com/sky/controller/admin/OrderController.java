package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminOrderController")
@Slf4j
@Api(tags = "商家订单相关接口")
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
        log.info("取消订单，{}",ordersCancelDTO);
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> list(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("查询所有订单，{}",ordersPageQueryDTO);
        PageResult pageResult = orderService.adminList(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单信息")
    public Result<OrderVO> detail(@PathVariable Long id){
        log.info("查看订单详情,{}",id);
        OrderVO orderVO = orderService.findById(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result accept(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("接单：{}",ordersConfirmDTO);
        orderService.accept(ordersConfirmDTO);
        return Result.success();
    }
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result reject(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        log.info("接单：{}",ordersRejectionDTO);
        orderService.reject(ordersRejectionDTO);
        return Result.success();
    }

    @GetMapping ("/statistics")
    @ApiOperation("各个订单的数量")
    public Result<OrderStatisticsVO> statistic(){

        OrderStatisticsVO orderStatisticsVO = orderService.statistic();
        return Result.success(orderStatisticsVO);
    }

    @PutMapping("delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable Long id){
        log.info("开始派送订单");
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable Long id){
        log.info("完成订单");
        orderService.complete(id);
        return Result.success();
    }










}
