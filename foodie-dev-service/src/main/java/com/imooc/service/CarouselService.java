package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @FileName CarouselService
 * @Description
 * @Author jiuhao
 * @Date 2020/5/19 10:41
 * @Modified
 * @Version 1.0
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     */
    List<Carousel> queryAll(Integer isShow);
}
