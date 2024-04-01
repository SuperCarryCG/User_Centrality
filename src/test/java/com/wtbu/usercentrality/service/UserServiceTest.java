package com.wtbu.usercentrality.service;

import com.wtbu.usercentrality.model.User;
import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * @author chenguang
 */

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("cg");
        user.setUserAccount("22204001");
        user.setAvatarUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201807%2F01%2F20180701152018_mUivR.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1709015820&t=d1e07f0f5c0bed023433e0358f35e031");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("12345678912");
        user.setEmail("771488525@qq.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);


    }

    @Test
        //断言只推荐在测试类中使用
    void loginRegister() {
        //编写单元测试
        String userAccount = "jkgoing1";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";
        long result = userService.loginRegister(userAccount, userPassword, checkPassword,planetCode);
        //1-密码不能为空
//        Assertions.assertEquals(-1, result);

        //2-账户长度不小于4位
//        userAccount = "jk";
//        result = userService.loginRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);

        //3-密码不少于8位
//        userAccount = "jkgo";
//        userPassword = "123456";
//        result = userService.loginRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);

        //4-账户不包括特殊字符
//        userAccount = "c!123456g";
//        userPassword = "12345678";
//        result = userService.loginRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);

        //5-密码和校验密码相同
//        checkPassword = "123456789";
//        result = userService.loginRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);

        //6-账户不能重复
        userAccount = "jkgoing1";
        result = userService.loginRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);

//        userAccount = "jkgoing99";
//        result = userService.loginRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertTrue(result > 0);



    }
}