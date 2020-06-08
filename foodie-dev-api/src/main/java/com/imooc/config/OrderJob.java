package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @FileName OrderJob
 * @Description
 * @Author jiuhao
 * @Date 2020/5/27 14:41
 * @Modified
 * @Version 1.0
 */
@Component
public class OrderJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderJob.class);

    @Autowired
    private OrderService orderService;

//    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
        LOGGER.info("开始执行定时任务" + DateUtil.getCurrentDateString("HH:mm:ss"));
        orderService.closeOrder();
    }
}

