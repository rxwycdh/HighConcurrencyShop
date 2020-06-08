package com.imooc.mapper;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @FileName OrdersMapperCustom
 * @Description
 * @Author jiuhao
 * @Date 2020/6/3 19:42
 * @Modified
 * @Version 1.0
 */
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryByOrders(@Param("paramsMap") Map<String, Object> map);

    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}
