package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.ccenter.MyCommentsService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName MyCommentsController
 * @Description
 * @Author jiuhao  597701764@qq.com
 * @Date 2020/6/6 19:23
 */
@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation("查询订单商品关联")
    @PostMapping("/pending")
    public IMOOCJSONResult comments(@RequestParam String userId,
                                    @RequestParam String orderId) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }
        // 判断订单是否已经评价过
        Orders myOrder = (Orders)imoocjsonResult.getData();
        if(YesOrNo.YES.type.equals(myOrder.getIsComment())) {
            return IMOOCJSONResult.errorMsg("该订单已经评价");
        }

        List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);

        return IMOOCJSONResult.ok(orderItems);
    }

    @ApiOperation("保存评论列表")
    @PostMapping("/saveList")
    public IMOOCJSONResult comments(@RequestParam String userId,
                                    @RequestParam String orderId,
                                    @RequestBody List<OrderItemsCommentBO> commentList) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        // 判断评论list不能为空
        if(commentList == null || commentList.isEmpty()) {
            return IMOOCJSONResult.errorMsg("评论不能为空");
        }

        myCommentsService.saveComments(orderId, userId, commentList);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评论列表")
    @PostMapping("/query")
    public IMOOCJSONResult query(@RequestParam String userId,
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

        PagedGridResult pagedGridResult = myCommentsService
                .queryMyComments(userId, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }
}
