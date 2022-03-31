package com.atguigu.orderservice.client;

import com.atguigu.commonutils.vo.CourseWebVoForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/03/31
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程相关信息跨模块
    @GetMapping("/eduservice/educourse/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);
}
