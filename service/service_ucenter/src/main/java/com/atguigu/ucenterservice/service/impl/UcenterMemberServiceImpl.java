package com.atguigu.ucenterservice.service.impl;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.commonutils.utils.JwtUtils;
import com.atguigu.commonutils.utils.MD5;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        //验证参数是否为空
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isAnyEmpty(code, mobile, nickname, password)) {
            throw new GuliException(20001,"注册信息缺失");
        }
        //验证手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001,"手机号重复");
        }
        //获取验证码
        String result = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(result)) {
            throw new GuliException(20001,"验证码错误");
        }
        //密码加密
        String md5Password  = MD5.encrypt(password);
        //补充信息后插入数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(md5Password);
        ucenterMember.setAvatar("https://guli-file220322.oss-cn-hangzhou.aliyuncs.com/1.jpg");
        ucenterMember.setIsDisabled(false);
        baseMapper.insert(ucenterMember);

    }

    @Override
    public String login(LoginVo loginVo) {
        //空检验
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isAnyBlank(mobile, password)) {
            throw new GuliException(20001,"手机号或密码有误");
        }
        //根据手机号获取用户信息
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember == null) {
            throw new GuliException(20001,"手机号或密码有误");
        }
        //校验密码
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001,"手机号或密码有误");
        }
        //生成token字符串
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }

    //统计注册人数远程调用
    @Override
    public Integer countRegister(String day) {
        Integer count = baseMapper.countRegister(day);
        return count;
    }
}
