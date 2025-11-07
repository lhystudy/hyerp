package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.model.User;
import com.hyerp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");
            
            if (username == null || username.isEmpty()) {
                return Result.error("用户名不能为空");
            }
            if (password == null || password.isEmpty()) {
                return Result.error("密码不能为空");
            }
            
            User user = userService.login(username, password);
            
            // 生成简单的token（实际项目中应该使用JWT等）
            String token = "token_" + System.currentTimeMillis() + "_" + user.getId();
            
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", user);
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        // 实际项目中应该清除token等
        return Result.success();
    }
}

