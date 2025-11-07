package com.hyerp.service;

import com.hyerp.dao.UserMapper;
import com.hyerp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RuntimeException("用户已被禁用");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    public User createUser(User user) {
        // 检查用户名是否已存在
        User existing = userMapper.selectByUsername(user.getUsername());
        if (existing != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认启用
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        return user;
    }

    public User updateUser(Long id, User user) {
        User existing = userMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("用户不存在");
        }
        // 如果修改了用户名，检查新用户名是否已存在
        if (user.getUsername() != null && !existing.getUsername().equals(user.getUsername())) {
            User usernameExists = userMapper.selectByUsername(user.getUsername());
            if (usernameExists != null) {
                throw new RuntimeException("用户名已存在");
            }
        }
        user.setId(id);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    public void deleteUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        userMapper.deleteByPrimaryKey(id);
    }
}

