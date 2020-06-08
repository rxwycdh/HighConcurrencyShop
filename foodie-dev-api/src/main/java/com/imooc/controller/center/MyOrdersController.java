package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.ccenter.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName MyOrdersController
 * @Description
 * @Author jiuhao
 * @Date 2020/6/3 19:59
 * @Modified
 * @Version 1.0
 */
@Api(value = "用户中心我的订单", tags = {"用户中心我的订单"})
@RequestMapping("myorders")
@RestController
public class MyOrdersController extends BaseController {

//    @Autowired
//    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表")
    @PostMapping("/query")
    public IMOOCJSONResult queryOrders(@RequestParam String userId,
                                       @RequestParam Integer orderStatus,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize) {
        if(StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMap(null);
        }

        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = myOrdersService
                .queryByOrders(userId, orderStatus, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @ApiOperation("模拟商家发货")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(@RequestParam String orderId) {

        if(StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单id不能为空");
        }

        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation("确认收货")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(@RequestParam String orderId,
                                          @RequestParam String userId) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        boolean res = myOrdersService.updateReceiverOrderStatus(orderId);
        if(!res) {
            return IMOOCJSONResult.errorMsg("确认收货失败");
        }

        return IMOOCJSONResult.ok();
    }



    @ApiOperation("用户删除订单")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@RequestParam String orderId,
                                  @RequestParam String userId) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        boolean res = myOrdersService.updateReceiverOrderStatus(userId, orderId);
        if(!res) {
            return IMOOCJSONResult.errorMsg("删除订单失败");
        }

        return IMOOCJSONResult.ok();
    }
    @ApiOperation("获得订单状态数概况")
    @PostMapping("/statusCounts")
    public IMOOCJSONResult statusCounts(@RequestParam String userId) {

        if(StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMap(null);
        }

        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);

        return IMOOCJSONResult.ok(orderStatusCounts);
    }

    @ApiOperation(value = "查询订单动向")
    @PostMapping("/trend")
    public IMOOCJSONResult trend(@RequestParam String userId,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize) {

        if(StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMap(null);
        }

        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myOrdersService
                .getOrdersTrend(userId, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }
}
