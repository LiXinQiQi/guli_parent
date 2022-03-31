package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/03/23
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children =new ArrayList<>();

}
