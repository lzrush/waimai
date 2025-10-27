package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Page<Orders> list(OrdersPageQueryDTO ordersPageQueryDTO);


    @Select("select * from orders where id = #{id}")
    Orders findById(Long id);

    @Delete("delete from orders where id = #{id}")
    void deleteById(Long id);

    @Update("update orders set status = 6 where id = #{id}")
    void updateById(Long id);

    OrderStatisticsVO listGroupBy(Long id);

    @Select("select  * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);

    Double sumByMap(Map map);

    Integer sumByTime(LocalDateTime beginTime, LocalDateTime endTime, Integer status);

    List<Map<String, Object>> Top10(LocalDateTime beginTime, LocalDateTime endTime, Integer status);
}
