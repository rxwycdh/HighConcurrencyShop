package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @FileName CarouselServiceImpl
 * @Description
 * @Author jiuhao
 * @Date 2020/5/19 10:42
 * @Modified
 * @Version 1.0
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;


    @Override
    public List<Carousel> queryAll(Integer isShow) {

        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        example.createCriteria().andEqualTo("isShow", isShow);

        return carouselMapper.selectByExample(example);
    }
}
