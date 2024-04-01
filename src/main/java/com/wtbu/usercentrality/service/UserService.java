package com.wtbu.usercentrality.service;

import com.wtbu.usercentrality.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author moon
* @description 针对表【user】的数据库操作Service
* @createDate 2024-01-28 02:06:36
*/
public interface UserService extends IService<User> {
    /**
     *用户注册
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param planetCode  星球编号
     * @return 新用户 id
     */
    long loginRegister(String userAccount,String userPassword,String checkPassword,String planetCode );

    /**
     * 用户登陆
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 返回脱敏的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

}

