package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.service.ccenter.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @FileName BaseController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/21 15:58
 * @Modified
 * @Version 1.0
 */
@Controller
public class BaseController {

    public static final Integer COMMON_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    public static final String FOODIE_SHOPCART = "shopcart";

    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /**
     * 微信支付成功 通知-> 支付中心 通知->               天天吃货平台
     *                              |-> 回调通知的url
     */
    String payReturnUrl = "http://a597701764.eicp.net/orders/notifyMerchantOrderPaid";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = "F:\\myjava\\study\\imooc\\faces";

        // linux
//    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "usr" + File.separator + "faces";

    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 验证用户与订单是否具有关联关系
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if(orders == null) {
            return IMOOCJSONResult.errorMsg("订单不存在");
        }
        return IMOOCJSONResult.ok(orders);
    }
}
