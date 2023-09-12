package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.ttl}")
    private Long tokenTTL;

    //模拟数据库
    static Map<Integer, User> userMap = new HashMap<>();
    static {
        User user1 = new User(1, "zhangsan", "张三", "123456");
        userMap.put(1, user1);
        User user2 = new User(2, "lisi", "李四", "123123");
        userMap.put(2, user2);
    }

    @RequestMapping("loginWithToken")
    public String login(User user){
        for (User dbUser : userMap.values()) {
            if (dbUser.getName().equals(user.getName()) && dbUser.getPassword().equals(user.getPassword())) {
                String token = TokenUtil.generateToken();
                boolean setFlag =false;
                try {
                    setFlag =redisUtil.hmset(token,TokenUtil.getObjectToMap(dbUser),tokenTTL);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(setFlag){
                    logger.info("登录成功！生成token！");
                    return token;
                }
            }
        }
        logger.info("生成token失败");
        return "";
    }


}
