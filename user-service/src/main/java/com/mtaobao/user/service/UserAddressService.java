package com.mtaobao.user.service;

import com.mtaobao.user.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 获取用户地址列表
     */
    List<UserAddress> getAddressList(Long userId);

    /**
     * 获取用户默认地址
     */
    UserAddress getDefaultAddress(Long userId);

    /**
     * 根据ID查询地址
     */
    UserAddress getById(Long id);

    /**
     * 新增地址
     */
    void addAddress(UserAddress address);

    /**
     * 更新地址
     */
    void updateAddress(UserAddress address);

    /**
     * 删除地址
     */
    void deleteAddress(Long id);

    /**
     * 设置默认地址
     */
    void setDefaultAddress(Long userId, Long addressId);
}
