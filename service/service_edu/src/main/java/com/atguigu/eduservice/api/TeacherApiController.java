package com.atguigu.eduservice.api;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: rise
 * @Date: 2022/03/30
 */
@Api(description = "查询讲师")
@RestController
@RequestMapping("/eduservice/teacherapi")
@CrossOrigin
public class TeacherApiController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getTeacherApiPage/{current}/{limit}")
    public R getTeacherApiPage(@PathVariable Long current,
                               @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        Map<String, Object> map = eduTeacherService.getTeacherApiPage(page);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据id获取讲师")
    @GetMapping("getTeacherCourseById/{id}")
    public R getTeacherCourseById(@PathVariable Long id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", eduTeacher.getId());
        List<EduCourse> courseList = eduCourseService.list(queryWrapper);
        return R.ok().data("eduTeacher", eduTeacher).data("courseList", courseList);
    }
}
