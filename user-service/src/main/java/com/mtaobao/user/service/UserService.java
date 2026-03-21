package com.mtaobao.user.service;

import com.mtaobao.user.entity.User;

public interface UserService {

    /**
     * 用户注册
     */
    User register(String username, String password);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 根据ID查询用户
     */
    User getById(Long id);

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 更新用户信息
     */
    void updateUser(User user);
}
