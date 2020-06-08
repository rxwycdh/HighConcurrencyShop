package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName ShopCartController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/22 15:36
 * @Modified
 * @Version 1.0
 */
@Api(value = "购物车接口", tags = "购物车接口相关Api")
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopCartController.class);

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId,
                               @RequestBody ShopcartBO shopcartBO,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if(StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        // TODO 前端用户在登陆的清空下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        LOGGER.info(shopcartBO.toString());
        return IMOOCJSONResult.ok();
    }

    @ApiOperation("从购物车中删除商品")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId,
                               @RequestParam String itemSpecId,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        // TODO 用户在页面购物车中的商品数据，如果用户已登录，则需要同步删除后端存的购物车数据

        return IMOOCJSONResult.ok();
    }
}
