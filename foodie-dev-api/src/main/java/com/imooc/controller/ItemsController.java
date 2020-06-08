package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ItemsController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/20 19:23
 * @Modified
 * @Version 1.0
 */
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemService itemService;

    @ApiOperation("查询商品详情")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult getItemInfo(@PathVariable String itemId) {
        if(StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @ApiOperation("查询商品评价等级")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(@RequestParam String itemId) {
        if(StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        CommentLevelCountsVO vo = itemService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(vo);
    }

    @ApiOperation(value = "查询商品评价")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(@RequestParam String itemId,
                                    @RequestParam Integer level,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize) {
        if(StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品列表")
    @GetMapping("/search")
    public IMOOCJSONResult comments(@RequestParam String keywords,
                                    @RequestParam String sort,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize) {

        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据三级分类id搜索商品列表")
    @GetMapping("/catItems")
    public IMOOCJSONResult comments(@RequestParam Integer catId,
                                    @RequestParam String sort,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize) {

        if(catId == null) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(catId, sort, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }

    // 用于刷新购物车中数据（商品价格刷新），类似京东

    @ApiOperation(value = "根据商品规格id列表查找最新的商品数据")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh(@RequestParam String itemSpecIds) {

        if(StringUtils.isBlank(itemSpecIds)) {
            return IMOOCJSONResult.ok();
        } 

        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);

        return IMOOCJSONResult.ok(shopcartVOS);
    }
}
