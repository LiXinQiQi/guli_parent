package com.atguigu.staservice.scheduled;

import com.atguigu.commonutils.utils.DateUtil;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/04/01
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void task() {
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStaDaily(date);
        System.out.println("生成数据成功"+date);
    }
}
