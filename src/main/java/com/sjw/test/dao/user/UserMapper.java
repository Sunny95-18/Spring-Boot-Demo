package com.sjw.test.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjw.test.entity.user.User;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 14:54
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
