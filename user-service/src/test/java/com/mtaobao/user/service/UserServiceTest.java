package com.mtaobao.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.user.entity.User;
import com.mtaobao.user.mapper.UserMapper;
import com.mtaobao.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UserService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1001L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setNickname("测试用户");
        testUser.setStatus(1);
        testUser.setDeleted(0);
    }

    /**
     * 测试用户注册成功
     */
    @Test
    void testRegister_Success() {
        // Given: 用户名不存在
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(null);
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // When
        User result = userService.register("newuser", "password123");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("newuser");
        assertThat(result.getPassword()).isEqualTo("password123");
        verify(userMapper).insert(any(User.class));
    }

    /**
     * 测试用户注册 - 用户名已存在
     */
    @Test
    void testRegister_UsernameExists() {
        // Given: 用户名已存在
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testUser);

        // When & Then
        assertThatThrownBy(() -> userService.register("testuser", "password123"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("用户名已存在");

        verify(userMapper, never()).insert(any(User.class));
    }

    /**
     * 测试用户登录成功
     */
    @Test
    void testLogin_Success() {
        // Given
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testUser);

        // When
        User result = userService.login("testuser", "password123");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    /**
     * 测试用户登录 - 用户不存在
     */
    @Test
    void testLogin_UserNotFound() {
        // Given
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> userService.login("notexist", "password123"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("用户不存在");
    }

    /**
     * 测试用户登录 - 密码错误
     */
    @Test
    void testLogin_WrongPassword() {
        // Given
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testUser);

        // When & Then
        assertThatThrownBy(() -> userService.login("testuser", "wrongpassword"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("密码错误");
    }

    /**
     * 测试用户登录 - 账号被禁用
     */
    @Test
    void testLogin_AccountDisabled() {
        // Given
        testUser.setStatus(0);
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testUser);

        // When & Then
        assertThatThrownBy(() -> userService.login("testuser", "password123"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("账号已被禁用");
    }

    /**
     * 测试根据用户名查询用户
     */
    @Test
    void testGetByUsername() {
        // Given
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testUser);

        // When
        User result = userService.getByUsername("testuser");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    /**
     * 测试根据ID查询用户
     */
    @Test
    void testGetById() {
        // Given
        when(userMapper.selectById(1001L)).thenReturn(testUser);

        // When
        User result = userService.getById(1001L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1001L);
    }
}
