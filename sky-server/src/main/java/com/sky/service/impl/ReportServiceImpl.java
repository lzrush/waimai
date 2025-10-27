package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     *
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate i = begin ; !i.equals(end) ; i=i.plusDays(1) )
            dateList.add(i);
        dateList.add(end);
        String ds = StringUtils.join(dateList, ",");
        List<Double> turnoverList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover =  orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0: turnover;
            turnoverList.add(turnover);
            //select sum(amount) from orders where order_time > ? and order_time < ? and status = 5 ;
        }
        String ts = StringUtils.join(turnoverList,",");
        return new TurnoverReportVO(ds,ts);
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<Long> totalUserList = new ArrayList<>();
        List<Long> newUserList = new ArrayList<>();
        for(LocalDate i = begin ; !i.equals(end) ; i = i.plusDays(1))
            dateList.add(i);
        dateList.add(end);
        Long sum = 0L;
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date,LocalTime.MAX);
            Long newUser = userMapper.newsum(beginTime,endTime);
            newUser = newUser == null ? 0L : newUser;
            sum += newUser;
            newUserList.add(newUser);
            totalUserList.add(sum);
        }
        String dl = StringUtils.join(dateList, ",");
        String tl = StringUtils.join(totalUserList, ",");
        String nl = StringUtils.join(newUserList, ",");
        return new UserReportVO(dl,tl,nl);
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<Integer> orderList = new ArrayList<>();
        List<Integer> validOrderList = new ArrayList<>();
        Integer sum = 0,validSum = 0;
        for (LocalDate i = begin ; !i.equals(end) ; i = i.plusDays(1)){
            dateList.add(i);
        }
        dateList.add(end);
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer status = null;
            Integer dSum = orderMapper.sumByTime(beginTime,endTime,status);
            status = Orders.COMPLETED;
            Integer dVSum = orderMapper.sumByTime(beginTime,endTime,status);
            orderList.add(dSum);
            validOrderList.add(dVSum);
            sum += dSum;
            validSum += dVSum;
        }
        String ds = StringUtils.join(dateList, ",");
        String os = StringUtils.join(orderList, ",");
        String vos = StringUtils.join(validOrderList, ",");
        return new OrderReportVO(ds,os,vos,sum,validSum, sum == 0?0.0:validSum*1.0/sum);
    }

    @Override
    public SalesTop10ReportVO getOrderTop10(LocalDate begin, LocalDate end) {

        LocalDateTime beginTime = LocalDateTime.of(begin,LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end,LocalTime.MAX);

        List<Map<String,Object>> mapList = orderMapper.Top10(beginTime,endTime,Orders.COMPLETED);
        List<Object> nameList = mapList.stream().map(map -> map.get("name")).toList();
        List<Object> numberList = mapList.stream().map(map -> map.get("num")).toList();
        String ns = StringUtils.join(nameList, ",");
        String ms = StringUtils.join(numberList, ",");
        return new SalesTop10ReportVO(ns,ms);
    }

}
