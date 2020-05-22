package com.imooc.service.impl;

import com.imooc.service.StuService;
import com.imooc.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @FileName TestTransServiceImpl
 * @Description
 * @Author jiuhao
 * @Date 2020/5/13 19:18
 * @Modified
 * @Version 1.0
 */
@Service
public class TestTransServiceImpl implements TestTransService {

    @Autowired
    private StuService stuService;

    /**
     * 事务传播测试
     * REQUIRED: 使用当前的事务，如果当前没有事务，则自己新建一个事务(仅是自己，其父方法不会也具有这个事务)，
     *           子方法就会运行在一个事务中，如果当前存在事务，则加入这个事务，成为一个整体
     *           example：领导没饭吃，我有钱，我会自己买了自己吃（自己创建事务）
     *                    领导有的吃，会分给你一起吃（事务传播）
     * SUPPORTS: 如果当前有事务（父方法及其以上），则使用当前事务。如果当前无事务，则不使用事务（多用于查询）
     *           example：领导没饭吃，我也没饭吃。领导有饭吃，我也有饭吃
     * MANDATORY : 该属性必须存在一个当前的事务，如果不存在，则抛出异常
     *           example：领导必须管饭，不管饭没饭吃，我就不乐意就不干了（抛出异常）
     * REQUIES_NEW : 如果当前有事务，则挂起该事务（当前事务会与自己创建的事务独立开来），并且自己创建一个新的事务给自己使用
     *               如果当前没有事务，则同REQUIRED
     *           example：领导有饭吃(有事务)，我偏不要，我自己买了自己吃
     *  NOT_SUPPORTED：如果当前有事务，则把事务挂起（相对于他自己挂起此事务），自己不使用事务去运行操作
     *           example: 领导有饭吃，分一点给你，我太忙了，放一边，我不吃
     * NEVER: 不执行事务，如果当前有事务存在（对于父方法），则抛出异常
     *           example: 领导有饭给你吃，我不想吃，我不干了，我抛出异常
     * NESTED: 如果当前有十五，则开启子事务（嵌套事务），嵌套事务独立提交或者回滚
     *         如果当前没有事务，则同REQUIRED
     *         但是如果主事务提交，则会携带子事务一起提交
     *         如果主事务回滚，则子事务会一起回滚
     *         如果子事务异常，则父事务可以选择性回滚（如果用了try catch 就父事务不回滚）
     *         example：领导决策不对，老板怪罪，领导带着小弟一同受罪，小弟出了差错，则领导可以推卸责任也可以一起承担责任
     *
     */


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationTrans() {

        stuService.saveParent();
        try{
            stuService.saveChildren();
        }catch (Exception e) {

        }

//        int a = 1/0;
    }
}
