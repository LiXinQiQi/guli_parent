package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/03/25
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //"删除视频"
    //请求url必须完整
    //参数注解参数名不能省略@PathVariable("videoId")
    @DeleteMapping("/eduvod/video/delVideo/{videoId}")
    public void delVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/delVideoList")
    public R delVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
