package com.wtbu.usercentrality;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;


import java.security.NoSuchAlgorithmException;


@SpringBootTest
class UserCentralityApplicationTests {

    @Test
    void testDigest() throws NoSuchAlgorithmException {
    String newPassword = DigestUtils.md5DigestAsHex(("abcd"+"cheng").getBytes());
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {

    }

}
