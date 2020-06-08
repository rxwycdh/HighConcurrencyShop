package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrderVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName OrdersController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/24 20:19
 * @Modified
 * @Version 1.0
 */
@Api(value = "订单接口", tags = {"订单相关接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("用户下单")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {

        if(!PayMethod.WEXIN.type.equals(submitOrderBO.getPayMethod())
                && !PayMethod.ALIPAY.type.equals(submitOrderBO.getPayMethod())) {
            return IMOOCJSONResult.errorMsg("支付方法不支持");
        }

        // 1.创建订单
        OrderVO ordervo = orderService.createOrder(submitOrderBO);
        MerchantOrderVO merchantOrderVO = ordervo.getMerchantOrderVO();
        merchantOrderVO.setReturnUrl(payReturnUrl);

        // 金额为一分钱
        merchantOrderVO.setAmount(1);

        // 2.移除购物车中已结算(已提交)的商品
        // TODO 整合redis之后，完善这里购物车中已结算的商品要清楚
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrderVO> entity = new HttpEntity<>(merchantOrderVO, headers);

        ResponseEntity<IMOOCJSONResult> imoocjsonResultResponseEntity = restTemplate
                .postForEntity(paymentUrl, entity, IMOOCJSONResult.class);
        IMOOCJSONResult paymentResult = imoocjsonResultResponseEntity.getBody();
        if(paymentResult.getStatus() != 200) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败");
        }

        LOGGER.info(submitOrderBO.toString());
        return IMOOCJSONResult.ok(ordervo.getOrderId());
    }

    /**
     * 通知的接口，当支付完成后，支付中心会通过此接口传入订单id，意味着此订单已经支付成功
     * @param merchantOrderId
     * @return
     */
    @PostMapping("notifyMerchantOrderPaid")
    public IMOOCJSONResult notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return IMOOCJSONResult.ok();
    }

    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(@RequestParam String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }
}
