package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/03/25
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public void delVideo(String videoId) {
        return;
    }

    @Override
    public R delVideoList(List<String> videoIdList) {
        return R.error().message("删除失败");
    }
}
