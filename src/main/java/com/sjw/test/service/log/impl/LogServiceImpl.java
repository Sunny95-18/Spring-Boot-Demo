package com.sjw.test.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjw.test.dao.log.LogMapper;
import com.sjw.test.dao.user.UserMapper;
import com.sjw.test.entity.log.Log;
import com.sjw.test.entity.user.User;
import com.sjw.test.service.log.LogService;
import com.sjw.test.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/21 16:58
 */
@Slf4j
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    @Autowired
    private LogMapper logMapper;

    @Override
    public int saveLog(Log log) {

        return logMapper.insert(log);
    }
}
