package com.sjw.test.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjw.test.common.exceptions.SysExceptionEnum;
import com.sjw.test.common.exceptions.SystemException;
import com.sjw.test.common.utils.JwtTokenUtil;
import com.sjw.test.common.vo.Response;
import com.sjw.test.dao.user.UserMapper;
import com.sjw.test.entity.user.User;
import com.sjw.test.entity.user.dto.UserLoginDto;
import com.sjw.test.entity.user.vo.UserInfoDetails;
import com.sjw.test.entity.user.vo.UserTokenVo;
import com.sjw.test.service.redis.RedisService;
import com.sjw.test.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 14:59
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public List<User> seleceUsers() {
        log.info("查询用户");
        redisService.set("this","user");
        String value=redisService.get("this").toString();
        log.info("缓存信息：{}",value);
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public boolean updateUser(String name) {
        log.info("修改用户");
        User user=userMapper.selectById(1);
        if(null!=user){
            user.setName(name);
            log.info("修改成功");
            return updateById(user);
        }
        return false;
    }

    @Override
    public User getUserByUserName(String username) {
           //先从缓存中获取
        User user=redisService.getCacheUser(username);
         if(null==user) {
             //如果没有就查询sql
             user= userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
             if(null==user){
                 //如果sql中也没有，就直接返回null
                 return null;
             }
             //将user放入缓存
              redisService.cacheUser(username,user,120L);
         }
         return user;
    }
    @Override
    public UserTokenVo login(UserLoginDto userLoginDto) {
            String token=null;
            UserInfoDetails userInfoDetails =(UserInfoDetails) userDetailsService.loadUserByUsername(userLoginDto.getUsername());
            //判断是否有用户
            if(null==userInfoDetails.getUser()){
                throw new SystemException(SysExceptionEnum.USER_IS_NOT_EXIST.getCode(),SysExceptionEnum.USER_IS_NOT_EXIST.getMessage());
            }
            //验证用户密码
            if (!passwordEncoder.matches(userLoginDto.getPassword(), userInfoDetails.getPassword())) {
                throw new SystemException(SysExceptionEnum.PASSWORD_ERROR.getCode(),SysExceptionEnum.PASSWORD_ERROR.getMessage());
            }
            //获取此用户token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfoDetails, null, userInfoDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userInfoDetails);

        log.info("token:{}",token);
         UserTokenVo userTokenVo=new UserTokenVo();
         userTokenVo.setUsername(userInfoDetails.getUsername());
         userTokenVo.setId(userInfoDetails.getUser().getId());
         userTokenVo.setToken(token);

        return userTokenVo;
    }

    @Override
    public Boolean register(UserLoginDto dto) {
        log.info("dto:{}",dto.toString());
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("name",dto.getUsername()));
        if(null!=user){
            return false;
        }
        user=new User();
        user.setName(dto.getUsername());
         String password=passwordEncoder.encode(dto.getPassword());
        log.info("password:{}",password);
        user.setPassword(password);
        log.info("注册用户;{}",user.toString());
        return userMapper.insert(user)==1?true:false;
    }
}
