package com.imooc.service.ccenter;

import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;

/**
 * @FileName MyOrdersService
 * @Description
 * @Author jiuhao
 * @Date 2020/6/3 19:53
 * @Modified
 * @Version 1.0
 */
public interface MyOrdersService {
    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedGridResult queryByOrders(String userId, Integer orderStatus, Integer pageNum, Integer pageSize);

    /**
     * 将订单状态改为已发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 -> 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiverOrderStatus(String orderId);

    /**
     * 逻辑删除订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean updateReceiverOrderStatus(String userId, String orderId);

    /**
     * 查询用户订单数
     * @param userId
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
