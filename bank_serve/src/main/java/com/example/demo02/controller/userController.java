package com.example.demo02.controller;

import com.example.demo02.domain.Users;
import com.example.demo02.mapper.UserMapper;
import com.example.demo02.service.MqttMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Validated
public class userController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MqttMessageSender mqttMessageSender;

    // 获取所有用户列表
    @GetMapping("/userall")
    public ResponseEntity<List<Users>> getAllUsers() {
    mqttMessageSender.sendMsg("java/a","你好");
    mqttMessageSender.sendMsg("abc",0,"@water_add_switch:0");


        try {
            List<Users> users = userMapper.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据用户ID获取用户详情
    @GetMapping("/getuser/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable String userId) {
        try {
            Users user = userMapper.findByUserId(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据微信openId获取用户
    @GetMapping("/openid/{openId}")
    public ResponseEntity<Users> getUserByOpenId(@PathVariable String openId) {
        try {
            Users user = userMapper.findByOpenId(openId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据用户名模糊查询
    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsersByName(@RequestParam String userName) {
        try {
            List<Users> users = userMapper.findByUserName(userName);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // 修改用户余额

    // 更新用户信息
    @PutMapping("/updateinfo/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody Users user) {
        try {
            // 确保URL中的ID与请求体中的ID一致
            if (!userId.equals(user.getUserId())) {
                return ResponseEntity.badRequest().body("用户ID不匹配");
            }

            // 检查用户是否存在
            if (userMapper.existsByUserId(userId) == 0) {
                return ResponseEntity.notFound().build();
            }

            int result = userMapper.update(user);
            if (result > 0) {
                return ResponseEntity.ok("用户更新成功");
            } else {
                return ResponseEntity.badRequest().body("用户更新失败");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器错误: " + e.getMessage());
        }
    }

    // 更新用户余额
    @PatchMapping("/{userId}/balance")
    public ResponseEntity<?> updateUserBalance(@PathVariable String userId, @RequestBody Map<String, Double> request) {
        try {
            Double balance = request.get("blance");
            if (balance == null) {
                return ResponseEntity.badRequest().body("余额不能为空");
            }

            // 余额不能为负数
            if (balance < 0) {
                return ResponseEntity.badRequest().body("余额不能为负数");
            }

            int result = userMapper.updateBalance(userId, balance);
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "用户余额更新成功");
                response.put("userId", userId);
                response.put("newBalance", balance);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.badRequest().body("用户余额更新失败");
            }
        } catch (Exception e) {
            // 如果log报错，可以直接删除log相关代码
            System.err.println("更新用户余额失败 - userId: " + userId + ", error: " + e.getMessage());
            return ResponseEntity.internalServerError().body("服务器错误: " + e.getMessage());
        }
    }



    // 更新用户信息（）
    @PutMapping("/update")
    public Integer updateUser(@RequestBody Users user) {
        return userMapper.updateById(user);
    }
}
