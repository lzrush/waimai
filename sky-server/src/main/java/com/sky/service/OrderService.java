package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult list(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO findById(Long id);

    void cancelOrder(Long id);

    void again(Long id);


    void cancel(OrdersCancelDTO ordersCancelDTO);

    PageResult adminList(OrdersPageQueryDTO ordersPageQueryDTO);

    void accept(OrdersConfirmDTO ordersConfirmDTO);

    void reject(OrdersRejectionDTO ordersRejectionDTO);

    OrderStatisticsVO statistic();

    void delivery(Long id);

    void complete(Long id);

    void reminder(Long id);
}
