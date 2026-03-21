package com.mtaobao.user.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.user.dto.UserLoginDTO;
import com.mtaobao.user.dto.UserRegisterDTO;
import com.mtaobao.user.entity.User;
import com.mtaobao.user.service.UserService;
import com.mtaobao.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserRegisterDTO dto) {
        User user = userService.register(dto.getUsername(), dto.getPassword());
        return Result.success(convertToVO(user));
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody UserLoginDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        Map<String, Object> data = new HashMap<>();
        data.put("user", convertToVO(user));
        data.put("token", "mock-token-" + user.getId()); // 简化版token
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(convertToVO(user));
    }

    private UserVO convertToVO(User user) {
        if (user == null) return null;
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        vo.setAvatar(user.getAvatar());
        vo.setStatus(user.getStatus());
        vo.setCreateTime(user.getCreateTime());
        return vo;
    }
}
